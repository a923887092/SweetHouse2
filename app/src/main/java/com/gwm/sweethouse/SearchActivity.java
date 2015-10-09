package com.gwm.sweethouse;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvSearch;
    private EditText etSearch;
    private Button btnClear, btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        etSearch = (EditText) findViewById(R.id.et_search);
        btnClear = (Button) findViewById(R.id.btn_clear);
        tvSearch.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String content = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(SearchActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchActivity.this, content, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear:

                break;
        }
    }
}
