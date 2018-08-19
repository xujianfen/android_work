package com.test.middle;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.banner.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHodler> {
    private ActionMode.Callback actionCallback;
    private ActionMode actionMode;
    private Activity a;
    public static boolean pressed = true;

    public int viewnum=0;
    private Context context;
    private ArrayList<String> datas;
    private ArrayList<Integer> views;
    public RecyclerViewAdapter(Context context,ArrayList<String> datas,ArrayList<Integer> views){
        this.context = context;
        this.datas = datas;
        this.views = views;
    }
    class MyViewHodler extends RecyclerView.ViewHolder{
        private ImageView iv_ic;
        private TextView tv_title;
        private ImageView iv_ic_top;
        public boolean booleanview;
        public MyViewHodler(View itemView){
            super(itemView);
            iv_ic = (ImageView) itemView.findViewById(R.id.home_left_im_hone);
            tv_title = (TextView)itemView.findViewById(R.id.home_left_tv);
//            iv_ic_top = (ImageView) itemView.findViewById(R.id.home_attention_top_im);
////            iv_ic_top.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return datas.size();
    }


    @Override
    public void onBindViewHolder(final MyViewHodler hodler, int position) {

        String data = datas.get(position);
        hodler.tv_title.setText(data);
        hodler.iv_ic.setImageResource(views.get(position));
        Toast.makeText(a,""+data+" "+views.get(position)+" "+position,Toast.LENGTH_SHORT).show();
  }


    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup arg0, int arg1) {

        View itemView = View.inflate(context,
                R.layout.home_right_recyclerview, null);
        return new MyViewHodler(itemView);

    }

}
