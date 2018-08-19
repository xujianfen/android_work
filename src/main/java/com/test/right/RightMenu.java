package com.test.right;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.attention.Attention;
import com.attention.AttentionChapter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.login.LoginActivity;
import com.test.HomeActivity;
import com.test.banner.R;
import com.test.left.userview.UserViewActivity;
import com.test.middle.AttentionChapterActivity;
import com.user.AccountStatic;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Call;


public class RightMenu extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
    static final int REFRESH_COMPLETE = 0X1112;
    SwipeRefreshLayout mSwipeLayout;
    public ListView listView;
    public Banner banner;
    public HomeActivity a;

    public int num = 0;
    public List<String> uString;
    public List<String> vString;
    public  List<Integer> vid;
    /*
     */
    PullToRefreshListView mPullRefreshListView;
    private LinkedList<String> mListItems;
    private ArrayAdapter<String> mAdapter;
    private List<Attention> attention;
    /*
    */

    public void setActivity(HomeActivity a) {
        this.a = a;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            num=0;
            initokhttp();
         }
    };


    public void initis() {

        for(Attention at:attention){
                 uString.add(at.getName());
                 vid.add(at.getId());
                 vString.add(at.getCover());

        }

        listView.setAdapter(new SampleAdapter(a,uString,vString,vid));
        listView.setOnItemClickListener(this);
        mListItems = new LinkedList<String>();
        mListItems.addAll(uString);
        //创建适配器
        mAdapter = new ArrayAdapter<String>(a, android.R.layout.simple_list_item_1, mListItems);
       if(num!=0)
        listView.setSelection(mAdapter.getCount()-1);
        List<String> view = new ArrayList<>();
        for(String u :vString)
            view.add(AccountStatic.ImageUrl +u);
        banner.setImages(view).setBannerTitles(uString).setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE).
                setImageLoader(new GlideImageLoader()).setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setClass(a, AttentionChapterActivity.class);
                intent.putExtra("id",""+vid.get(position-1));
                intent.putExtra("title",uString.get(position-1));
                a.startActivity(intent);
            }
        }).start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_main, container, false);
        mSwipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe);
        mPullRefreshListView = (PullToRefreshListView) v.findViewById(R.id.pull_refresh_list);

        initview();


         return v;
    }

    public void initokhttp() {
        String url = AccountStatic.URSE_HTTP + "SetAllComics";
        Map<String, String> params = new HashMap<String, String>();
        params.put("num", num + "");
        OkHttpUtils.post().url(url).params(params).build().execute(new RightMenu.MyStringCallback());

    }

    public void initview() {

        mSwipeLayout.setOnRefreshListener(this);
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            //设置下拉刷新监听
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                num++;
                 initokhttp();
                new GetDataTask().execute();
            }
        });
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(a);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);
        /**
         *
         *监听最后一条
         *
         */
        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {
                Toast.makeText(a, "最后一条!", Toast.LENGTH_SHORT).show();
            }
        });
        listView = mPullRefreshListView.getRefreshableView();


        //设置上拉刷新·或者下拉刷新
        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        View header = LayoutInflater.from(a).inflate(R.layout.header, null);
        banner = (Banner) header.findViewById(R.id.banner);
        banner.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, App.H / 4));
        num = 0;
        listView.addHeaderView(banner);
        initokhttp();

        //initis();
     }

//下拉刷新回调
    @Override
    public void onRefresh() {
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
    /**
     *
     *
     *
     */
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
            int i=0;
            String[] s = new String[uString.size()];
            for(String ss:uString){
                s[i++] = ss;
            }
            return s;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (mPullRefreshListView.getMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
                mListItems.addFirst("下拉到新数据...");
            } else if (mPullRefreshListView.getMode() == PullToRefreshBase.Mode.PULL_FROM_END)
                mListItems.addLast("上拉到新数据...");
            mAdapter.notifyDataSetChanged();

            // Call onRefreshComplete when the list has been refreshed.
            mPullRefreshListView.onRefreshComplete();

            super.onPostExecute(result);
        }
    }



    public class MyStringCallback extends StringCallback {

        //出错
        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
//            login_button_name.setText("onError:" + e.getMessage());
            Toast.makeText(a, "网络异常", Toast.LENGTH_SHORT).show();
        }

        //完成
        @Override
        public void onResponse(String response, int id) {

            if(num==0) {
                uString = new ArrayList<>();
                vString = new ArrayList<>();
                vid = new ArrayList<>();
                attention = new ArrayList<>();
//                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//                listView.setStackFromBottom(false);
            }
//            else
//                listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
//                listView.setStackFromBottom(true);
            Gson gson = new Gson();
            attention = gson.fromJson(response, new
                    TypeToken<List<Attention>>() {
                    }.getType());


            initis();
        }

    }
}