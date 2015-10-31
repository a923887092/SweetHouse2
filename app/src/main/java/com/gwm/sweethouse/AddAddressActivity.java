package com.gwm.sweethouse;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gwm.sweethouse.global.GlobalContacts;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.net.URLEncoder;

public class AddAddressActivity extends Activity implements View.OnClickListener {
    ImageButton ibtn_return;
    RelativeLayout rl_selectContact,rl_addDistict;
    Button btn_saveAddress;
    EditText et_addName,et_addPhone,et_detailAdd;
    //所在地区
    TextView tv_distinct;
    private final int SEND_SMS_TYPE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        initViews();
    }

    private void initViews() {
        ibtn_return = (ImageButton) findViewById(R.id.ibtn_toAddress);
        rl_selectContact = (RelativeLayout) findViewById(R.id.rl_selectContact);
        rl_addDistict = (RelativeLayout) findViewById(R.id.rl_addDistict);
        btn_saveAddress = (Button) findViewById(R.id.btn_saveAdd);
        et_addName = (EditText) findViewById(R.id.et_addName);
        et_addPhone = (EditText) findViewById(R.id.et_addPhone);
        et_detailAdd = (EditText) findViewById(R.id.et_detailAdd);
        tv_distinct = (TextView) findViewById(R.id.tv_distinct);

        ibtn_return.setOnClickListener(this);
        rl_selectContact.setOnClickListener(this);
        rl_addDistict.setOnClickListener(this);
        btn_saveAddress.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_toAddress:
                finish();
                break;
            case R.id.btn_saveAdd:

                sendToWeb();
                finish();
                break;
            case R.id.rl_selectContact:
                Toast.makeText(AddAddressActivity.this,"选择联系人", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.setAction(Intent.ACTION_PICK);
                i.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, SEND_SMS_TYPE);
                break;
            case R.id.rl_addDistict:
                break;

        }
    }

    //将新建的地址发送到web端，存到数据库
    private void sendToWeb() {
        String name = et_addName.getText().toString();
        String phone = et_addPhone.getText().toString();
        String detailAddress = tv_distinct.getText().toString()+et_detailAdd.getText().toString();
        try {
            String nameString = URLEncoder.encode(name, "utf-8");
//            String nameString = Uri.encode(name, "utf-8");
//            String detailAddressString = Uri.encode(detailAddress, "utf-8");
            String detailAddressString = URLEncoder.encode(detailAddress, "utf-8");

            HttpUtils httpUtils = new HttpUtils();
            String url = GlobalContacts.VISON_URL+"/AddressServlet?method=addAddress&user_id=123458&add_name="+nameString+"&add_phone="+phone+"&add_address="+detailAddressString;
            httpUtils.send(HttpRequest.HttpMethod.GET,
                    url,
                    new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {
                            Toast.makeText(AddAddressActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(HttpException e, String s) {
                            Toast.makeText(AddAddressActivity.this,"添加失败,请重试！",Toast.LENGTH_SHORT).show();
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case SEND_SMS_TYPE:
                        Uri contactData = data.getData();
                        Cursor c = managedQuery(contactData, null, null, null, null);
                        c.moveToFirst();
                        String phoneNum = this.getContactPhone(c);
                        String name = this.getContactName(c);
                        et_addPhone.setText(phoneNum);
                        et_addName.setText(name);
                        break;
                }
                super.onActivityResult(requestCode, resultCode, data);
        }


    }

    //获取联系人姓名
    private String getContactName(Cursor cursor){
        int  nameColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int nameNum = cursor.getInt(nameColumn);
        String nameResult="";
        if (nameNum > 0)
        {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = " + contactId,
                    null, null);
            if (phones.moveToFirst())
            {
                // 遍历所有的电话号码
                for (;!phones.isAfterLast();phones.moveToNext())
                {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    System.out.print(phone_type);
                    String nameString = phones.getString(index);
                    switch(phone_type)
                    {
                        case 2:
                            nameResult=nameString;
                            break;
                    }
                }
                if (!phones.isClosed())
                {
                    phones.close();
                }
            }
        }
        return nameResult;
    }
    //获取联系人电话
    private String getContactPhone(Cursor cursor)
    {

        int phoneColumn = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String phoneResult="";
        System.out.print(phoneNum);
        if (phoneNum > 0)
        {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人的电话号码的cursor;
            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+ " = " + contactId,
                    null, null);
            if (phones.moveToFirst())
            {
                // 遍历所有的电话号码
                for (;!phones.isAfterLast();phones.moveToNext())
                {
                    int index = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phones.getInt(typeindex);
                    String phoneNumber = phones.getString(index);
                    switch(phone_type)
                    {
                        case 2:
                            phoneResult=phoneNumber;
                            break;
                    }
                }
                if (!phones.isClosed())
                {
                    phones.close();
                }
            }
        }
        return phoneResult;
    }

}