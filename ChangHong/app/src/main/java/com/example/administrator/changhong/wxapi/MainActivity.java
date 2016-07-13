package com.example.administrator.changhong.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.changhong.R;
import com.example.administrator.changhong.RegisterActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends Activity {
    ImageView back,personal,weixin;
    TextView privacy,binding,register;
    private IWXAPI iwxapi;
    private static final String APP_ID="wx539256bab11005a7";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //通过WXAPIFactory获取IWXAPI的实例
        iwxapi= WXAPIFactory.createWXAPI(getApplicationContext(), APP_ID, true);
        //将应用appid注册到微信
        iwxapi.registerApp(APP_ID);
        inni();
    }

    /**
     * 初始化控件
     */
    private void inni(){
        back=(ImageView)findViewById(R.id.top_left);
        personal=(ImageView)findViewById(R.id.top_right);
        weixin=(ImageView)findViewById(R.id.weixin_login);
        privacy=(TextView)findViewById(R.id.privacy);
        binding=(TextView)findViewById(R.id.binding);
        register=(TextView)findViewById(R.id.register);


        back.setOnClickListener(clickListener);
        personal.setOnClickListener(clickListener);
        privacy.setOnClickListener(clickListener);
        binding.setOnClickListener(clickListener);
        register.setOnClickListener(clickListener);
        weixin.setOnClickListener(clickListener);
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
                    Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.weixin_login://微信第三方登陆
                    Log.i("clicked","ssss");
                    weixinLogin();
                    iwxapi.handleIntent(getIntent(),iwxapiEventHandler);
                    break;
               default:
                   break;
        }
    }
    };

    /**
     * 重写Activity的方法
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        iwxapi.handleIntent(intent, iwxapiEventHandler);
    }
    /**
     *
     */
    IWXAPIEventHandler iwxapiEventHandler=new IWXAPIEventHandler() {


        @Override
        public void onReq(BaseReq baseReq) {

        }


        // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法

        @Override
        public void onResp(BaseResp baseResp) {
            int result = 0;
            Log.i("BaseResp",baseResp.errCode+"");
            switch (baseResp.errCode) {

                case BaseResp.ErrCode.ERR_OK:
                    result = R.string.errcode_success;
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = R.string.errcode_cancel;
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = R.string.errcode_deny;
                    break;
                default:
                    result = R.string.errcode_unknown;
                    break;

            }

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

        }

    };


    private void weixinLogin(){
        Log.i("Login","ssss");
        // 发送oauth请求
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        iwxapi.sendReq(req);
       // iwxapi.openWXApp();
    }
}
