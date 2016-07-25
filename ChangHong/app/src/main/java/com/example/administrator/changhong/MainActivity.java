package com.example.administrator.changhong;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {
    ImageView back,personal;
    TextView privacy,binding,register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inni();
    }

    /**
     * 初始化控件
     */
    private void inni(){
        back=(ImageView)findViewById(R.id.top_left);
        personal=(ImageView)findViewById(R.id.top_right);
        privacy=(TextView)findViewById(R.id.privacy);
        binding=(TextView)findViewById(R.id.binding);
        register=(TextView)findViewById(R.id.register);


        back.setOnClickListener(clickListener);
        personal.setOnClickListener(clickListener);
        privacy.setOnClickListener(clickListener);
        binding.setOnClickListener(clickListener);
        register.setOnClickListener(clickListener);

    }

    /**
     * 点击事件
     */
    View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.top_left://左上返回
                    break;
                case R.id.top_right://右上
                    break;
                case R.id.privacy://隐私
                    break;
                case R.id.binding://绑定
                    break;
                case R.id.register://注册
                    MyTask task=new MyTask();
                    task.execute("");
                    uxtilsRequest();
                   /* Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);*/
                    break;
               default:
                   break;
        }
    }
    };

    class MyTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            binding.setText(s);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String str=request(httpUrl,httpArg);
            return str;
        }
    }


    String httpUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";
    String httpArg = "cityname=%E5%8C%97%E4%BA%AC";
    String jsonResult = request(httpUrl, httpArg);


    /**
     * @param
     *            :请求接口
     * @param httpArg
     *            :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "801c5e1aca87ed2ba21840465887ae68");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    private void uxtilsRequest(){
        RequestParams params=new RequestParams("http://apis.baidu.com/apistore/weatherservice/cityname?cityname=%E5%8C%97%E4%BA%AC");
        params.setHeader("apikey",  "801c5e1aca87ed2ba21840465887ae68");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                privacy.setText(result);
                Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
