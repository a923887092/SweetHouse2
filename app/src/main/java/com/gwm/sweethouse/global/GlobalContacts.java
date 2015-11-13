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
//    public static final String VISON_URL = "http://10.0.3.2:8080/SweetHouse";
    public static final String VISON_URL = "http://10.203.1.78:8080/SweetHouse";
//    public static final String SERVER_URL = "http://10.0.3.2:8080/SweetHouse";
    public static final String SERVER_URL = "http://10.203.1.78:8080/SweetHouse";
//    public static final String SERVER_URL = "http://10.203.1.99:8080/SweetHouse";
    public static final String RECOMMEND_URL = SERVER_URL + "/recommendServlet?method=getJson&pageNo=";
    public static final String MALL_ZM_URL = SERVER_URL + "/productXlServlet?method=getJson&dlName=" + Uri.encode("开关照明");
    public static final String MALL_WY_URL = SERVER_URL + "/productXlServlet?method=getJson&dlName=" + Uri.encode("卫浴洁具");
    public static final String GOODS_ZM_URL = SERVER_URL + "/ProductServlet?method=getJson&xlId=";
    public static final String GOODS_DISCOUNT_URL = SERVER_URL + "/ProductServlet?method=getDiscountJson&discount=0.4";
    public static final String HOME_SEARCH_GOODS_URL = SERVER_URL + "/ProductServlet?method=getJsonForSearch&productName=";
    public static final String GOOD_URL = SERVER_URL + "/ProductServlet?method=getProductJson&id=";
    public static final String GOOD_IMG_URL = SERVER_URL + "/ProductServlet?method=getProductImgJson&id=";
    public static final String ACTIVITY_URL = SERVER_URL + "/activityServlet?method=getActivities&addr=";
    public static final String SALEDS_URL = SERVER_URL + "/saledServlet?method=getJson";
    public static final String IMAGE_NULL_URL = SERVER_URL + "/image/ic_default_adimage.jpg";
    public static final String PARAMETER_URL = SERVER_URL + "/parameterServlet?method=getJson&productId=";
    public static final int PAGE_SIZE = 6;

    //11 06
    public static final String COMMENT_URL = SERVER_URL + "/commentServlet?method=getJson&productId=";
    public static final String ADD_CART_URL = SERVER_URL + "/AddtoCart";


    //by btt
    //http://10.203.1.99:8080/SweetHouse/ZxCompanyServlet?method=searchCompany&name=%E5%88%9B%E8%85%BE
    public static final String COMPANY_URL=SERVER_URL+"/ZxCompanyServlet?method=searchCompany&name=";
    //http://localhost:8080/SweetHouse/ZxCompanyServlet?method=comSelect&city=%E5%A7%91%E8%8B%8F
    public static final String COMPANY_SELECT_URL=SERVER_URL+"/ZxCompanyServlet?method=comSelect";
    public static final String COMPANY_DETAIL_URL=SERVER_URL+"/CompanyDetail?method=comDetail&comid=";
    public static final String COMPANYA_URL=SERVER_URL+"/CompanyaPicServlet?method=comaPic&comid=";
    public static final String BEAUT_PIC_URL=SERVER_URL+"/BeatufulPicServlet?method=picSelect";
    //http://localhost:8080/SweetHouse/CodePicServlet?method=codePic&space=%E5%8D%A7%E5%AE%A4
    public static final String CODE_PIC_URL=SERVER_URL+"/CodePicServlet?method=codePic";
    //http://localhost:8080/SweetHouse/BeatufulPicServlet?method=picDetail&beaid=1
    public static final String PIC_DETAIT_URL=SERVER_URL+"/BeatufulPicServlet?method=picDetail&beaid=";
    //http://localhost:8080/SweetHouse/CodePicDetailServlet?method=picCodeDetail&codid=1
    public  static final String CODEPIC_DETAIL_URL=SERVER_URL+"/CodePicDetailServlet?method=picCodeDetail&codid=";

    //by tl
    public static final String CART_URL=SERVER_URL + "/Cart_servlet";
    public static final String ADDRESS_URL=SERVER_URL + "/Address";
    public static final String CHANGE_URL=SERVER_URL + "/ChangeCart";
}
