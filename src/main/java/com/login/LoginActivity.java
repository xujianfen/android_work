package com.login;



import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;



import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.test.HomeActivity;
import com.test.banner.R;
import com.user.Account;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.InjectView;
import okhttp3.Call;

public class LoginActivity extends FinalActivity {
    @ViewInject(id= R.id.login_button_name,click="onclick")
    private Button login_button_name ;
    @ViewInject(id=R.id.login_ed_name)
    private EditText login_ed_name;
    @ViewInject(id=R.id.login_ed_password)
    private EditText login_ed_password;
    @ViewInject(id=R.id.login_tv_name)
    private TextView login_tv_name;
    @ViewInject(id=R.id.login_tv_password)
    private TextView login_tv_password;
    @ViewInject(id=R.id.mProgressBar)
    private ProgressBar mProgressBar;
    @ViewInject(id=R.id.register_button_name,click="on")
    private Button button ;
    private String idAccount="2";
    private String password="3";
    private String error = "-1";

    private static final MediaType JSON = MediaType.parse("aqqlicaton/json;charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);
        mProgressBar.setMax(100);

    }
    /**
     * 点击事件
     * */
    public void on(View view){
        buttonl(false);
    }

    public void onclick(View view){
        buttonl(true);
    }
    public void buttonl(boolean b){
        if(login_ed_name.getText()!=null&&!login_ed_name.getText().toString().equals("")&&!login_ed_name.getText().toString().trim().isEmpty()){

            if(login_ed_password.getText()!=null&&!login_ed_password.getText().toString().equals("")&&!login_ed_password.getText().toString().trim().isEmpty()){

               getDataFromByPost(b);

            }
            else{

                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();

            }

        }
        else{

                Toast.makeText(LoginActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();

        }

    }

    /**
     * 点击事件内容
     * */
    private void inlogin(String name,boolean b) {
        if(b) {
            if (name == null || name.equals(error)) {
                Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!name.equals(idAccount)) {

                if (!name.equals(password)) {

                    Log.e("sss", name);
                    Gson gson = new Gson();
                    Account account = gson.fromJson(name, Account.class);
                    AccountStatic accountStatic = new AccountStatic(account);
                    Log.e("sss", account.toString());
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
//                         intent.putExtra("id", myid.get(position));
//                         intent.putExtra("a",""+1);
                    intent.setClass(LoginActivity.this, HomeActivity.class);
                    LoginActivity.this.startActivity(intent);
                } else {

                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();

                }
            } else {

                Toast.makeText(LoginActivity.this, "该账号不存在", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            if (name == null || name.equals(error)) {
                Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                return;
            }
            if (name.equals("2")) {
                Toast.makeText(LoginActivity.this, "该账号以存在", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(LoginActivity.this, "注册成功",Toast.LENGTH_SHORT).show();
            }
        }

        }





    /**
     *@ post..
     */
    private void getDataFromByPost(boolean b) {
        String url;

         url = AccountStatic.URSE_HTTP+"Login";

        Map<String,String> params = new HashMap<String,String>();
        params.put("name",login_ed_name.getText().toString());
        params.put("password",login_ed_password.getText().toString());
        params.put("boolean",b+"");
        MyStringCallback m = new MyStringCallback();
        m.b = b;
        OkHttpUtils.post().url(url).params(params).build().execute(m);
  }


    /**
     * post请求
//     * @param url
//     * @param json
     * @return
     * @throws IOException
     */

    public class MyStringCallback extends StringCallback
    {

        public boolean b;
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
            Toast.makeText(LoginActivity.this, "网络异常",Toast.LENGTH_SHORT).show();
        }
        //完成
        @Override
        public void onResponse(String response, int id)
        {
            inlogin(response,b);
        }
        //进度
        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e("sss", "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }

}