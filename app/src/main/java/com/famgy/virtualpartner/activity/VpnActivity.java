package com.famgy.virtualpartner.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.VpnService;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.service.PlusVpnService;

public class VpnActivity extends AppCompatActivity {

    private static final int REQUEST_VPN = 1;
    private static final String TAG = "NetGuard.Main";
    private SwitchCompat swEnabled;
    private AlertDialog dialogVpn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpn);

        initView();
    }

    private void initView() {
        swEnabled = findViewById(R.id.sw_vpn_enabled);
        swEnabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(TAG, "Switch" + isChecked);

                if (isChecked) {
                    final Intent prepare = VpnService.prepare(VpnActivity.this);
                    if (null == prepare) {
                        Log.i(TAG, "Prepare done");
                        onActivityResult(REQUEST_VPN, RESULT_OK, null);
                    } else {
                        // Show dialog
                        swEnabled.setChecked(false);
                        LayoutInflater inflater = LayoutInflater.from(VpnActivity.this);
                        View view = inflater.inflate(R.layout.vpn, null, false);
                        dialogVpn = new AlertDialog.Builder(VpnActivity.this)
                                .setView(view)
                                .setCancelable(false)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i(TAG, "Start intent=" + prepare);
                                        startActivityForResult(prepare, REQUEST_VPN);
                                    }
                                })
                                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        dialogVpn = null;
                                    }
                                })
                                .create();

                        dialogVpn.show();
                    }
                } else {
                    PlusVpnService.stop("switch off", VpnActivity.this, false);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        Log.i(TAG, "onActivityResult request=" + requestCode + " result=" + requestCode + " ok=" + (resultCode == RESULT_OK));

        if (requestCode == REQUEST_VPN) {
            // Handle VPN approval
            if (resultCode == RESULT_OK) {
                PlusVpnService.start("prepared", this);

                Toast on = Toast.makeText(VpnActivity.this, R.string.msg_on, Toast.LENGTH_LONG);
                on.setGravity(Gravity.CENTER, 0, 0);
                on.show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, R.string.msg_vpn_cancelled, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.w(TAG, "Unknown activity result request=" + requestCode);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
