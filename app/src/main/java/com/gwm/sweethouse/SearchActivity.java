package com.gwm.sweethouse;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
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
    private Button btnClear, btn1;
    private ArrayList<String> historySearchList = new ArrayList<>();
    private SharedPreferences sp;
    private Gson gson;
    private ListView lvAgo;
    private AgoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
            System.out.println("-----");
        }
        lvAgo.setAdapter(adapter);
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
                    Toast.makeText(SearchActivity.this, content, Toast.LENGTH_SHORT).show();
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
        }
    }
}
