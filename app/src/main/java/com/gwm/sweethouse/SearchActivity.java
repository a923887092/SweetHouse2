package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private TextView tvSearch;
    private EditText etSearch;
    private Button btnClear, btn11, btn12, btn13, btn14, btn21, btn22, btn23, btn24, btn31, btn32;
    private ArrayList<String> historySearchList = new ArrayList<>();
    private SharedPreferences sp;
    private Gson gson;
    private ListView lvAgo;
    private AgoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        btn11 = (Button) findViewById(R.id.btn11);
        btn12 = (Button) findViewById(R.id.btn12);
        btn13 = (Button) findViewById(R.id.btn13);
        btn14 = (Button) findViewById(R.id.btn14);
        btn21 = (Button) findViewById(R.id.btn21);
        btn22 = (Button) findViewById(R.id.btn22);
        btn23 = (Button) findViewById(R.id.btn23);
        btn24 = (Button) findViewById(R.id.btn24);
        btn31 = (Button) findViewById(R.id.btn31);
        btn32 = (Button) findViewById(R.id.btn32);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);
        btn14.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        btn24.setOnClickListener(this);
        btn31.setOnClickListener(this);
        btn32.setOnClickListener(this);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvSearch = (TextView) findViewById(R.id.tv_search);
        etSearch = (EditText) findViewById(R.id.et_search);
        btnClear = (Button) findViewById(R.id.btn_clear);
        lvAgo = (ListView) findViewById(R.id.lv_ago);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        String history_search = sp.getString("history_search", "");
        gson = new Gson();
        adapter = new AgoListAdapter();
        if (!TextUtils.isEmpty(history_search)) {
            historySearchList = gson.fromJson(history_search, ArrayList.class);
        } else {

            btnClear.setVisibility(View.INVISIBLE);
            System.out.println("-----");
        }
        lvAgo.setAdapter(adapter);
        lvAgo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                text = textView.getText().toString();
                startNewActivity(text);
            }
        });
        tvSearch.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    class AgoListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return historySearchList.size();
        }

        @Override
        public String getItem(int position) {
            return historySearchList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = new TextView(SearchActivity.this);
            view.setText(getItem(position));
            view.setPadding(10, 10, 10, 10);
            return view;
        }
    }
    private String text;
    @Override
    public void onClick(View v) {
        SharedPreferences.Editor edit = sp.edit();
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_search:
                String content = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(content)){
                    Toast.makeText(SearchActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    int count = 0;
                    if (!historySearchList.isEmpty()){
                        for (String content1 :historySearchList){
                            if (content.equals(content1)){
                                count++;
                            }
                        }
                    }
                    if (count == 0) {
                        historySearchList.add(content);
                        String json = gson.toJson(historySearchList);
                        edit.putString("history_search", json);
                        edit.commit();
                        adapter.notifyDataSetChanged();
                    }
                    btnClear.setVisibility(View.VISIBLE);
                    startNewActivity(content);
//                    Toast.makeText(SearchActivity.this, content, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_clear:
                if (!historySearchList.isEmpty()){
                    edit.remove("history_search");
                    edit.commit();
                    historySearchList.clear();
                    adapter.notifyDataSetChanged();
                    System.out.println("historySearch:" + historySearchList);
                    Toast.makeText(SearchActivity.this, "历史搜索已清除", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn11:
                text = getBtnText(btn11);
                startNewActivity(text);
                break;
            case R.id.btn12:
                text = getBtnText(btn12);
                startNewActivity(text);
                break;
            case R.id.btn13:
                text = getBtnText(btn13);
                startNewActivity(text);
                break;
            case R.id.btn14:
                text = getBtnText(btn14);
                startNewActivity(text);
                break;
            case R.id.btn21:
                text = getBtnText(btn21);
                startNewActivity(text);
                break;
            case R.id.btn22:
                text = getBtnText(btn22);
                startNewActivity(text);
                break;
            case R.id.btn23:
                text = getBtnText(btn23);
                startNewActivity(text);
                break;
            case R.id.btn24:
                text = getBtnText(btn24);
                startNewActivity(text);
                break;
            case R.id.btn31:
                text = getBtnText(btn31);
                startNewActivity(text);
                break;
            case R.id.btn32:
                text = getBtnText(btn32);
                startNewActivity(text);
                break;

        }
    }

    private void startNewActivity(String text) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("content", text);
        startActivity(intent);
    }

    public String getBtnText(Button btn){
        String textBtn = btn.getText().toString();
        return textBtn;
    }
}
