package com.example.administrator.quickweibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.model.AccessTokenKeeper;
import com.example.administrator.quickinterface.Constants;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class MainActivity extends Activity {
    TextView textView;
    AuthInfo mAuthInfo;
    Oauth2AccessToken mAccessToken;
    SsoHandler mSsoHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (AccessTokenKeeper.readAccessToken(this) != null) {
            Intent intent = new Intent(MainActivity.this, WeiBoActivity.class);
            startActivity(intent);
            finish();
        } else {
            inni();
        }
    }

    /**
     * 初始化
     */
    private void inni() {
        textView = (TextView) findViewById(R.id.weibo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weiboLogin();
            }
        });
    }

    /**
     * 登陆微博
     */
    private void weiboLogin() {
        mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
                Constants.REDIRECT_URL, Constants.SCOPE);
        mSsoHandler = new SsoHandler(MainActivity.this, mAuthInfo);//创建SsoHandler对象
        mSsoHandler.authorize(new AuthListener());

    }


    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
            mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从 Bundle 中解析 Token
            if (mAccessToken.isSessionValid()) {
                Log.i("token", mAccessToken.toString());
                AccessTokenKeeper.writeAccessToken(MainActivity.this, mAccessToken); //保存Token

            } else {
                // 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
                String code = values.getString("code", "");

            }
        }

        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
            Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, WeiBoActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
