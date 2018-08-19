package com.add_attention;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.login.LoginActivity;
import com.test.banner.R;
import com.test.left.userview.UserViewActivity;
import com.test.middle.AttentionChapterActivity;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class AttentionRecyclerViewAdapter extends RecyclerView.Adapter<AttentionRecyclerViewAdapter.MyViewHodler> {


    public  boolean pressed = true;

    public int viewnum=0;
    private AttentionAcitvity context;
    private ArrayList<String> datas;
    private  ArrayList<String> views;
    private  ArrayList<ImageView> viewlist;
    public static  int NUM_I=0;

    public ArrayList<String> id;
    public ArrayList<Boolean> booleanlist;
    public AttentionRecyclerViewAdapter(AttentionAcitvity context, ArrayList<String> datas, ArrayList<String> views,ArrayList<String> id){
        this.context = context;
        this.datas = datas;
        this.views = views;
        this.viewlist = new ArrayList<ImageView>();
        this.booleanlist = new ArrayList<Boolean>();
        this.id = id;
    }

    class MyViewHodler extends RecyclerView.ViewHolder{
        private ImageView iv_ic;
        private TextView tv_title;
        private ImageView iv_ic_top;
        public boolean booleanview;



        public MyViewHodler(View itemView) {
            super(itemView);

                iv_ic = (ImageView) itemView.findViewById(R.id.home_attention_im);
                tv_title = (TextView)itemView.findViewById(R.id.home_attention_tv);
                iv_ic_top = (ImageView) itemView.findViewById(R.id.home_attention_top_im);
                iv_ic_top.setVisibility(View.INVISIBLE);
                booleanview = false;
                booleanlist.add(booleanview);

        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }


    @Override
    public void onBindViewHolder(final AttentionRecyclerViewAdapter.MyViewHodler hodler,final int position) {

        String data = datas.get(position);
        hodler.tv_title.setText(datas.get(position)+" ");
        if(position==datas.size()-1)
            hodler.iv_ic.setBackgroundResource(Integer.parseInt(views.get(position)));
        else
            Glide.with(context).load(AccountStatic.ImageUrl+views.get(position)).placeholder(R.mipmap.ic_below)
                    .error(R.mipmap.ic_hone_new_2).bitmapTransform(new
                    RoundedCornersTransformation(context,30,0,RoundedCornersTransformation.CornerType.BOTTOM)).
                    crossFade(1000).into(hodler.iv_ic);


        if(!a) {
            while (b < viewlist.size()) {
                viewlist.remove(b);
                if(b!=0)
                Log.e("sss", viewlist.size() + "-->" + viewlist.get(b-1).toString());
            }a=true;
        }
        viewlist.add(hodler.iv_ic_top);
        Log.e("sss",datas.size() +"||||"+viewlist.size() + "-->" + hodler.iv_ic_top.toString());



        hodler.iv_ic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                if(pressed){
                    if(position<datas.size()-1) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setClass(context, AttentionChapterActivity.class);
                        intent.putExtra("id",id.get(position));
                        intent.putExtra("title",datas.get(position));
                        intent.putExtra("a",1+"");
                        context.startActivity(intent);
                    }else{
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setClass(context, AddAttention.class);
                        context.startActivity(intent);
                    }
                }else{
                    if(position<datas.size()-1) {
                        booleanlist.set(position, !booleanlist.get(position));
                        booleanView(booleanlist.get(position), hodler.iv_ic_top);
                        Log.e("sss","104-->"+booleanlist.get(position)+"---"+viewlist.get(position).toString());
                    }
                }
            }

        });
        hodler.iv_ic.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View v) {
                if(position<datas.size()-1) {
                    viewnum = 0;
                    if (pressed) {
                        context.fab.setVisibility(View.VISIBLE);
                        for (int i = 0; i < viewlist.size() - 1; i++) {
                            viewlist.get(i).setVisibility(View.VISIBLE);
                            booleanlist.set(i, false);
                            booleanView(booleanlist.get(i), viewlist.get(i));
                        }
                    } else {
                        context.fab.setVisibility(View.INVISIBLE);
                        for (int i = 0; i < views.size() - 1; i++) {
                            viewlist.get(i).setVisibility(View.INVISIBLE);
                        }
                    }
                    pressed = !pressed;
                }else{

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setClass(context, AttentionChapterActivity.class);

                    context.startActivity(intent);


                }


                return true;

            }
         });
     }


    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup arg0, int arg1) {

        View itemView = View.inflate(context,
                R.layout.home_middle_recyclerview, null);
        return new AttentionRecyclerViewAdapter.MyViewHodler(itemView);
    }

       private void booleanView(boolean booleanimage,ImageView imageview){
        if(booleanimage){
            imageview.setImageResource(R.mipmap.ic_user_add);
            Log.v("sss","add-->"+imageview.toString());
            viewnum++;

        }
        else{
            imageview.setImageResource(R.mipmap.ic_user_delete);
//            Log.v("sss","delete-->"+imageview.toString());
            viewnum--;
            if(viewnum<0)
                viewnum = 0;
        }

    }


    public void setadd(ArrayList<String> a,ArrayList<String> b,ArrayList<String> c){
        datas = new ArrayList<String>();
        views = new ArrayList<String>();
        viewlist = new ArrayList<ImageView>();
        booleanlist =new ArrayList<Boolean>();
        id = new ArrayList<String>();
        notifyItemRemoved(0);
        for(int i=0;i<a.size();i++)
        addData(i,a.get(i),b.get(i),c.get(i));
    }
    /**
     * 添加数据
     * @param position
     *
     */
    public void addData(int position,String a,String b,String c){

        datas.add(position,a);
        views.add(position,b);
        id.add(c);
        notifyItemInserted(position);
    }
    /**
     * 移除数据
     * @param i
     */
    public void removeData(int i){
        initOkhttp(id.get(i));

        id.remove(i);

        datas.remove(i);
        views.remove(i);
        booleanlist.remove(i);
        notifyItemRemoved(i);
        notifyItemRangeChanged(i,datas.size()-i);

    }

   public void delete(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Message msg = Message.obtain();
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


         }
    boolean a= true;
    int b = -1;

    private void initDokhttp(String id){

            Map<String,String> params = new HashMap<String,String>();
            params.put("id",id);
            String url = "DeleteMyWorkAttention";
            new AccountStatic().initOkhttp(context,params,url,false,null);
        }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<String> aa = new ArrayList<String>();
            for(int i=0;i<booleanlist.size();i++){
                       if(booleanlist.get(i)) {
                           aa.add(id.get(i));
                       }
            }
            for(String aaa:aa)
                initDokhttp(aaa);

            for(int i=0;i<booleanlist.size();i++) {
                if(booleanlist.get(i)){
                    removeData(i);
                    if(a){
                        b = i;
                        a = false;
                    }
                    i--;
          }
            }
            chag();
        }
};
     public void chag(){

             context.fab.setVisibility(View.INVISIBLE);
             for (int i = 0; i < viewlist.size()-1; i++) {
                 viewlist.get(i).setVisibility(View.INVISIBLE);
//                 Log.e("sss",i+"ii-->"+viewlist.get(i).toString());
             }
              pressed =true;
       }

    public void initOkhttp(String i){

    }
}
