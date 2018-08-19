package com.test.left.userview;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.add_attention.AttentionAcitvity;
import com.parsonal.Parsonal;
import com.test.banner.R;
import com.test.middle.MiddleRecyclerViewAdapter;
import com.user.AccountStatic;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHodler> {
	/**
	 * intent.setClass(a,Parsonal.class);
	 //                  startActivityForResult(intent, AccountStatic.PARSONAL);
	 ////                  a.startActivity(intent);home_right_relativelayout
	 */
	private Activity context;
	private ArrayList<String> datas;
	private ArrayList<Integer> views;
	
	public MyRecyclerViewAdapter(Activity context, ArrayList<String> datas, ArrayList<Integer> views){
			this.context = context;
			this.datas = datas;
			this.views = views;

	}
	class MyViewHodler extends RecyclerView.ViewHolder{
		private ImageView iv_ic;
		private TextView tv_title;
		private  RelativeLayout relativelayout;
		public MyViewHodler(View itemView){

			super(itemView);
			iv_ic = (ImageView) itemView.findViewById(R.id.home_right_iv);
			tv_title = (TextView)itemView.findViewById(R.id.home_right_tv);
			relativelayout = (RelativeLayout)itemView.findViewById(R.id.home_right_relativelayout);

		}
	}


/**
 * �õ�������
 * 
 */
	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	/**
	 * 
	 * �൱��getView�����ݲ��ֵĴ���
	 * 
	 */
	@Override
	public void onBindViewHolder(MyViewHodler hodler,final int position) {
		
		String data = datas.get(position);
		hodler.tv_title.setText(data);
		hodler.iv_ic.setImageResource(views.get(position));
		hodler.relativelayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				switch (position) {
					case 0:

						intent.setClass(context, Parsonal.class);
						context.startActivityForResult(intent, AccountStatic.PARSONAL);
						break;
					case 1:

						intent.setClass(context, AttentionAcitvity.class);
						context.startActivityForResult(intent, AccountStatic.USERVIEWACTIVITY);
						break;
				}

				}
		});


	}
	/**
	 * �൱��getView�����еĴ���View��ViewHolder
	 * 
	 * 
	 */

	@Override
	public MyViewHodler onCreateViewHolder(ViewGroup arg0, int arg1) {
		
		View itemView = View.inflate(context,
				R.layout.home_right_recyclerview, null);
		return new MyViewHodler(itemView);
	}
	
    
}
