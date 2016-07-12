package com.example.administrator.changhong;

import android.app.Activity;
import android.content.Intent;
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
                    Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    break;
               default:
                   break;
        }
    }
    };
}
