package com.gwm.sweethouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BlindActivity extends Activity {
    EditText etLength,etHeight,etPaint;
    TextView tvPaint2;
    Button btnSum,btnSure;
    RelativeLayout rlBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind);
        etLength= (EditText) findViewById(R.id.ed_length);
        etHeight= (EditText) findViewById(R.id.ed_height);
        btnSum= (Button) findViewById(R.id.btn_jisuan);
        etPaint= (EditText) findViewById(R.id.ed_gg);
        rlBack= (RelativeLayout) findViewById(R.id.rl_back);
        final DecimalFormat df= new DecimalFormat("######0.0");
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String l = etLength.getText().toString();
                double length = Double.parseDouble(l);
                String h = etHeight.getText().toString();
                double height = Double.parseDouble(h);
                String p = etPaint.getText().toString();
                final double paint = Double.parseDouble(p);
                final double sum1 = length * height;
                final double paints1 = sum1 / paint;
                String sum = df.format(sum1);
                String paints = df.format(paints1);
                View view = View.inflate(BlindActivity.this, R.layout.dialog_chuanglian, null);
                tvPaint2 = (TextView) view.findViewById(R.id.tv_tuliao2);
                btnSure = (Button) view.findViewById(R.id.btn_jisuan);
                tvPaint2.setText("您所需要的布料为:" + paints + "米");
                final AlertDialog dialog = new AlertDialog.Builder(BlindActivity.this)
                        .setView(view)
                        .show();
                btnSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

}
