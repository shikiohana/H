package com.example.administrator.changhong;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.http.RequestParams;
import org.xutils.x;

import model.ChanghongUser;

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
     * 125.86.59.3
     * @param sex  性别
     */
    private void registerForSex(String sex){




    }

    public String getT(EditText editText){
        return userName.getText().toString();

    }



}
