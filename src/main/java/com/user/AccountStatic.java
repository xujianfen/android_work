package com.user;


import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.add_attention.AddAttention;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.*;

import java.util.HashMap;
import java.util.Map;

public class AccountStatic {
    public static Account  account;
    public AccountStatic(Account account){
    	this.account = account;
    }
    public AccountStatic(){}
    public static final String URSE_HTTP = "http://192.168.43.225:8080/day11_05_jstl/servlet/";
    public static final String URSE_HTTP2= "http://192.168.43.225:8080/FileUpload/upload/";
    public static final String URSE_HTTP3= "http://192.168.43.225:8080/FileUpload/";
    public static final String mBaseUrl = "http://192.168.43.225:8080/FileUpload/FileUploadServlet";
    public static final String ImageUrl = "http://192.168.43.225:8080/FileUpload/upload/";
    public static  final int PARSONAL = 0;
    public static  final int USERVIEWACTIVITY = 1;
    public static final String A = "192.168.43.225";
    public static final String B = "192.168.137";

    public static boolean s_boolean(EditText ed){
        String s = ed.getText().toString();
        return s_boolean(s);
    }

    public static boolean s_boolean(String s){

        if(s==null||s.equals("")||s.trim().isEmpty())
            return false;
        else
            return true;
    }
    Map<String,String> params;
    Context context;

     public String s ;
    public  void initOkhttp(final Context context, Map<String, String> params, String url, final boolean b, final View view){
             url = this.URSE_HTTP+url;
             this.params = params;
             com.zhy.http.okhttp.OkHttpUtils.post().url(url).params(params).build().execute(new MaxStringCallback(context, new MaxStringCallback.My() {
                     @Override
                     public void onResponse(String s,int i) {

                         if(b)
                             Snackbar.make(view, "添加成功", Snackbar.LENGTH_INDEFINITE).show();
                         else
                             Toast.makeText(context,"访问成功",Toast.LENGTH_SHORT).show();
                         Log.e("sss","s = "+s);
                         AccountStatic.this.s= s;
                         get();
                     }
            }));

            return;
        }
    public static AccountStatic get(){
        return  new AccountStatic();
    }
}
