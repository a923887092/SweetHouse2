package com.gwm.sweethouse.global;

import android.net.Uri;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2015/10/12.
 */
public class GlobalContacts {
    //    public static final String SERVER_URL = "http://192.168.56.1:8080/SweetHouse";
    public static final String VISON_URL = "http://10.40.8.13:8080/SweetHouse";
    public static final String SERVER_URL = "http://10.0.3.2:8080/SweetHouse";
    public static final String RECOMMEND_URL = SERVER_URL + "/recommendServlet?method=getJson";
    public static final String MALL_ZM_URL = SERVER_URL + "/productXlServlet?method=getJson&dlName=" + Uri.encode("开关照明");
    public static final String MALL_WY_URL = SERVER_URL + "/productXlServlet?method=getJson&dlName=" + Uri.encode("卫浴洁具");
    public static final String GOODS_ZM_URL = SERVER_URL + "/ProductServlet?method=getJson&xlId=";
    public static final String GOOD_URL = SERVER_URL + "/ProductServlet?method=getProductJson&id=";
    public static final String GOOD_IMG_URL = SERVER_URL + "/ProductServlet?method=getProductImgJson&id=";
}
