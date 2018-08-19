package com.user;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.login.LoginActivity;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by 枫 on 2018/8/12.
 */

public class MaxStringCallback extends StringCallback {


    public Context concext;
    public My my;

    public MaxStringCallback(StringCallback stringCallback) {
        super();
    }

    //启动前
    @Override
    public void onBefore(okhttp3.Request request, int id){
    }
    //启动后
    @Override
    public void onAfter(int id)
    {
    }
    public MaxStringCallback(Context concext,My my){
         super();
        this.concext = concext;
        this.my = my;
    }
    //出错
    @Override
    public void onError(Call call, Exception e, int id)
    {
        e.printStackTrace();
//            login_button_name.setText("onError:" + e.getMessage());
        Toast.makeText(concext, "网络异常",Toast.LENGTH_SHORT).show();
    }
    //完成
    @Override
    public void onResponse(String response, int id)
    {

        my.onResponse(response,id);
    }

    public  interface My{
        public abstract void onResponse(String response, int id);

    }

    //进度
    @Override
    public void inProgress(float progress, long total, int id)
    {
//        Log.e("sss", "inProgress:" + progress);
//        mProgressBar.setProgress((int) (100 * progress));
    }
}


