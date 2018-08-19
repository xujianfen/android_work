package com.test.right;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.attention.Attention;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.HomeActivity;
import com.test.banner.R;
import com.test.middle.AttentionChapterActivity;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Call;


public class SampleAdapter extends BaseAdapter {

    private List<String> mDataset;
    private HomeActivity context;
    private List<Integer> id;
    private List<String> v;
    public SampleAdapter(HomeActivity context, List<String> mDataset, List<String> v, List<Integer> id) {
        this.mDataset = mDataset;
        this.context = context;
        this.v =v;
        this.id = id;
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
        convertView=View.inflate(context, R.layout.text_item,null);
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        Button button = (Button) convertView.findViewById(R.id.button);
        ImageView imageview = (ImageView)convertView.findViewById(R.id.view);
        textView.setText(mDataset.get(position)+" "+id.get(position));
        RelativeLayout relativelayour =(RelativeLayout)convertView.findViewById(R.id.home_right_relativelayout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  initokhttp(position);
            }
        });
        relativelayour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setClass(context, AttentionChapterActivity.class);
                intent.putExtra("id",""+id.get(position));
                intent.putExtra("title",mDataset.get(position));
                Toast.makeText(context,""+id.get(position),Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(AccountStatic.ImageUrl+ v.get(position)).placeholder(R.mipmap.ic_image_null)
								.error(R.mipmap.ic_image_null).crossFade(1000).into(imageview);

        if (position%2==0){
            textView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        }else{
            textView.setBackgroundColor(Color.WHITE);
        }
        return convertView;
    }
        public void initokhttp(int i) {
            String url = AccountStatic.URSE_HTTP + "U_add_Delete_MyLike_attention";
            Map<String, String> params = new HashMap<String, String>();
            params.put("id",""+AccountStatic.account.getId());
            if(AccountStatic.account.getAttention()!=null)
            params.put("attention",AccountStatic.account.getAttention());
            params.put("attention_id",""+id.get(i));
            params.put("boolean_num",true+"");
            OkHttpUtils.post().url(url).params(params).build().execute(new SampleAdapter.MyStringCallback());
         }
    public class MyStringCallback extends StringCallback {

        //出错
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            login_button_name.setText("onError:" + e.getMessage());
            context.setInit();
            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
        }

        //完成
        @Override
        public void onResponse(String response, int id) {

                     AccountStatic.account.setAttention(response);
        }

    }
}
