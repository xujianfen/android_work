package com.test.middle;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attention.Attention;
import com.bumptech.glide.Glide;
import com.test.banner.R;
import com.user.AccountStatic;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class AttentionSampleAdapter extends BaseAdapter {

    private ArrayList<String> mDataset;
    private ArrayList<String> mViews;
    private AttentionChapterActivity context;

    public AttentionSampleAdapter(AttentionChapterActivity context, ArrayList<String> mDataset,ArrayList<String> mViews) {
        this.mDataset = mDataset;
        this.context = context;
        this.mViews = mViews;

    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=View.inflate(context,R.layout.home_right_recyclerview,null);
        TextView textView = (TextView) convertView.findViewById(R.id.home_right_tv);
        textView.setText(mDataset.get(position));
        ImageView imageview = (ImageView) convertView.findViewById(R.id.home_right_iv);
        RelativeLayout r = (RelativeLayout)convertView.findViewById(R.id.home_right_relativelayout);
        try {
            imageview.setImageResource(Integer.parseInt(mViews.get(position)));
        }catch (Exception e){
            Glide.with(context).load(AccountStatic.ImageUrl + mViews.get(position)).placeholder(R.mipmap.ic_image_null)
                    .error(R.mipmap.ic_image_null).bitmapTransform(new
                    RoundedCornersTransformation(context, 30, 0, RoundedCornersTransformation.
                    CornerType.BOTTOM)).crossFade(1000).into(imageview);
        }

            if (position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        }else{
            convertView.setBackgroundColor(Color.WHITE);
        }
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.num = (position+1) + "";
                context.setTitle("第"+context.num+"话");
                context.initokhttp();


            }
        });

        return convertView;
    }

}
