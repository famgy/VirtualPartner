package com.famgy.virtualpartner.activity;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.famgy.virtualpartner.R;
import com.famgy.virtualpartner.util.AESDecryptUtil;
import com.famgy.virtualpartner.util.AESEncryptUtil;

public class LineDecriptActivity extends AppCompatActivity {

    private TextView tv_androidID = null;
    private EditText et_cipherText = null;
    private TextView et_cipherText2 = null;
    private TextView tv_content = null;
    private EditText tv_content2 = null;
    private Button btn_getContent = null;
    private Button btn_getContent2 = null;
    private final Long longValue = 15485863L;
    private String content = null;
    private CharSequence cs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_decript);

        tv_androidID = (TextView) findViewById(R.id.tv_androidID);
        et_cipherText = (EditText) findViewById(R.id.et_cipherText);
        tv_content = (TextView) findViewById(R.id.tv_content);
        btn_getContent = (Button) findViewById(R.id.btn_getContent);


        cs = Settings.Secure.getString(this.getContentResolver(),"android_id");
        tv_androidID.append(cs.toString());

        btn_getContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = AESDecryptUtil.decrypt(Long.valueOf(longValue),et_cipherText.getText().toString().trim(),cs.toString());
                tv_content.setText(content);
            }
        });


        tv_content2 = (EditText) findViewById(R.id.tv_content2);
        et_cipherText2 = (TextView) findViewById(R.id.et_cipherText2);
        btn_getContent2 = (Button) findViewById(R.id.btn_getContent2);


        btn_getContent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = AESEncryptUtil.encrypt(Long.valueOf(longValue),tv_content2.getText().toString().trim(),cs.toString());
                et_cipherText2.setText(content);
            }
        });
    }
}
