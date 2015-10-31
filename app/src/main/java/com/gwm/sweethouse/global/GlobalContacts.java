package com.gwm.sweethouse.global;

import android.net.Uri;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2015/10/12.
 */
public class GlobalContacts {
    //by gwm
    //    public static final String SERVER_URL = "http://192.168.56.1:8080/SweetHouse";
    public static final String VISON_URL = "http://10.203.1.73:8080/SweetHouse";
//    public static final String SERVER_URL = "http://10.0.3.2:8080/SweetHouse";
    public static final String SERVER_URL = "http://10.203.1.78:8080/SweetHouse";
//    public static final String SERVER_URL = "http://10.203.1.99:8080/SweetHouse";
    public static final String RECOMMEND_URL = SERVER_URL + "/recommendServlet?method=getJson&pageNo=";
    public static final String MALL_ZM_URL = SERVER_URL + "/productXlServlet?method=getJson&dlName=" + Uri.encode("开关照明");
    public static final String MALL_WY_URL = SERVER_URL + "/productXlServlet?method=getJson&dlName=" + Uri.encode("卫浴洁具");
    public static final String GOODS_ZM_URL = SERVER_URL + "/ProductServlet?method=getJson&xlId=";
    public static final String HOME_SEARCH_GOODS_URL = SERVER_URL + "/ProductServlet?method=getJsonForSearch&productName=";
    public static final String GOOD_URL = SERVER_URL + "/ProductServlet?method=getProductJson&id=";
    public static final String GOOD_IMG_URL = SERVER_URL + "/ProductServlet?method=getProductImgJson&id=";
    public static final String ACTIVITY_URL = SERVER_URL + "/activityServlet?method=getActivities&addr=";
    public static final String SALEDS_URL = SERVER_URL + "/saledServlet?method=getJson";
    public static final int PAGE_SIZE = 6;


    //by btt
    //http://10.203.1.99:8080/SweetHouse/ZxCompanyServlet?method=searchCompany&name=%E5%88%9B%E8%85%BE
    public static final String COMPANY_URL=SERVER_URL+"/ZxCompanyServlet?method=searchCompany&name=";
    public static final String CITY_SEARCH_URL=SERVER_URL+"/ZxCompanyServlet?method=searchCity&addr=";
    public static final String STYLE_SEARCH_URL=SERVER_URL+"/ZxCompanyServlet?method=searchStyle&style=";
    public static final String HOT_SEARCH_ASC=SERVER_URL+"/ZxCompanyServlet?method=searchSort";
    public static final String HOT_SEARCH_DESC=SERVER_URL+"/ZxCompanyServlet?method=descSort";
    public static final String COMPANY_DETAIL_URL=SERVER_URL+"/CompanyDetail?method=comDetail&comid=";
    public static final String COMPANYA_URL=SERVER_URL+"/CompanyaPicServlet?method=comaPic&comid=";

    //by tl
    public static final String CART_URL="http://10.204.1.55:8080/sweethouse/Cart_servlet";
    public static final String ADDRESS_URL="http://10.204.1.55:8080/sweethouse/AddressServlet";
    public static final String CHANGE_URL="http://10.204.1.55:8080/sweethouse/ChangeCart";
}
