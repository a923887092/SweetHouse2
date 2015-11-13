package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CalculatorActivity extends Activity implements View.OnClickListener{
    private LinearLayout llPaint,llFloorz,llWall,llFloorb,llWallPaper,llBlind;
    private RelativeLayout rlBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        llPaint= (LinearLayout) findViewById(R.id.ll_tuliao);
        llFloorz= (LinearLayout) findViewById(R.id.ll_dizhuang);
        llWall= (LinearLayout) findViewById(R.id.ll_qiangzhuan);
        llFloorb= (LinearLayout) findViewById(R.id.ll_diban);
        llWallPaper= (LinearLayout) findViewById(R.id.ll_qiangzhi);
        llBlind= (LinearLayout) findViewById(R.id.ll_chuanglian);
        rlBack= (RelativeLayout) findViewById(R.id.rl_back);
        llPaint.setOnClickListener(this);
        llFloorz.setOnClickListener(this);
        llWall.setOnClickListener(this);
        llFloorb.setOnClickListener(this);
        llWallPaper.setOnClickListener(this);
        llBlind.setOnClickListener(this);
        rlBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.ll_tuliao:
            Intent intl=new Intent(CalculatorActivity.this,PaintActivity.class);
            startActivity(intl);
            break;
        case R.id.ll_dizhuang:
            Intent indz=new Intent(CalculatorActivity.this,FloorzActivity.class);
            startActivity(indz);
            break;
        case R.id.ll_qiangzhuan:
            Intent inqzuan=new Intent(CalculatorActivity.this,WallActivity.class);
            startActivity(inqzuan);
            break;
        case R.id.ll_diban:
            Intent indb=new Intent(CalculatorActivity.this,FloorbActivity.class);
            startActivity(indb);
            break;
        case R.id.ll_qiangzhi:
            Intent inqzhi=new Intent(CalculatorActivity.this,WallPaperActivity.class);
            startActivity(inqzhi);
            break;
        case R.id.ll_chuanglian:
            Intent incl=new Intent(CalculatorActivity.this,BlindActivity.class);
            startActivity(incl);
            break;
        case R.id.rl_back:
            finish();
    }
    }
}
