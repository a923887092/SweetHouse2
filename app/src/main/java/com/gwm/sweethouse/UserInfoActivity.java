package com.gwm.sweethouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gwm.sweethouse.bean.User;
import com.gwm.sweethouse.global.GlobalContacts;
import com.gwm.sweethouse.utils.LoginUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.umeng.message.PushAgent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class UserInfoActivity extends Activity implements View.OnClickListener {
    RelativeLayout mlayout1, mlayout3, mlayout4, mlayout5, mlayout6;
    ImageView imageView;
    ImageButton ibtn_userinfoReturn;
    TextView tv_phoneInfo, tv_nameInfo, tv_birthinfo,tv_sexinfo;
    Button btn_exit;
   // String phoneNumber;
    int  user_id;
    boolean loginState;
    String photoUrl;
    /**
     * ImageView对象
     */
    private ImageView iv_photo;
    private String[] items = new String[]{"拍照", "选择本地图片"};
    private String[] sexItems = new String[]{"男", "女","保密"};
    File photoDir;
    private Handler handler;
    /**
     * 请求码
     */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESIZE_REQUEST_CODE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initViews();

        //友盟统计应用启动数据，所有Activty都要添加
        PushAgent.getInstance(UserInfoActivity.this).onAppStart();

       /* Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        user_id = intent.getIntExtra("user_id", 0);
        loginState = intent.getBooleanExtra("loginState", false);
        PhotoUrl = intent.getStringExtra("photoUrl");
        tv_phoneInfo.setText(phoneNumber);*/
        user_id = LoginUtils.getUser_id(UserInfoActivity.this);
        getAllInfoFromWeb(user_id);

       // Log.e("用户信息页得到的数据", phoneNumber + user_id + loginState + PhotoUrl);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        tv_birthinfo.setText(msg.obj.toString());
                        break;
                    case 1:
                        tv_sexinfo.setText(msg.obj.toString());
                        break;
                    case 2:
                        tv_sexinfo.setText(msg.obj.toString());
                        break;
                    case 3:
                        tv_sexinfo.setText(msg.obj.toString());
                        break;
                    case 4:
                        tv_nameInfo.setText(msg.obj.toString());
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initViews() {
        mlayout1 = (RelativeLayout) findViewById(R.id.rl_userinfo1);
        mlayout3 = (RelativeLayout) findViewById(R.id.rl_userinfo3);
        mlayout4 = (RelativeLayout) findViewById(R.id.rl_userinfo4);
        mlayout5 = (RelativeLayout) findViewById(R.id.rl_userinfo5);
        mlayout6 = (RelativeLayout) findViewById(R.id.rl_userinfo6);
        imageView = (ImageView) findViewById(R.id.iv_userPhoto);
        tv_phoneInfo = (TextView) findViewById(R.id.tv_phoneinfo);
        tv_nameInfo = (TextView) findViewById(R.id.tv_nameinfo);
        tv_birthinfo = (TextView) findViewById(R.id.tv_birthinfo);
        tv_sexinfo = (TextView) findViewById(R.id.tv_sexinfo);
        btn_exit = (Button) findViewById(R.id.btn_exit);
        ibtn_userinfoReturn = (ImageButton) findViewById(R.id.ibtn_userinfoReturn);
        mlayout1.setOnClickListener(this);
        ibtn_userinfoReturn.setOnClickListener(this);
        mlayout3.setOnClickListener(this);
        mlayout4.setOnClickListener(this);
        mlayout5.setOnClickListener(this);
        mlayout6.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_userinfoReturn:
                //想法：：：可以携带已更改的数据返回我的界面
                //想法：：：将修改后的数据一次性提交到服务器
                //只是finish（），连续的finish（）会导致错误
                finish();
                break;
            case R.id.rl_userinfo1:
                showPhotoDialog();
                break;
            case R.id.rl_userinfo3:
               /* String nickName = tv_nameInfo.getText().toString();
                Intent intent = new Intent(UserInfoActivity.this, AlterUserNameActivity.class);
                intent.putExtra("nickName",nickName);
                startActivity(intent);*/
                showNameDialog();
                break;
            case R.id.rl_userinfo4:
                showSexDialog();
                //选择性别
                break;
            case R.id.rl_userinfo5:
                showDateDialog();
                break;
            case R.id.rl_userinfo6:
                startActivity(new Intent(UserInfoActivity.this,AlterPassActivity.class));
                break;
            case R.id.btn_exit:
                SharedPreferences sp = getSharedPreferences("login",UserInfoActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("loginState", false);
                //editor.clear();
                editor.commit();
                startActivity(new Intent(UserInfoActivity.this,MainActivity.class));
                //finish();
                break;
        }

    }


    //从服务器加载用户所有信息
    private void getAllInfoFromWeb(int user_id) {
        Log.e("用户ID", user_id + "");
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET,
                GlobalContacts.VISON_URL + "/userServlet?method=getAllInfo&user_id=" + user_id,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Gson gson = new Gson();
                        User user = gson.fromJson(result,User.class);
                        Log.e("user",user.toString());
                        //设置头像
                        photoUrl = user.getUser_image();
                        BitmapUtils bitmapUtils = new BitmapUtils(UserInfoActivity.this);
                        bitmapUtils.display(imageView, GlobalContacts.VISON_URL + photoUrl);
                        //设置电话
                            tv_phoneInfo.setText(user.getUser_mobile());
                        //设置昵称
                        if (!user.getUser_name().isEmpty()) {
                            tv_nameInfo.setText(user.getUser_name());
                        }
                        //设置性别
                        if (!user.getUser_sex().isEmpty()) {
                            tv_sexinfo.setText(user.getUser_sex());
                        }
                        //设置生日
                        if (!user.getUser_birth().isEmpty()) {
                            tv_birthinfo.setText(user.getUser_birth());
                            //
                        }
                    }

                    //图片路径加载失败
                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.e("加载图片失败", "气死了。。。。。。。");
                    }
                });
    }

    private void showNameDialog() {
        final Message msg = new Message();
        View view = View.inflate(UserInfoActivity.this,R.layout.layout_username_dialog,null);
        Button btn_dialogName = (Button) view.findViewById(R.id.btn_dialogName);
        final EditText et_newName = (EditText) view.findViewById(R.id.et_dialogName);
        final AlertDialog dialog = new AlertDialog.Builder(UserInfoActivity.this).setView(view).show();
        btn_dialogName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String newName = et_newName.getText().toString();
                setUserName(newName);
                msg.what = 4;
                msg.obj = newName;
                handler.sendMessage(msg);
            }
        });
    }

    private void setUserName(String newName) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method","setUserName");
        params.addQueryStringParameter("user_name",newName);
        params.addQueryStringParameter("user_id",user_id+"");
        String url = GlobalContacts.VISON_URL + "/userServlet";
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(UserInfoActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });

    }

    private void showSexDialog() {
        final Message msg = new Message();
        new AlertDialog.Builder(this)
                .setItems(sexItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                msg.what = 1;
                                msg.obj = "男";
                                handler.sendMessage(msg);
                                setUserSex("男");
                                break;
                            case 1:
                                msg.what = 2;
                                msg.obj = "女";
                                handler.sendMessage(msg);
                                setUserSex("女");
                                break;
                            case 2:
                                msg.what = 3;
                                msg.obj = "保密";
                                handler.sendMessage(msg);
                                setUserSex("保密");
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void setUserSex(String sex) {
        //http://localhost:8080/SweetHouse/userServlet?method=setUserSex&user_sex=woman&user_id=123458
       // http://localhost:8080/SweetHouse/userServlet?method=setUserBirth&user_birth=1991.8.12&user_id=123458
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method","setUserSex");
        params.addQueryStringParameter("user_sex",sex);
        params.addQueryStringParameter("user_id",user_id+"");
        String url = GlobalContacts.VISON_URL + "/userServlet";
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(UserInfoActivity.this, "性别修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(UserInfoActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                        handler.sendMessage(msg);
                        String birth =  year + "." + (month + 1) + "." + dayOfMonth + ".";
                        setUserBirth(birth);
                        Toast.makeText(UserInfoActivity.this, year + "年" +(month + 1) + "月" + dayOfMonth + "日", Toast.LENGTH_SHORT).show();
                    }
                }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void setUserBirth(String birth) {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("method","setUserBirth");
        params.addQueryStringParameter("user_birth",birth);
        params.addQueryStringParameter("user_id",user_id+"");
        String url = GlobalContacts.VISON_URL + "/userServlet";
        httpUtils.send(HttpRequest.HttpMethod.GET, url, params, new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                Toast.makeText(UserInfoActivity.this, "生日修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }


    /**
     * 显示选择头像设置对话框
     */
    private void showPhotoDialog() {

        new AlertDialog.Builder(this)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent1, CAMERA_REQUEST_CODE);
                                break;
                            case 1:
                                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                                intent2.setType("image/*");
                                startActivityForResult(intent2, IMAGE_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {

            if (data == null) {
                return;
            } else {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitMap = extras.getParcelable("data");
                    Uri uri = savaBitMap(bitMap);
                    startPhotoZoom(uri);
                }
            }
        } else if (requestCode == IMAGE_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Uri uri = data.getData();
            //这时候的uri是以content开头的，不能直接使用，要转换为file：开头的Uri
            startPhotoZoom(convertUri(uri));
            //Toast.makeText(UserInfoActivity.this, convertUri(uri).toString(), Toast.LENGTH_SHORT).show();
        } else if (requestCode == RESIZE_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            //裁剪成功
            Bundle extras = data.getExtras();
            Bitmap bitmap = extras.getParcelable("data");
            //将剪切以后的图片变换成圆形
            Bitmap roundBitMap = toRoundBitmap(bitmap);
            //将裁剪后的头像缓存到本地
            savaphoto(roundBitMap);
            //将头像上传到服务器
            sendToWeb(roundBitMap);
            //将user_info页的头像设为圆形头像
            imageView.setImageBitmap(roundBitMap);
        }

    }

    //将裁剪后的图片上传到服务器
    /*
    * 服务器端还未实现
    * */
    private void sendToWeb(Bitmap bitmap) {

    }

    //将截取后的图片缓存到本地
    private void savaphoto(Bitmap bitmap) {
        photoDir = new File(Environment.getExternalStorageDirectory() + "/com.sweethouse.photo");
        if (!photoDir.exists()) {
            photoDir.mkdir();
        }
        File photo = new File(photoDir.getAbsolutePath() + "/" + user_id+".png");

        try {
            FileOutputStream fos = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //选择本地图片要进行路径的转换
    private Uri convertUri(Uri uri) {
        InputStream is = null;
        //以content开头的转化为BitMap文件
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            //BitMap文件写入到sd卡中，返回file开头的uri
            return savaBitMap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //将content:  uri的bitMap缓存到本地，转化为file： uri，再去裁剪，得到裁剪后的uri
    private Uri savaBitMap(Bitmap bm) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/com.sweethouse.avater");
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }
        File image = new File(tmpDir.getAbsolutePath() + "/" + "avater.png");
        try {
            FileOutputStream fos = new FileOutputStream(image);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(image);
    }

    /**
     * 裁剪图片方法实现
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, RESIZE_REQUEST_CODE);
    }


    // 转换图片成圆形

    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
