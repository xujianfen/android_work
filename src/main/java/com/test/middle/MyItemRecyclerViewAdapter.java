package com.test.middle;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.add_attention.MessageEvent;
import com.attention.AttentionChapter;
import com.bumptech.glide.Glide;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.test.banner.R;
import com.test.middle.dummy.DummyContent.DummyItem;
import com.user.AccountStatic;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {
    public List<DanmakuView> arraylist = new ArrayList<DanmakuView>();
    private final List<DummyItem> mValues;
    private final Attention_Fragment.OnListFragmentInteractionListener mListener;
    private AttentionChapterActivity context;
    private boolean nboolean;
    public  List<String>  viewlist;
    public  List<String> danmak;
    public  List<String> danmak_position;
    public AttentionChapter attentionChapter;

    public  void getadd(){

        addData();

    }
    /**
     * 添加数据
     *
     *
     */
    public void addData(){

        viewlist.add(viewlist.size(),new String(""));
        notifyItemInserted(viewlist.size());
    }

    public MyItemRecyclerViewAdapter(final AttentionChapterActivity context, List<DummyItem> items, Attention_Fragment.OnListFragmentInteractionListener listener,
                                     List<String>  viewlist,  List<String> danmak,List<String> danmak_position,boolean  nboolean) {
        mValues = items;
        mListener = listener;
        this.context = context;

        this.viewlist =viewlist;
        Log.e("sss","my-->"+viewlist);
        this.danmak = danmak;
        this.danmak_position = danmak_position;
        this.nboolean = nboolean;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
        boolean g=true;
       final int j;

        if(nboolean)
             Glide.with(context).load(AccountStatic.ImageUrl + viewlist.get(position)).placeholder(R.mipmap.ic_image_null)
                    .error(R.mipmap.ic_image_null).bitmapTransform(new
                    RoundedCornersTransformation(context, 30, 0, RoundedCornersTransformation.
                    CornerType.BOTTOM)).crossFade(1000).into(holder.iv);
        else
            holder.iv.setImageResource(R.mipmap
            .kg_add_widget_disabled);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {

                    mListener.onListFragmentInteraction(holder.mItem);

                }
            }
        });
        //重点
        List<IDanmakuItem> list = initItems(holder.mDanmakuView,position);
        //变成随机数据
        Collections.shuffle(list);
        //添加到弹幕控件厘里面
        holder.mDanmakuView.addItem(list,true);
//        NewDan dan = new NewDan(holder.mDanmakuView);
//        dan.onResume();
        holder.mDanmakuView.show();
        holder.mDanmakuView.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){

//                EventBus.getDefault().post(position);
                EventBus.getDefault().post(new MessageEvent(position));
                       Toast.makeText(context,""+position,Toast.LENGTH_SHORT).show();
            }
        });
        context.getRegisterForContextMenu(holder.mDanmakuView,holder.iv);
        arraylist.add(holder.mDanmakuView);
    }

    /**
     *
     * 构建弹幕数据集合
     * @param mDanmakuView
     * @return
     */
    private List<IDanmakuItem> initItems(DanmakuView mDanmakuView,int num) {
        List<IDanmakuItem> list = new ArrayList<>();
        //创建文本弹幕
        if(danmak!=null)
        for (int i = 0; i < danmak.size(); i++) {
           int a = Integer.parseInt(danmak_position.get(i));
            if(a==num) {
                IDanmakuItem item = new DanmakuItem(context,danmak.get(i), mDanmakuView.getWidth());
                list.add(item);
            }
        }
        //创建带图片弹幕
//        if(danmak!=null)
////        String msg = " : text with image   ";
//        for (int i = 0; i < danmak.size(); i++) {
//            int a = Integer.parseInt(danmak_position.get(i));
//            if(a==num) {
//                    ImageSpan imageSpan = new ImageSpan(context, R.drawable.em);
//                    SpannableString spannableString = new SpannableString(danmak.get(i));
//                    spannableString.setSpan(imageSpan, spannableString.length() - 2, spannableString.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    IDanmakuItem item = new DanmakuItem(context, spannableString, mDanmakuView.getWidth(), 0, 0, 0, 1.5f);
//                    list.add(item);
//            }
//        }
        return list;
    }

    @Override
    public int getItemCount() {
        return (viewlist==null)?0:viewlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final ImageView iv;

        public DummyItem mItem;

        public LinearLayout linearLayout;

        public DanmakuView mDanmakuView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iv = (ImageView) view.findViewById(R.id.attention_chapter_iv);
            mDanmakuView = (DanmakuView) mView.findViewById(R.id.danmakuView);
            linearLayout = (LinearLayout)view.findViewById(R.id.linearLayout_fragment_item);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mView.getId() + "'";
        }




    }



    class NewDan  implements AttentionChapterActivity.Dan {

        public DanmakuView mDanmakuView;

        public NewDan() {

        }

        public NewDan(DanmakuView mDanmakuView) {
            super();
            this.mDanmakuView = mDanmakuView;
        }
        //显示弹幕
        @Override
        public void onResume() {
            if (mDanmakuView != null)
                mDanmakuView.show();
        }

        @Override
        public void onPause() {
            if (mDanmakuView != null)
                mDanmakuView.hide();
        }

        @Override
        public void onDestroy() {
            if (mDanmakuView != null)
                mDanmakuView.clear();
        }
    }
    //显示弹幕
    public void onResume() {
       for(DanmakuView mDanmakuView:arraylist) {
           if (mDanmakuView != null)
//               mDanmakuView.show();
               mDanmakuView.setVisibility(View.VISIBLE);
       }

    }

    //隐藏图片
    public void onPause() {
        for(DanmakuView mDanmakuView:arraylist)
        if (mDanmakuView != null) {
//            mDanmakuView.hide();
           mDanmakuView.setVisibility(View.INVISIBLE);
        }
    }

//清除弹幕
    public void onDestroy() {
        for(DanmakuView mDanmakuView:arraylist)
            if (mDanmakuView != null)
                mDanmakuView.clear();
    }


    public void onDanmaku(String input,int ii){
        if (TextUtils.isEmpty(input)) {
            Toast.makeText(context,"请输入内容", Toast.LENGTH_SHORT).show();
        } else {
//            IDanmakuItem item = new DanmakuItem(context, input, arraylist.get(ii).getWidth());
//            item.setTextColor(context.getResources().getColor(R.color.my_item_color));
//            item.setTextSize(14);
//            item.setTextColor(R.color.recoveryColorAccent);
            IDanmakuItem item = new DanmakuItem(context, new SpannableString(input),arraylist.get(ii).getWidth(),0,R.color.my_item_color,0,1);
             arraylist.get(ii).addItemToHead(item);
        }

    }
}


