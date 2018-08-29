package com.famgy.virtualpartner.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.adapter.SimpleAdapter;
import com.famgy.virtualpartner.helper.SoCHelper;

public class SoCFragment extends Fragment {

    private Activity activity;
    private Context context;

    private String CLICKONITEM = "CLICKONITEM";

    private String GPU_VENDOR = "GPU_VENDOR";
    private String GPU_RENDERER = "GPU_RENDERER";

    private String USE_DEFAULT_INFORMATION = "USE_DEFAULT_INFORMATION";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.fragment_soc, container, false);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("VirtualPartner", Context.MODE_PRIVATE);

        ListView listView = layoutView.findViewById(R.id.listViewSoC);

        String[] stringInformation = new String[] {
                getString(R.string.CPUModel),
                getString(R.string.CPUCores),
                getString(R.string.CPUFreq),
                getString(R.string.CPUGovernor),
                getString(R.string.BogoMIPS),
                getString(R.string.GPUVendor),
                getString(R.string.GPURenderer),
                getString(R.string.OpenGLVersion)
        };
        String[] stringValues;

        if (!sharedPreferences.getBoolean(USE_DEFAULT_INFORMATION, false)) {
            stringValues = new String[] {
                    SoCHelper.getCPUModel(),
                    SoCHelper.getCPUCores(),
                    SoCHelper.getCPUFreq() /*+ " (min - MAX)"*/,
                    SoCHelper.getCPUGovernor(0),
                    SoCHelper.getBogoMIPS(),
                    sharedPreferences.getString(GPU_VENDOR, getString(R.string.Unknown)),
                    sharedPreferences.getString(GPU_RENDERER, getString(R.string.Unknown)),
                    SoCHelper.getOpenGLVersion(context)
            };
        } else {
            stringValues = new String[] {
                    "Qualcomm® Snapdragon™ 835",
                    "8 cores",
                    "1.9 - 2.35 Ghz",
                    "interactive",
                    getString(R.string.Unknown),
                    "Qualcomm®",
                    "Adreno 540",
                    getString(R.string.Unknown)
            };
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(activity, stringInformation, stringValues);
        listView.setAdapter(simpleAdapter);

        if (sharedPreferences.getBoolean(CLICKONITEM, false)) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(position);
                    switch (position) {
                        case 2:
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                            CharSequence[] charSequencesCPUFreqCurrent;
                            if (SoCHelper.getCPUCores().contains("2")) {
                                charSequencesCPUFreqCurrent = new CharSequence[]{
                                        "CPU0: " + SoCHelper.getCurrentCPUFreq(0) + " MHz",
                                        "CPU1: " + SoCHelper.getCurrentCPUFreq(1) + " MHz"
                                };
                            } else if (SoCHelper.getCPUCores().contains("4")) {
                                charSequencesCPUFreqCurrent = new CharSequence[]{
                                        "CPU0: " + SoCHelper.getCurrentCPUFreq(0) + " MHz",
                                        "CPU1: " + SoCHelper.getCurrentCPUFreq(1) + " MHz",
                                        "CPU2: " + SoCHelper.getCurrentCPUFreq(2) + " MHz",
                                        "CPU3: " + SoCHelper.getCurrentCPUFreq(3) + " MHz"
                                };
                            } else if (SoCHelper.getCPUCores().contains("8")) {
                                charSequencesCPUFreqCurrent = new CharSequence[] {
                                        "CPU0: " + SoCHelper.getCurrentCPUFreq(0) + " MHz",
                                        "CPU1: " + SoCHelper.getCurrentCPUFreq(1) + " MHz",
                                        "CPU2: " + SoCHelper.getCurrentCPUFreq(2) + " MHz",
                                        "CPU3: " + SoCHelper.getCurrentCPUFreq(3) + " MHz",
                                        "CPU4: " + SoCHelper.getCurrentCPUFreq(4) + " MHz",
                                        "CPU5: " + SoCHelper.getCurrentCPUFreq(5) + " MHz",
                                        "CPU6: " + SoCHelper.getCurrentCPUFreq(6) + " MHz",
                                        "CPU7: " + SoCHelper.getCurrentCPUFreq(7) + " MHz"
                                };
                            } else
                                break;
                            builder2.setItems(charSequencesCPUFreqCurrent, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Don't do anything - gabrielecappellaro
                                }
                            });
                            builder2.show();
                            break;
                        case 4:
                            AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
                            builder4.setTitle(R.string.BogoMIPSInfoTitle);
                            builder4.setMessage(R.string.BogoMIPSInfoMessage);
                            builder4.setNegativeButton(R.string.Close, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder4.show();
                    }
                }
            });
        }

        return layoutView;
    }
}
