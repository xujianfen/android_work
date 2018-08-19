package com.test.middle;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.banner.R;
import com.user.AccountStatic;

public class MiddleRecyclerViewAdapter extends RecyclerView.Adapter<MiddleRecyclerViewAdapter.MyViewHodler> {
    private ActionMode.Callback actionCallback;
    private ActionMode actionMode;
    private Activity a;
    public static boolean pressed = true;

    public int viewnum=0;
    private Context context;
    private ArrayList<String> datas;
    private ArrayList<String> views;
    private ArrayList<ImageView> viewlist;
    private  ArrayList<Integer> ids;
    private ArrayList<Boolean> booleans;
    public MiddleRecyclerViewAdapter(Context context,ArrayList<String> datas,ArrayList<String> views,ArrayList<Integer> ids){
        this.context = context;
        this.datas = datas;
        this.views = views;
        this.ids = ids;
        viewlist = new ArrayList<ImageView>();
        booleans = new ArrayList<Boolean>();
    }
    class MyViewHodler extends RecyclerView.ViewHolder{
        private ImageView iv_ic;
        private TextView tv_title;
        private ImageView iv_ic_top;
        public boolean booleanview;
         public MyViewHodler(View itemView){
            super(itemView);
            iv_ic = (ImageView) itemView.findViewById(R.id.home_attention_im);
            tv_title = (TextView)itemView.findViewById(R.id.home_attention_tv);
            iv_ic_top = (ImageView) itemView.findViewById(R.id.home_attention_top_im);
             iv_ic_top.setVisibility(View.INVISIBLE);
            booleanview = false;
            viewlist.add(iv_ic_top);
            booleans.add(booleanview);
        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHodler hodler, final int position) {

        String data = datas.get(position);
        hodler.tv_title.setText(data);
//        hodler.iv_ic.setImageResource(views.get(position));
        Glide.with(a).load(AccountStatic.ImageUrl+ views.get(position)).placeholder(R.mipmap.ic_image_null)
							.error(R.mipmap.ic_image_null).crossFade(1000).into(hodler.iv_ic);

        hodler.iv_ic.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                if(pressed){

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setClass(a,AttentionChapterActivity.class);
                    intent.putExtra("id",""+ids.get(position));
                    intent.putExtra("title",datas.get(position));
                     a.startActivity(intent);
                    Toast.makeText(context,""+position+" "+booleans.get(position),Toast.LENGTH_SHORT).show();

                }else{

                        booleans.set(position,!booleans.get(position));
                         Log.e("sss","boolean "+position+" = -->"+booleans.toString());
                            Toast.makeText(context,""+position+" "+booleans.get(position),Toast.LENGTH_SHORT).show();
                        booleanView(booleans.get(position),viewlist.get(position));

                }
                Toast.makeText(a,pressed+"",Toast.LENGTH_LONG).show();
            }

        });
        hodler.iv_ic.setOnLongClickListener(new View.OnLongClickListener(){

            public boolean onLongClick(View v) {
                pressed =false;
                viewnum = 0;
                 for(int i=0;i<viewlist.size();i++) {
                     viewlist.get(i).setVisibility(View.VISIBLE);
                     booleans.set(i,false);
                     booleanView(booleans.get(i),viewlist.get(i));
                 }
                if(actionMode!=null){

                    return false;
                }
                actionMode = a.startActionMode(actionCallback);
                v.setSelected(true);
                return true;

            }


        });

    }

    public void setActionMode(ActionMode actionMode, boolean pressed){

        this.actionMode = actionMode;
        this.pressed = pressed;
         for(int i=0;i<viewlist.size();i++)
            viewlist.get(i).setVisibility(View.INVISIBLE);

    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup arg0, int arg1) {

        View itemView = View.inflate(context,
                R.layout.home_middle_recyclerview, null);
        return new MyViewHodler(itemView);
    }






    private void booleanView(boolean booleanimage,ImageView imageview){
        if(booleanimage){
            imageview.setImageResource(R.mipmap.ic_user_add);
            viewnum++;
            Toast.makeText(a,viewnum+"",Toast.LENGTH_SHORT).show();

        }
        else{
            imageview.setImageResource(R.mipmap.ic_user_delete);
            viewnum--;
            if(viewnum<0)
                viewnum = 0;
            Toast.makeText(a,viewnum+"",Toast.LENGTH_SHORT).show();
        }

    }

    public void setactionMode(Activity a,ActionMode actionMode,ActionMode.Callback actionCallback){
        this.actionMode = actionMode;
        this.a = a;
        this.actionCallback = actionCallback;
    }



}
