package com.gwm.sweethouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class WallActivity extends Activity {
    EditText etLength,etWidth,etHeight,etqzLength,etqzWidth;
    TextView tvPaint,tvPaint2;
    Button btnSum,btnSure;
    RelativeLayout rlBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);
        etLength= (EditText) findViewById(R.id.ed_length);
        etWidth= (EditText) findViewById(R.id.ed_width);
        etHeight= (EditText) findViewById(R.id.ed_height);
        etqzLength= (EditText) findViewById(R.id.ed_dzlength);
        etqzWidth= (EditText) findViewById(R.id.ed_dzwidth);
        btnSum= (Button) findViewById(R.id.btn_jisuan);
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
                String l=etLength.getText().toString();
                double length = Double.parseDouble(l);

                String w=etWidth.getText().toString();
                double width = Double.parseDouble(w);

                String h=etHeight.getText().toString();
                double height = Double.parseDouble(h);

                String ldz=etqzLength.getText().toString();
                double lengthdz = Double.parseDouble(ldz);

                String wdz=etqzWidth.getText().toString();
                double widthdz = Double.parseDouble(wdz);

                Double sum1= (length*height*2)+(width*height)*2;
                Double num1= sum1/(lengthdz*widthdz);
                String sum = df.format(sum1);
                String num = df.format(num1);
                View view = View.inflate(WallActivity.this, R.layout.dialog_qiangzuan, null);
                tvPaint= (TextView) view.findViewById(R.id.tv_tuliao);
                tvPaint2= (TextView) view.findViewById(R.id.tv_tuliao2);
                btnSure= (Button) view.findViewById(R.id.btn_jisuan);
                tvPaint.setText("您所需要的墙砖总面积:"+sum+"平方米");
                tvPaint2.setText("您所需要的墙砖数量:"+num+"块");

                final AlertDialog dialog=new AlertDialog.Builder(WallActivity.this)
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
