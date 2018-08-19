package com.test.left;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.parsonal.Parsonal;
import com.test.banner.R;
import com.test.left.userview.MyRecyclerViewAdapter;
import com.test.left.userview.UserViewActivity;
import com.user.Account;
import com.user.AccountStatic;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class LeftMenu extends Fragment{
    private View v;
    private Activity a;
    private ArrayList<String> datas;
    private ArrayList<Integer> views;
    private RecyclerView recyclerview;
    private MyRecyclerViewAdapter adapter;
    private ImageView imageView;
    private TextView titleUsername;
//    private Bitmap image;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,  Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.left, container,false);
        imageView = (ImageView)v.findViewById(R.id.home_left_im_hone);
        titleUsername =   (TextView)v.findViewById(R.id.home_left_tv);
        titleUsername.setText(AccountStatic.account.getUsername());
//        image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //bitmap转化为byte数组传递
                Intent intent = new Intent();
                intent.setClass(a,UserViewActivity.class);
                startActivityForResult(intent, AccountStatic.USERVIEWACTIVITY);
//                a.startActivity(intent);
           }
        });
        initData();
        viewGlide();
        recyclerview = (RecyclerView)v.findViewById(R.id.home_left_re);

        //设置Recyclerview的适配器
        adapter = new MyRecyclerViewAdapter(a,datas,views);
        recyclerview.setAdapter(adapter);
        //显示recyclerview  下面为（context，垂直方向 ，不倒序）
        recyclerview.setLayoutManager(new LinearLayoutManager(a,LinearLayoutManager.VERTICAL,false));
        return v;

    }

    /**
     *
     * 显示头像
     *
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Glide.with(a).load(msg.obj).placeholder(R.mipmap.ic_hone).error(R.mipmap.ic_hone).bitmapTransform(new RoundedCornersTransformation(a,30,0,RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(imageView);

        }

    };
    public void viewGlide(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Message msg = Message.obtain();
                    msg.obj =  AccountStatic.ImageUrl+AccountStatic.account.getHead();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    /**
     *
     * 为recyclerview设置内容
     *
     */
    private void initData() {

            datas = new ArrayList<String>();
            views = new ArrayList<Integer>();
            datas.add("资料管理");
            views.add(R.mipmap.ic_menu_paste_holo_light);
            datas.add("我的作品");
            views.add(R.mipmap.ic_menu_feedback);
            datas.add("下载更新");
            views.add(R.mipmap.ic_menu_settings);
//            //准备数据集合
//            for(int i=0;i<100;i++){
//                datas.add(i+"");
//            }

        }


    public void setActivity(Activity a){
        this.a =a;
    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//
//        Intent intent = new Intent();
//        switch(position){
//              case 0:
//
//                  intent.setClass(a,Parsonal.class);
//                  startActivityForResult(intent, AccountStatic.PARSONAL);
////                  a.startActivity(intent);
//
//                  break;
//              case 1:
//
//                  intent.setClass(a,Parsonal.class);
//                  a.startActivity(intent);
//                break;
//              case 2:
//                  break;
//         }

//    }

//       public void setImageView(){
//
//       }
      public  void setImage_title(){
          titleUsername.setText(AccountStatic.account.getUsername());
//          viewGlide();
          Glide.with(a).load(AccountStatic.ImageUrl+AccountStatic.account.getHead()).placeholder(R.mipmap.ic_hone).error(R.mipmap.ic_hone).bitmapTransform(new RoundedCornersTransformation(a,30,0,RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(imageView);

      }
}
