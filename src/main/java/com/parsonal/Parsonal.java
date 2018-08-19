package com.parsonal;



import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.login.LoginActivity;
import com.test.banner.R;
import com.user.Account;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class Parsonal extends AppCompatActivity {

//    private int id;//id 1
    @InjectView(R.id.parsonal_username_et)
    public EditText usetname_et;//
    @InjectView(R.id.parsonal_password_et)
    public EditText password_et;//密码 3
    @InjectView(R.id.parsonal_phone_et)
    public EditText phone_et;//手机号 4
    @InjectView(R.id.radioButton_men)
    public RadioButton gender_men;//男14
    @InjectView(R.id.radioButton_man)
    public RadioButton gender_man;//女 14
    @InjectView(R.id.parsonal_introduce_et)
    public EditText introduce_et;//介绍 15
    @InjectView(R.id.parsonal_industry_et)
    public EditText industry_et;//工作室 16
    @InjectView(R.id.imageButton)
    public ImageView  imagebuttom;
    @InjectView(R.id.imageButton1)
    public ImageView imagebuttom1;
    @OnClick(R.id.imageButton1)
    void butotn1(){
        finish();
    }
    @OnClick(R.id.imageButton)
    void button(){
        initOkhttp();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsonal);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initText();

    }
    public void initOkhttp(){
        String url = AccountStatic.URSE_HTTP+"Parsonal";
        Map<String,String> params = new HashMap<String,String>();
        params.put("id",AccountStatic.account.getId()+"");
        if(AccountStatic.s_boolean(usetname_et))
            params.put("username",usetname_et.getText().toString());
        else
            params.put("username",AccountStatic.account.getUsername());

        if(AccountStatic.s_boolean(password_et))
            params.put("password",password_et.getText().toString());
        else
            params.put("password",AccountStatic.account.getPassword())
                    ;
        if(AccountStatic.s_boolean(phone_et))
            params.put("phone",phone_et.getText().toString());
        else
            params.put("phone",AccountStatic.account.getPhone());

        if(AccountStatic.s_boolean(introduce_et))
            params.put("introduce",introduce_et.getText().toString());
        else
            params.put("introduce",AccountStatic.account.getIntroduce());

        if(AccountStatic.s_boolean(industry_et))
            params.put("industry",industry_et.getText().toString());
        else
            params.put("industry",AccountStatic.account.getIndustry());

        if(gender_man.isChecked())
            params.put("gender","女");
        else if(gender_men.isChecked())
            params.put("gender","男");

        OkHttpUtils.post().url(url).params(params).build().execute(new Parsonal.MyStringCallback());
    }
    public void initText(){
        Account account = AccountStatic.account;
        usetname_et.setHint(account.getUsername());
        password_et.setHint(account.getPassword());
        phone_et.setHint(account.getPhone());
        introduce_et.setHint(account.getIntroduce());
        industry_et.setHint(account.getIndustry());
        if(account.getGender()!=null&&account.getGender().equals("男")){
            gender_man.setChecked(false);
            gender_men.setChecked(true);
        }else if(account.getGender()!=null&&account.getGender().equals("女")){
            gender_man.setChecked(true);
            gender_men.setChecked(false);
        }
     }
    @OnClick(R.id.radioButton_man)
    void Man(View view){
            gender_man.setChecked(true);
            gender_men.setChecked(false);
    }

    @OnClick(R.id.radioButton_men)
    void Men(View view){
        gender_man.setChecked(false);
        gender_men.setChecked(true);

    }



    public class MyStringCallback extends StringCallback
    {
        //启动前
        @Override
        public void onBefore(okhttp3.Request request, int id){
        }
        //启动后
        @Override
        public void onAfter(int id)
        {
        }

        //出错
        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
//            login_button_name.setText("onError:" + e.getMessage());
            Toast.makeText(Parsonal.this, "网络异常",Toast.LENGTH_SHORT).show();
        }
        //完成
        @Override
        public void onResponse(String response, int id)
        {
            Toast.makeText(Parsonal.this, "修改成功",Toast.LENGTH_SHORT).show();
            if(AccountStatic.s_boolean(usetname_et))
            AccountStatic.account.setUsername(usetname_et.getText().toString());
            if(AccountStatic.s_boolean(password_et))
            AccountStatic.account.setPassword(password_et.getText().toString());
            if(AccountStatic.s_boolean(phone_et))
            AccountStatic.account.setPhone(phone_et.getText().toString());
            if(AccountStatic.s_boolean(industry_et))
            AccountStatic.account.setIndustry(industry_et.getText().toString());
            if(AccountStatic.s_boolean(introduce_et))
            AccountStatic.account.setIntroduce(introduce_et.getText().toString());
            if(gender_men.isChecked())
            AccountStatic.account.setGender("男");
            if(gender_man.isChecked())
            AccountStatic.account.setGender("女");
            Log.v("sss",AccountStatic.account.toString());
            finish();
        }



        //进度
        @Override
        public void inProgress(float progress, long total, int id)
        {
//            Log.e("sss", "inProgress:" + progress);
//            mProgressBar.setProgress((int) (100 * progress));
        }
    }

}
