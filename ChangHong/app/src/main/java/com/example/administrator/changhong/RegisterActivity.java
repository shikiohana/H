package com.example.administrator.changhong;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.xutils.http.HttpTask;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import changhonginterface.ChanghongApiService;
import model.ChanghongUser;
import model.HttpUtils;
import model.MyUtils;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2016/7/12.
 */
public class RegisterActivity extends Activity {
    EditText phone, pass, confirm, userName;
    RadioGroup sex;
    TextView register;
    RadioButton male;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inni();
    }

    /**
     * 初始化控件
     */
    private void inni() {
        phone = (EditText) findViewById(R.id.phone_number);
        pass = (EditText) findViewById(R.id.password);
        confirm = (EditText) findViewById(R.id.confirm);
        userName = (EditText) findViewById(R.id.user_name);
        sex = (RadioGroup) findViewById(R.id.sex);
        male = (RadioButton) findViewById(R.id.male);
        register = (TextView) findViewById(R.id.register_button);
        male.setChecked(true);//默认男性为选中
        register.setOnClickListener(clickListener);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (allNotEmpty()) {//是否有为输出的选项
                switch (sex.getCheckedRadioButtonId()) {//判断哪个性别被选中了
                    case R.id.female:
                        registerForSex("女");
                        break;
                    case R.id.male:
                        registerForSex("男");
                        break;
                    default:
                        break;
                }
            }
        }
    };


    private boolean allNotEmpty() {
        boolean empty = false;
        if (notEmpty(phone, "手机号不能为空")) {
            if (notEmpty(pass, "密码不能为空")) {
                if (pass.getText().toString().equals(confirm.getText().toString())) {
                    if (notEmpty(userName, "用户名不能为空")) {

                        empty = true;
                    }
                } else {
                    Toast.makeText(this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }
            }
        }
        return empty;
    }

    /**
     * 输入框是否为空
     *
     * @param editText
     * @param toast    为空时候需要显示的提示
     * @return
     */
    private boolean notEmpty(EditText editText, String toast) {

        String str = editText.getText().toString();
        if (str != null && !str.equals("")) {

            return true;

        }
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 注册
     * @param sex  性别
     */
    private void registerForSex(String sex){

        RequestParams params = new RequestParams("http://apis.baidu.com/apistore/weatherservice/cityname");
        params.addHeader("name", "value");
        params.addQueryStringParameter("name", "value");

// 只包含字符串参数时默认使用BodyParamsEntity，
// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params.addBodyParameter("name", "value");

// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
     //   params.addBodyParameter("file", new File("path"));


      /*  HttpTask http = new HttpTask(params, null, new org.xutils.common.Callback.CommonCallback() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });*/

        RequestParams params1 = new RequestParams("http://apis.baidu.com/apistore/weatherservice/cityname");
        HashMap<String,String > map=new HashMap<String, String>();
        map.put("MTel", getT(phone));
        map.put("Gender", sex);
        map.put("CNName", getT(userName));
        map.put("pwd", getT(pass));
        map.put("openID", "shikiohana");
       MyTask task=new MyTask();
        task.execute(map);
// 只包含字符串参数时默认使用BodyParamsEntity，
// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
        params1.addBodyParameter("name", "value");
        params1.addBodyParameter("MTel", getT(phone));
        params1.addBodyParameter("Gender", sex);
        params1.addBodyParameter("CNName", getT(userName));
        params1.addBodyParameter("pwd", getT(pass));
        params1.addBodyParameter("openID","shikiohana");
       x.http().get(params, new org.xutils.common.Callback.CommonCallback<ChanghongUser>() {
           @Override
           public void onSuccess(ChanghongUser result) {
                Log.i("result",result.toString());
               Toast.makeText(RegisterActivity.this, "chenggong", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onError(Throwable ex, boolean isOnCallback) {
               Toast.makeText(RegisterActivity.this, "错误", Toast.LENGTH_SHORT).show();
               Log.i("Error",ex.toString());
           }

           @Override
           public void onCancelled(CancelledException cex) {

           }

           @Override
           public void onFinished() {

           }
       });

        /*Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.1.252:8088/api/").build();
        ChanghongApiService service=retrofit.create(ChanghongApiService.class);
        ChanghongUser user=new ChanghongUser();

        user.setCnName(getT(userName));
        user.setGender(sex);
        user.setmTel(getT(phone));
        user.setPwd(getT(pass));
        user.setOpenId("111111");
        service.creatUser(user, new Callback<ChanghongUser>() {
            @Override
            public void onResponse(Response<ChanghongUser> response, Retrofit retrofit) {
                Log.i("Onrepse", response.toString());
                Toast.makeText(RegisterActivity.this,"成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(RegisterActivity.this,"失败",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    public String getT(EditText editText){
        return editText.getText().toString();

    }

    class MyTask extends AsyncTask<HashMap<String,String> ,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(RegisterActivity.this,s, Toast.LENGTH_SHORT).show();
            Log.i("result",s);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(HashMap<String,String>... strings) {
            HttpUtils utils=new HttpUtils();
            String json = maptoString(strings[0]);
          //  String str=utils.postJsonData("http://apis.baidu.com/apistore/weatherservice/cityname",json);
           String str=utils.postJsonData("http:///192.168.1.252:8088/api/reg",json);

            //String str=utils.submitPostData("http://192.168.1.252:8088/api/reg",strings[0],"UTF_8");
            return str;
        }
    };



    private String maptoString(HashMap<String,String> map){
        JSONObject jsonObject=new JSONObject();
        Set<String> set=map.keySet();
        for(String key : set){
            try {
                jsonObject.put(key,map.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }






        Log.i("json",jsonObject.toString());
        return jsonObject.toString();
    }
}
