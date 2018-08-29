package com.famgy.virtualpartner.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.VpnService;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.famgy.virtualpartner.R;

public class PlusVpnService extends VpnService {
    public static final String EXTRA_COMMAND = "Command";
    private static final String EXTRA_REASON = "Reason";
    private static final String TAG = "PlusVpnService";
    public enum Command {run, start, reload, stop, stats, set, householding, watchdog}

    private volatile Looper commandLooper;
    private volatile CommandHandler commandHandler;

    private ParcelFileDescriptor vpn = null;
    private PlusVpnService.Builder last_builder = null;

    private Thread tunnelThread = null;

    @Override
    public void onCreate() {
        super.onCreate();

        HandlerThread commandThread = new HandlerThread(getString(R.string.app_name) + " command", Process.THREAD_PRIORITY_FOREGROUND);
        commandThread.start();

        commandLooper = commandThread.getLooper();
        commandHandler = new CommandHandler(commandLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received " + intent);

        Command cmd = (Command) intent.getSerializableExtra(EXTRA_COMMAND);
        String reason = intent.getStringExtra(EXTRA_REASON);

        Log.i(TAG, "Start intent=" + intent + " command=" + cmd + " reason=" + reason);

        commandHandler.queue(intent);

        return START_STICKY;
    }

    public static void start(String reason, Context context) {
        Intent intent = new Intent(context, PlusVpnService.class);
        intent.putExtra(EXTRA_COMMAND, Command.start);
        intent.putExtra(EXTRA_REASON, reason);
        ContextCompat.startForegroundService(context, intent);
    }

    public static void stop(String reason, Context context, boolean vpnonly) {
        Intent intent = new Intent(context, PlusVpnService.class);
        intent.putExtra(EXTRA_COMMAND, Command.stop);
        intent.putExtra(EXTRA_REASON, reason);
        ContextCompat.startForegroundService(context, intent);
    }

    private final class CommandHandler extends Handler {
        public int queue = 0;

        public CommandHandler(Looper looper) {
            super(looper);
        }


        public void queue(Intent intent) {
            synchronized (this) {
                queue++;
            }
            Command cmd = (Command) intent.getSerializableExtra(EXTRA_COMMAND);
            Message msg = commandHandler.obtainMessage();
            msg.obj = intent;
            msg.what = cmd.ordinal();
            commandHandler.sendMessage(msg);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                synchronized (PlusVpnService.this) {
                    handleIntent((Intent) msg.obj);
                }
            } catch (Throwable ex) {
                Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
            }
        }

        private void handleIntent(Intent intent) {
            Command cmd = (Command) intent.getSerializableExtra(EXTRA_COMMAND);
            String reason = intent.getStringExtra(EXTRA_REASON);
            Log.i(TAG, "Executing intent=" + intent + " command=" + cmd + " reason=" + reason);

            switch (cmd) {
                case start:
                    start();
                    break;
                case stop:
                    stop();
                    break;
                default:
                    Log.e(TAG, "Unknown command=" + cmd);
            }
        }

        private void start() {
            if (vpn == null) {
                last_builder = getBuilder();
                vpn = startVPN(last_builder);
                if (vpn == null) {
                    Log.e(TAG, "Start VPN failed");
                    return;
                }

                startNative(vpn);
            }
        }

        private void stop() {
            if (vpn != null) {
                stopVPN(vpn);
                vpn = null;
            }
        }
    }

    private ParcelFileDescriptor startVPN(Builder builder) throws SecurityException {
        try {
            return builder.establish();
        } catch (SecurityException ex) {
            throw ex;
        } catch (Throwable ex) {
            Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
            return null;
        }
    }

    private void stopVPN(ParcelFileDescriptor pfd) {
        Log.i(TAG, "Stopping");
        try {
            pfd.close();
        } catch (IOException ex) {
            Log.e(TAG, ex.toString() + "\n" + Log.getStackTraceString(ex));
        }
    }

    private Builder getBuilder() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean subnet = prefs.getBoolean("subnet", false);
        boolean tethering = prefs.getBoolean("tethering", false);
        boolean lan = prefs.getBoolean("lan", false);
        boolean ip6 = prefs.getBoolean("ip6", true);
        boolean filter = prefs.getBoolean("filter", false);
        boolean system = prefs.getBoolean("manage_system", false);

        // Build VPN service
        Builder builder = new Builder();
        builder.setSession(getString(R.string.app_name));

        // VPN address
        String vpn4 = prefs.getString("vpn4", "10.1.10.1");
        Log.i(TAG, "vpn4=" + vpn4);
        builder.addAddress(vpn4, 32);

        // Subnet routing
        builder.addRoute("0.0.0.0", 0);

        // MTU
        builder.setMtu(1500);

        // Add list of allowed applications
        try {
            //builder.addAllowedApplication("com.android.chrome");
            builder.addAllowedApplication("com.tencent.mtt");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return builder;
    }

    private class Builder extends VpnService.Builder {
        private NetworkInfo networkInfo;
        private int mtu;
        private List<String> listAddress = new ArrayList<>();
        private List<String> listRoute = new ArrayList<>();
        private List<InetAddress> listDns = new ArrayList<>();
        private List<String> listDisallowed = new ArrayList<>();

        private Builder() {
            super();
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = cm.getActiveNetworkInfo();
        }

        @Override
        public VpnService.Builder setMtu(int mtu) {
            this.mtu = mtu;
            super.setMtu(mtu);
            return this;
        }

        @Override
        public Builder addAddress(String address, int prefixLength) {
            listAddress.add(address + "/" + prefixLength);
            super.addAddress(address, prefixLength);
            return this;
        }

        @Override
        public Builder addRoute(String address, int prefixLength) {
            listRoute.add(address + "/" + prefixLength);
            super.addRoute(address, prefixLength);
            return this;
        }

        @Override
        public Builder addDnsServer(InetAddress address) {
            listDns.add(address);
            super.addDnsServer(address);
            return this;
        }

        @Override
        public Builder addAllowedApplication(String packageName) throws PackageManager.NameNotFoundException {
            listDisallowed.add(packageName);
            super.addAllowedApplication(packageName);
            return this;
        }

        @Override
        public Builder addDisallowedApplication(String packageName) throws PackageManager.NameNotFoundException {
            listDisallowed.add(packageName);
            super.addDisallowedApplication(packageName);
            return this;
        }
    }

    private void startNative(final ParcelFileDescriptor vpn)
    {
        if (null == tunnelThread) {
            tunnelThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    jniRun(vpn.getFd());
                }
            });

            tunnelThread.start();
        }
    }

    private void stopNative(ParcelFileDescriptor vpn)
    {
        if (null != tunnelThread) {
            jniStop(vpn.getFd());
            tunnelThread = null;
        }
    }

    private native void jniRun(int tunnelFd);
    private native void jniStop(int tunnelFd);
}
