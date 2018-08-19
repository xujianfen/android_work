package com.test.middle;


import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;

import android.widget.ImageView;
import android.widget.Toast;

import com.attention.Attention;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.banner.R;
import com.test.left.userview.MyRecyclerViewAdapter;
import com.test.right.RightMenu;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class MiddleMenu extends Fragment {

	private ActionMode.Callback actionCallback;
	private ActionMode actionMode;
	private Activity a;
    public List<Attention> attention_array;
	public RecyclerView recyclerview;
	private ArrayList<String> datas;
	private ArrayList<String> views;
	private ArrayList<Integer> ids;
	private MiddleRecyclerViewAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,  Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.middle, container,false);


		initokhttp();
		recyclerview = (RecyclerView)v.findViewById(R.id.home_middle_re);
		//设置Recyclerview的适配器
	   	return v;
	}

	/*可见（visible)
             XML文件：android:visibility="visible"
             Java代码：view.setVisibility(View.VISIBLE);

             不可见（invisible）
             XML文件：android:visibility="invisible"
             Java代码：view.setVisibility(View.INVISIBLE);

             隐藏（GONE）
             XML文件：android:visibility="gone"
             Java代码：view.setVisibility(View.GONE);
         */
	public void setactionMode(Activity a,ActionMode actionMode,ActionMode.Callback actionCallback){

		this.a = a;
		this.actionMode = actionMode;
		this.actionCallback = actionCallback;
	}
	public void setActionMode(ActionMode actionMode,boolean pressed){

			adapter.setActionMode(actionMode,pressed);

	}

    public void initview(){
		adapter = new MiddleRecyclerViewAdapter(a,datas,views,ids);
		adapter.setactionMode(a,actionMode,actionCallback);
		recyclerview.setAdapter(adapter);

		//显示recyclerview  下面为（context，垂直方向 ，不倒序）
		recyclerview.setLayoutManager(new GridLayoutManager(a,3,LinearLayoutManager.VERTICAL,false));
		recyclerview.scrollToPosition(datas.size()-1);
	}

		public void initokhttp() {

			Map<String, String> params = new HashMap<String, String>();
			if(AccountStatic.account.getAttention()!=null)
			params.put("attention",AccountStatic.account.getAttention());
			OkHttpUtils.post().url( AccountStatic.URSE_HTTP + "SetMyLikeComics").params(params).build().execute(new MiddleMenu.MyStringCallback());

		}
		public class MyStringCallback extends StringCallback {

		//出错
		@Override
		public void onError(Call call, Exception e, int id) {
			e.printStackTrace();
			Toast.makeText(a, "网络异常", Toast.LENGTH_SHORT).show();
		}

		//完成
		@Override
		public void onResponse(String response, int id) {
			Gson gson = new Gson();
			attention_array = gson.fromJson(response, new
					TypeToken<List<Attention>>() {
					}.getType());
			datas = new ArrayList<String>();
			views = new ArrayList<String>();
			ids = new ArrayList<Integer>();
			for(Attention arraylist:attention_array){
				datas.add(arraylist.getName());
				views.add(arraylist.getCover());
				ids.add(arraylist.getId());
				initview();
			}


		}

	}

}