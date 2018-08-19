package com.add_attention;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.attention.MyComics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.banner.R;
import com.test.middle.MiddleRecyclerViewAdapter;
import com.user.AccountStatic;
import com.user.MaxStringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.utils.Exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

import static com.test.banner.R.mipmap.a;

public class AttentionAcitvity extends AppCompatActivity {

    @InjectView(R.id.attention_rv)
    public RecyclerView recyclerview;
    @InjectView(R.id.toolbar_attention)
    public Toolbar toolbar;
    @InjectView(R.id.fab_attention)
    public FloatingActionButton fab;
    private ArrayList<String> datas;
    public ArrayList<String> ids;
    private ArrayList<String> views;
    private AttentionRecyclerViewAdapter  adapter;
    private ActionMode.Callback actionCallback;
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_acitvity);
        ButterKnife.inject(AttentionAcitvity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("我的作品");
        setTitleColor(R.color.recoveryTextColor);
//        toolbar.setCollapseIcon
//        toolbar.setLogo(R.mipmap.ic_hone_new_1);
        //返回按钮图片
//        toolbar.setNavigationIcon(R.mipmap.ic_finish);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "以选中" + adapter.viewnum + "项确定要删除？", Snackbar.LENGTH_LONG).setAction("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapter.delete();
                        Toast.makeText(AttentionAcitvity.this, "ss", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
        initOkhttp();
        initData();

        //设置Recyclerview的适配器
        initview();
    }
     public void initview(){  adapter = new AttentionRecyclerViewAdapter(AttentionAcitvity.this,datas,views,ids);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new GridLayoutManager(AttentionAcitvity.this,3, LinearLayoutManager.VERTICAL,false));
        fab.setVisibility(View.INVISIBLE);

    }

    public List<MyComics> mycomics;
      public void initOkhttp(){
          try {
              Map<String, String> params = new HashMap<String, String>();
              params.put("work_id", AccountStatic.account.getId() + "");

              OkHttpUtils.post().url(AccountStatic.URSE_HTTP+"SetMyComics").params(params).build().execute(new  StringCallback()
              {
                  @Override
                  public void onBefore(okhttp3.Request request, int id) {
                      super.onBefore(request, id);
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
                      Toast.makeText(AttentionAcitvity.this, "网络异常",Toast.LENGTH_SHORT).show();
                  }
                  //完成
                  @Override
                  public void onResponse(String response, int id)
                  {
                      Toast.makeText(AttentionAcitvity.this, "添加成功", Toast.LENGTH_SHORT).show();
                      Gson gson = new Gson();
                      mycomics = gson.fromJson(response,new TypeToken<List<MyComics>>() {
                      }.getType());
                      for(MyComics mm:mycomics)
                      Log.e("sss",mm.toString());
                      initData();
                      adapter.setadd(datas,views,ids);
                  }
                  //进度
                  @Override
                  public void inProgress(float progress, long total, int id)
                  {

                  }
              });

//              mycomics = gson.fromJson(AccountStatic.get().initOkhttp(AttentionAcitvity.this, params, , false, null),
//                      new TypeToken<List<MyComics>>() {
//                      }.getType());
//              for(MyComics mm:mycomics)
//              Log.e("sss",mm.toString());
//              initData();
          }
         catch (Exception e){
             e.printStackTrace();
             Toast.makeText(AttentionAcitvity.this,"网络异常",Toast.LENGTH_SHORT).show();
         }
      }



private void initData1(){
    ids = new ArrayList<String>();
    datas = new ArrayList<String>();
    views = new ArrayList<String>();
    if(mycomics!=null)
        for(MyComics m:mycomics){
            datas.add(m.getName());
            views.add(m.getCover());
            ids.add(m.getId());
        }




}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        initview();
    }

    private void initData() {
        ids = new ArrayList<String>();
        datas = new ArrayList<String>();
        views = new ArrayList<String>();
        if(mycomics!=null)
       for(MyComics m:mycomics){
           datas.add(m.getName());
           views.add(m.getCover());
           ids.add(m.getId());
       }
        datas.add(" ");
        views.add(R.mipmap.kg_add_widget_disabled+"");
             ids.add(-1+"");
    }

}
