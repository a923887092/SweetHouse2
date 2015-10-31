package com.gwm.sweethouse;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.client.HttpRequest;

public class CalenderActivity extends Activity {
    TextView tv_calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        tv_calendar = (TextView) findViewById(R.id.tv_calender);
        getData();

    }
    private void getData() {
         // JuheDemo.getRequest1();
        HttpUtils httpUtils = new HttpUtils();
        String url = "";
       /* httpUtils.send(HttpRequest.HttpMethod.GET,
                url,);*/

    }
}


