package com.test.middle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.example.account.Account;
//import com.example.fen4.R;
//import com.example.fen.R.id;

//import com.view.ment.LeftMenu;
//import com.view.ment.MainUI;
//import com.view.ment.MiddleMenu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;


//import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.add_attention.MessageEvent;
import com.attention.AttentionChapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opendanmaku.DanmakuView;
import com.test.banner.R;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;


public class AttentionChapterActivity extends AppCompatActivity implements OnItemClickListener {

    @InjectView(R.id.left_drawer)
    public ListView mDrawerList;
    @InjectView(R.id.toolbar_chapter_attention)
    public Toolbar toolbar;
    @InjectView(R.id.attention_et)
    public EditText editText;
    @InjectView(R.id.attention_bt)
    public Button button;
    @InjectView(R.id.linearLayout)
    public LinearLayout linearlayout;
    @InjectView(R.id.fab_chapter_attention)
    public FloatingActionButton fab;
    @InjectView(R.id.fab_chapter_attention1)
    public FloatingActionButton fab1;


    @OnClick(R.id.fab_chapter_attention)
    void fab_chapter_attention1() {
        if (newboolean) {
            contentFragmentc.myItemRecyclerViewAdapter.onResume();
            fab.setImageResource(R.mipmap.ic_menu_view);
        } else {
            contentFragmentc.myItemRecyclerViewAdapter.onPause();
            fab.setImageResource(R.mipmap.ic_menu_view1);
        }
        newboolean = !newboolean;

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBus3(MessageEvent m) {
        if (m != null)
            a = m.id;
        linearlayout.setVisibility(View.VISIBLE);
        EventBus.getDefault().unregister(AttentionChapterActivity.this);
    }

    public int a = 0;
    public boolean newboolean = false;
    private Attention_Fragment contentFragmentc;


    @OnClick(R.id.fab_chapter_attention1)
    void fab_chapter_attention() {
        Snackbar.make(fab1, "请选择页面", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!EventBus.getDefault().isRegistered(AttentionChapterActivity.this))
                    EventBus.getDefault().register(AttentionChapterActivity.this);
                fab.setVisibility(View.INVISIBLE);
                fab1.setVisibility(View.INVISIBLE);

            }
        }).show();

    }

    @OnClick(R.id.attention_bt)
    void attention_button() {
        String input = editText.getText().toString();
        if(AccountStatic.s_boolean(input)){
            contentFragmentc.myItemRecyclerViewAdapter.onDanmaku(input, a);
            inituokhttp(input);
            editText.setText(null);
            linearlayout.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            fab1.setVisibility(View.VISIBLE);
            ;
            inituokhttp(input);
        }else{
            Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
        }
    }
     public void inituokhttp(String input){
        Map<String,String> params = new HashMap<String,String>();
         params.put("attention_id",attentionChapter.get(0).getId()+"");
         if(attentionChapter.get(0).getDanmak()!=null) {
             params.put("danmak", attentionChapter.get(0).getDanmak() + "");
             params.put("danmak_postion",""+ attentionChapter.get(0).getDanmak_position());
             Toast.makeText(AttentionChapterActivity.this,""+attentionChapter.get(0).getDanmak_position(),Toast.LENGTH_SHORT).show();
         }
         params.put("danmak_position_id",a+"");
         params.put("danmak_id",input+"");

         String url = "Danmak";
        new AccountStatic().initOkhttp(AttentionChapterActivity.this,params,url,false,null);
    }
    private ArrayList<String> menuLists;
    private ArrayList<String> viewLists;
    private AttentionSampleAdapter adapter;
    //    private ActionBarDrawerToggle mDrawerToggle;
    private String mTitle;
    ActionBarDrawerToggle toggle;
    private DrawerLayout mDrawerLayout;
    public  List<AttentionChapter> attentionChapter;
    public  List<String>  viewlist;
    public  List<String> danmak;
    public  List<String> danmak_position;
   public String id;
    public String title;
    public String A = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attention);

        ButterKnife.inject(AttentionChapterActivity.this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //设置图标
        toolbar.setNavigationIcon(R.mipmap.ic_menu_feedback);
        // 标题

        initToolBar();

        Intent i = getIntent();
        id = i.getStringExtra("id");
        title = i.getStringExtra("title");
        toolbar.setTitle(title);

        //把ToolBar的设置的ActionBar的位置
        setSupportActionBar(toolbar);
        mDrawerLayout.setDrawerListener(toggle);
        A = i.getStringExtra("a");
        if (id == null)
            Log.e("sss", "null");
        else
            Log.e("sss", id);
        initokhttp();
    }


    public void initokhttp()

      {

          Map<String,String> params = new HashMap<String,String>();
        params.put("attention_id",id);
        params.put("this_chapter",num+"");
        OkHttpUtils.post().url(AccountStatic.URSE_HTTP+"SetLookAttentionChapter").params(params).build().execute(new  StringCallback()
        {
            @Override
            public void onBefore(okhttp3.Request request, int id) {
                super.onBefore(request, id);
            }

            //启动后
            @Override
            public void onAfter(int id)
            {
            }

            //出错
            @Override
            public void onError(Call call, Exception e, int id)
            {
                e.printStackTrace();


                Toast.makeText(AttentionChapterActivity.this, "网络异常",Toast.LENGTH_SHORT).show();
            }


            //完成
            @Override
            public void onResponse(String response, int id)
            {
                int NUM = Integer.parseInt(num);

                if(AccountStatic.s_boolean(response)&&!response.equals("[]")){
                    NUM--;
                Gson gson = new Gson();
                attentionChapter= gson.fromJson(response, new
                        TypeToken<List<AttentionChapter>>() {
                        }.getType());

//                if(attentionChapter.get(NUM).getView_list()!=null)
                        Log.e("sss", attentionChapter.get(0).getView_list());
                        viewlist = gson.fromJson(attentionChapter.get(0).getView_list(), new
                                TypeToken<List<String>>() {
                                }.getType());
                        Log.e("sss", "viewlist=" + viewlist + " " + attentionChapter.get(0).getView_list());
//                if(attentionChapter.get(NUM).getDanmak()!=null&&!attentionChapter.get(NUM).getDanmak().equals("[]"))
                        danmak = gson.fromJson(attentionChapter.get(0).getDanmak(), new
                                TypeToken<List<String>>() {
                                }.getType());
//                if(attentionChapter.get(NUM).getDanmak_position()!=null&&!attentionChapter.get(NUM).getDanmak_position().equals("[]"))
                        danmak_position = gson.fromJson(attentionChapter.get(0).getDanmak_position(), new
                                TypeToken<List<String>>() {
                                }.getType());
                        Attention_Fragment.viewlist = viewlist;
                        Attention_Fragment.danmak = danmak;
                        Attention_Fragment.danmak_position = danmak_position;
                        Attention_Fragment.nboolean = true;
                        initview();


                }else {

                    if (A != null){
                        viewlist = new ArrayList<String>();
                    danmak = new ArrayList<String>();
                    danmak_position = new ArrayList<String>();
                    viewlist.add(new String(-1 + ""));
                    Attention_Fragment.viewlist = viewlist;
                    Attention_Fragment.danmak = danmak;
                    Attention_Fragment.danmak_position = danmak_position;
                    Attention_Fragment.nboolean = false;
                    initview();
                }

                }

                initLeftOkhttp();
            }
            //进度
            @Override
            public void inProgress(float progress, long total, int id)
            {

            }
        });

    }

    /**
     *
     * 左边
     *
     */
    public void initLeftOkhttp(){
        Map<String,String> params = new HashMap<String,String>();
        params.put("attention_id",id);
        OkHttpUtils.post().url(AccountStatic.URSE_HTTP+"SetAllAttentionChapter").params(params).build().execute(new  StringCallback()
        {
            @Override
            public void onBefore(okhttp3.Request request, int id) {
                super.onBefore(request, id);
            }

            //启动后
            @Override
            public void onAfter(int id)
            {
            }

            //出错
            @Override
            public void onError(Call call, Exception e, int id)
            {
                e.printStackTrace();


                Toast.makeText(AttentionChapterActivity.this, "网络异常",Toast.LENGTH_SHORT).show();
            }


            //完成
            @Override
            public void onResponse(String response, int id)
            {          viewLists = new ArrayList<String>();
                menuLists = new ArrayList<String>();
               Gson gson = new Gson();

                viewLists  = gson.fromJson(response, new
                                TypeToken<List<String>>() {
                                }.getType());

                    initLeft();
            }
            //进度
            @Override
            public void inProgress(float progress, long total, int id)
            {

            }
        });

    }
       public void initLeft() {

           for (int i = 0; i < viewLists.size(); i++)
               menuLists.add("第" + (i + 1) + "话");
           if (A != null){
               menuLists.add(" ");
               viewLists.add("" + R.mipmap.kg_add_widget_disabled);
            }
               adapter = new AttentionSampleAdapter(this, menuLists, viewLists);
               mDrawerList.setAdapter(adapter);

       }
    public void initview(){

         Attention_Fragment.newcontext = AttentionChapterActivity.this;
        FragmentManager fm = getFragmentManager();
        contentFragmentc = new Attention_Fragment();
//        contentFragmentc.myItemRecyclerViewAdapter.get();
        fm.beginTransaction().replace(R.id.content_frame, contentFragmentc).commit();
    }

    private void initToolBar() {


        //获取开关同时让开关和DrawerLayout关联在一起
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.mipmap.ic_menu, R.string.action_settings, 0) {
            //抽屉打开时调用
            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
                toolbar.setTitle("请选择");
                invalidateOptionsMenu();
            }

            //抽屉关闭时调用
            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                toolbar.setTitle(title);
                invalidateOptionsMenu();//        toggle = new ActionBarDrawerToggle(this, mDrawerLayout,0, 0);

            }
        };
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置点击事件，点击弹出menu界面
        mDrawerLayout.setDrawerListener(toggle);
    }



    //    /**加载选项菜单时使用
//     *
//     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //装载菜单资源文件
        if(A!=null)

            getMenuInflater().inflate(R.menu.menu, menu);

        else
            getMenuInflater().inflate(R.menu.bleow, menu);

//		menu.add(R.id.action_websearch);
        return true;
    }

    /**
     * 重写标题菜单
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //动态显示或隐藏菜单
        menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);
        //需要将ActionDrawerToggle与DrawerLayout的状态同步
        //将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    //    @Override
    protected void onResume(Dan dan) {
        super.onResume();
        dan.onResume();
    }


    //    @Override
    protected void onPause(Dan dan) {
        super.onPause();
        dan.onPause();
    }

    //    @Override
    protected void onDestroy(Dan dan) {
        super.onDestroy();
        dan.onDestroy();
    }

    interface Dan {
        public abstract void onResume();

        public abstract void onPause();

        public abstract void onDestroy();
    }
   public void getRegisterForContextMenu(DanmakuView dview,ImageView view){
     this.view =view;
       if(A!=null)
       registerForContextMenu(dview);
   }




   public ImageView view ;
    /**
     *
     * 长按点击菜单
     *
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info
                = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        switch(item.getItemId()){

            case R.id.camera:

                camera(true);
                return true;
            case R.id.userview:

                camera(false);
                return false;
            default:
                return super.onContextItemSelected(item);
        }

    }
    public void camera(boolean a){

        if(a){
            Intent intent;
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAMERA_REQUEST_CODE);
        }

        else{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent,GALLERY_REQUEST_CODE);
        }

    }

    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEXT_CODE = 3;

    /**
     *
     *回调
     *
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //拍照回调
        if(requestCode == CAMERA_REQUEST_CODE)
            if(data == null){
                return;
            }
            else{
                Bundle extras = data.getExtras();
                if(extras != null){
                    Bitmap bm = extras.getParcelable("data");
                    Uri uri = saveBitmap(bm);
                    startImageZoom(uri);
                }

            }
            //图库回调
        else if(requestCode == GALLERY_REQUEST_CODE){
            if(data == null){
                return;
            }

            Uri uri;
            uri = data.getData();
            Uri fileUri = convertUri(uri);
            startImageZoom(fileUri);


        }
        //裁剪回调
        else if(requestCode == CROP_REQUEXT_CODE){
            if(data == null){
                return;
            }
            Bundle extras = data.getExtras();
            if(extras == null){
                return;
            }
            Bitmap bm = extras.getParcelable("data");
            view.setImageBitmap(bm);
            sendImage(bm);
            contentFragmentc.myItemRecyclerViewAdapter.getadd();
        }
    }





    //跳转到裁剪界面
    private void startImageZoom(Uri uri){

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",150);
        intent.putExtra("outputY",150);
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_REQUEXT_CODE);

    }
    //保存
    private Uri saveBitmap(Bitmap bm){
        File tmpDir = new File(Environment.getExternalStorageDirectory()+"/com.xujianfen.avater");
        if(!tmpDir.exists()){
            tmpDir.mkdir();
        }
        File img = new File(tmpDir.getAbsolutePath() + "avater.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
            //ˢ�������
            fos.flush();
            //�ر������
            fos.close();

            return Uri.fromFile(img);
        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }

    private Uri convertUri(Uri uri){
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveBitmap(bitmap);
        } catch (Exception e) {

            e.printStackTrace();
            return null;

        }

    }
    public List<File>  newfile = new ArrayList<File>();
    public void sendImage(Bitmap bitmap) {
        // 保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";
        File file = new File(appDir, fileName);
        newfile.add(file);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();

            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);
          } catch (Exception e) {
         e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }


    public int m = 0;
    public String filenameNew  = null;

    public void maxFileUpload(){
      try {
             m=0;
          for (int i = 0; i < newfile.size(); i++)
              multiFileUpload(i);


//          String
      }catch (Exception e){
          Toast.makeText(AttentionChapterActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
      }
    }



    public void multiFileUpload(int i)
    {

        File file = newfile.get(i);//new File(Environment.getExternalStorageDirectory(), "zxing_image.png");
        if (!file.exists())
        {
            Toast.makeText(AttentionChapterActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", "鬼瞳");
        params.put("password", "123");
        String filename = "messenger_01.png";
        filenameNew = file.getAbsolutePath()+filename;
        String url = AccountStatic.mBaseUrl;
        OkHttpUtils.post()//
                .addFile("mFile", filename, file)//
                .url(url)
                .params(params)//
                .build()//
                .execute(new AttentionChapterActivity.MyStringCallback(true));
    }

    public List<String> string = new ArrayList<String>();

    public class MyStringCallback extends StringCallback
    {

        private  boolean b;
        public MyStringCallback(boolean b){
            super();
            this.b = b;
        }
        //启动前
        @Override
        public void onBefore(okhttp3.Request request, int id){
            Toast.makeText(AttentionChapterActivity.this, "开始添加",Toast.LENGTH_SHORT).show();
        }
        //启动后
        @Override
        public void onAfter(int id)
        {
        }

        //出错
        public  String himt;
        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            //            login_button_name.setText("onError:" + e.getMessage());

            Toast.makeText(AttentionChapterActivity.this, "添加失败",Toast.LENGTH_SHORT).show();
        }
        //完成

        @Override
        public void onResponse(String response, int id)
        {

                string.add(new String(response));
                Toast.makeText(AttentionChapterActivity.this, "添加成功",Toast.LENGTH_SHORT).show();
                m++;
            if(m==newfile.size())
            post();
        }

        //进度
        @Override
        public void inProgress(float progress, long total, int id)
        {

        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.attention, menu);
    }


    /**
     *
     *
     * 添加操作
     */
     public String num = ""+1;
    public void post(){
        Gson gson = new Gson();
        String json = gson.toJson(string);
        String url = AccountStatic.URSE_HTTP+"AddAttentionChapter";
        Map<String,String> params = new HashMap<String,String>();
        params.put("this_chapter",num);
        params.put("view_list",json);
        params.put("attention_id",id);
        OkHttpUtils.post().url(url).params(params).build().execute(new StringCallback()
    {
        //启动前
        @Override
        public void onBefore(okhttp3.Request request, int id){
        }
        //启动后
        @Override
        public void onAfter(int id)
        {
        }

        //出错
        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();

            Toast.makeText(AttentionChapterActivity.this, "这里是post-->网络异常",Toast.LENGTH_SHORT).show();
        }
        //完成
        @Override
        public void onResponse(String response, int id)
        {
            Toast.makeText(AttentionChapterActivity.this, "添加成功",Toast.LENGTH_SHORT).show();
        }
        //进度
        @Override
        public void inProgress(float progress, long total, int id)
        {

        }
    });




    }


    //覆写方法让系统判断点击的图标后是否弹出侧拉页面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_websearch:
                finish();
                break;

            case R.id.action_multiFileUpload:
//                if()
                maxFileUpload();
                break;

        }
        return super.onOptionsItemSelected(item);

    }




    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

        Toast.makeText(AttentionChapterActivity.this,"受死"+position,Toast.LENGTH_SHORT).show();


//        Fragment contentFragment = new ContentFragment();
//        Bundle args = new Bundle();
//        args.putString("text", menuLists.get(position));
//        //传递数值给contentFragment
//        contentFragment.setArguments(args);
//        FragmentManager fm = getFragmentManager();
//        fm.beginTransaction().replace(R.id.content_frame, contentFragment).commit();
//        mDrawerLayout.closeDrawer(mDrawerList);


    }


//    if(A!=null)
//            menu.findItem(R.id.action_multiFileUpload).setVisible(false);


}



