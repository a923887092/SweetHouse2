package com.gwm.sweethouse.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/11/9.
 */
public class MyData {
    public int error_code;
    public String reason;
    public ResultData result;
    public class ResultData {
        public DataData data;
        public class DataData {
            public String animalsYear;
            public String avoid;
            public String date;
            public String lunar;
            public String lunarYear;
            public String suit;
            public String weekday;
            public String year_month;
        }
    }
}
