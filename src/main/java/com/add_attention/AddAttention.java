package com.add_attention;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.attention.Attention;
import com.bumptech.glide.Glide;
import com.login.LoginActivity;
import com.squareup.okhttp.Request;
import com.test.banner.R;
import com.test.left.userview.UserViewActivity;
import com.user.Account;
import com.user.AccountStatic;
import com.user.MaxStringCallback;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.InjectViews;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Call;

public class AddAttention extends AppCompatActivity {
    @InjectView(R.id.toolbar_add_attention)
    public Toolbar toolbar;
    @InjectView(R.id.add_attention_finish)
    public ButtonBarLayout finsih_bbl;
    @InjectView(R.id.attention_name_et)
    public EditText attention_name;
    @InjectView(R.id.attention_introduce_et)
    public EditText attention_introduce;
    @InjectView(R.id.attention_cover_iv)
    ImageView imageview;
    @OnClick(R.id.attention_cover_iv)
    void findImageView(){
        Intent intent = new Intent();
        intent.setClass(AddAttention.this,UserViewActivity.class);
        startActivityForResult(intent, AccountStatic.PARSONAL);

    }
    @OnClick(R.id.add_attention_finish)
    void findFinsih_bbl(){

        multiFileUpload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attention);
        ButterKnife.inject(AddAttention.this);
        setSupportActionBar(toolbar);
        registerForContextMenu(imageview);

//        getSupportActionBar(),setDisplayShowTitleEnabled(false);
        setTitle(null);
//        toolbar.Icon(R.mipmap.ic_collect_false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //返回按钮图片
//        toolbar.setNavigationIcon(R.mipmap.ic_finish);
//        toolbar.setTitle("墨瞳漫画");
//        toolbar.setV
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     finish();
                                                 }
                                             });

    }

   public void initAttention(final  String re){

         String url = AccountStatic.URSE_HTTP+"AddComics";
         Map<String,String> params = new HashMap<String,String>();
         if(!AccountStatic.s_boolean(attention_name)){
             Snackbar.make(finsih_bbl, "漫画名不能为空", Snackbar.LENGTH_INDEFINITE).show();
         }
        else
         if(!AccountStatic.s_boolean(attention_introduce)){
             Snackbar.make(finsih_bbl, "介绍不能为空", Snackbar.LENGTH_INDEFINITE).show();
        }else{
           params.put("introduce", attention_introduce.getText().toString());

           params.put("name", attention_name.getText().toString());
           params.put("work_id", AccountStatic.account.getId() + "");
           params.put("work",AccountStatic.account.getUsername());
             params.put("cover",re);
           OkHttpUtils.post().url(url).params(params).build().execute(new  StringCallback()
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
//            login_button_name.setText("onError:" + e.getMessage());
                     Toast.makeText(AddAttention.this, "网络异常",Toast.LENGTH_SHORT).show();
                 }
                 //完成
                 @Override
                 public void onResponse(String response, int id)
                 {
                     Toast.makeText(AddAttention.this, "添加成功", Toast.LENGTH_SHORT).show();
                     finish();
                 }
                 //进度
                 @Override
                 public void inProgress(float progress, long total, int id)
                 {

                 }
             });
       }
   }












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
        switch(item.getItemId()) {

            case R.id.camera:

                camera(true);
                return true;
            case R.id.userview:

                camera(false);
                return false;

        }
                return super.onContextItemSelected(item);


    }







    /**
     *
     * 跳转
     *
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.userview, menu);

    }
    public void setView(ImageView imageview){

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
            imageview.setImageBitmap(bm);
            sendImage(bm);

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
    public File newfile;
    public void sendImage(Bitmap bitmap) {
        // 保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";
        File file = new File(appDir, fileName);
        newfile = file;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();

            // 把文件插入到系统图库
            MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), fileName, null);



        } catch (Exception e) {
            Toast.makeText(AddAttention.this, "添加失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }

    /**
     *
     * okhttp上传图片
     *
     */
    public String filenameNew  = null;
    public void multiFileUpload()
    {

        File file = newfile;//new File(Environment.getExternalStorageDirectory(), "zxing_image.png");
        if (file==null||!file.exists())
        {
            Toast.makeText(AddAttention.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
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
                .execute(new AddAttention.MyStringCallback(true));
    }

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
            Toast.makeText(AddAttention.this, "请稍等。。",Toast.LENGTH_SHORT).show();
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

            Toast.makeText(AddAttention.this, "添加失败，请检查你的封面",Toast.LENGTH_SHORT).show();
        }
        //完成

        @Override
        public void onResponse(String response, int id)
        {

                Log.v("sss", "1=" +AccountStatic.ImageUrl+re);


                Toast.makeText(AddAttention.this, "添加成功",Toast.LENGTH_SHORT).show();
   Glide.with(AddAttention.this).load(AccountStatic.ImageUrl+response).placeholder(R.mipmap.ic_below)
                        .error(R.mipmap.ic_hone_new_2).crossFade(1000).into(imageview);
                initAttention(response);

        }

        //进度
        @Override
        public void inProgress(float progress, long total, int id)
        {

        }
    }
    public String re;
    public void post(String response){

        String url = AccountStatic.URSE_HTTP+"Image";
//		    response = AccountStatic.ImageUrl+"response";
        Map<String,String> params = new HashMap<String,String>();
        params.put("id",AccountStatic.account.getId()+"");
        params.put("image",response);
        Log.e("sss",params.toString());
        OkHttpUtils.post().url(url).params(params).build().execute(new AddAttention.MyStringCallback(false));

    }


}
