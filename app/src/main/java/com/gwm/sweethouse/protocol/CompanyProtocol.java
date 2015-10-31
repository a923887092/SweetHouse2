package com.gwm.sweethouse.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwm.sweethouse.bean.Company;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/24.
 */
public class CompanyProtocol extends BaseProtocol<ArrayList<Company>> {
    private static final String TAG = "Companyrotocol";
    public CompanyProtocol(String url) {
        super(url);
    }

    @Override
    public ArrayList<Company> paserJson(String json) {
        Gson gson = new Gson();
        ArrayList<Company> arrayList = gson.fromJson(json, new TypeToken<ArrayList<Company>>() {
        }.getType());
        return arrayList;
    }

    @Override
    protected String getTypes() {
        return "company";
    }
}
