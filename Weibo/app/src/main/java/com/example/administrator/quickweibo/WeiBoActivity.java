package com.example.administrator.quickweibo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.model.AccessTokenKeeper;
import com.example.administrator.model.Weibo;
import com.example.administrator.quickinterface.ApiService;
import com.example.administrator.quickinterface.Constants;
import com.example.administrator.quickinterface.Statuses;
import com.example.administrator.utils.HttpUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;




import org.xutils.http.RequestParams;
import org.xutils.x;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/25.
 */
public class WeiBoActivity extends Activity {
    private Oauth2AccessToken mToken;
    private RecyclerView recyclerView;
    private TextView username;
    private String token;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo);
        inni();

    }




    private void inni(){
        username=(TextView)findViewById(R.id.username);
        recyclerView=(RecyclerView)findViewById(R.id.recycle);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // mGetF();
              //  xget();
                getFriendInfo();
            }
        });
        mToken= AccessTokenKeeper.readAccessToken(this);
        token=mToken.getToken();

    }


    private void getFriendInfo(){
        Retrofit retrofit=new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(Constants.BASE_URL_STATUSES).build();
        service=retrofit.create(ApiService.class);

        Call<String> call=service.loadFriends();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String str=response.body();
                Log.i("call",call.toString());
                Log.i("response", response.body());
                username.setText(str);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i("response", call.toString());
                Toast.makeText(WeiBoActivity.this, "失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void mGetF(){
        MyTask task=new MyTask();
        task.execute("");
    }


    class MyTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            Log.i("s",s);
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String result= HttpUtils.get(Constants.BASE_URL_STATUSES+Statuses.FRIENDS,"access_token",token);
            return result;
        }
    }

    private void xget(){
        RequestParams params=new RequestParams(Constants.BASE_URL_STATUSES+Statuses.FRIENDS);
        params.addParameter("access_token",token);
       Log.i("http",params.toString());
        x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                //Toast.makeText(WeiBoActivity.this, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage()+"11", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinished() {
                Toast.makeText(x.app(), "完成", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
