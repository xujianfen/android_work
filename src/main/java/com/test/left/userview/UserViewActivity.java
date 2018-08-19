package com.test.left.userview;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.bumptech.glide.Glide;

//import org.apache.commons.io.output.ByteArrayOutputStream;
//import org.apache.http.Header;



//import com.example.fen4.R;
//import com.example.fen.R;
import com.bumptech.glide.Priority;
import com.facebook.common.internal.Objects;
import com.login.LoginActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.Base64;
import com.loopj.android.http.DataAsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
//import com.view.ment.MiddleMenu;

//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.AlertDialog.Builder;
//import android.app.Dialog;
import android.accounts.Account;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
//import android.util.Base64;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;

import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.test.banner.R;
import com.test.middle.MiddleMenu;
import com.user.AccountStatic;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.InjectView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Request;
import static com.test.banner.R.id.mProgressBar;


public class UserViewActivity extends ActionBarActivity{

	private MiddleMenu middleMenu;
//	@InjectView(R.id.left_userimageview)
	private ImageView imageView;
	private ActionMode actionMode;//ʹ��ActioonMode��ɲ˵�����
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
	private static int CROP_REQUEXT_CODE = 3;
    private boolean newboolean = true;
    private boolean newboolean_two = false;

	public Toolbar toolbar;
	/**
		 * Snackbar a;
		 * 初始化
		 * CoordinatorLayout
		 * Toolbar a;
		 */
		@Override
		protected void onCreate(Bundle savedInstanceState) {
				// TODO Auto-generated method stub
			    super.onCreate(savedInstanceState);
				setContentView(R.layout.left_userhone);
			Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_add_attention);
			setSupportActionBar(toolbar);
				imageView = (ImageView)findViewById(R.id.left_userimageview);
				middleMenu = new MiddleMenu();
				//菜单绑定图片
				registerForContextMenu(imageView);
				viewGlide();

		}
		private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
				super.handleMessage(msg);

				Glide.with(UserViewActivity.this).load(msg.obj).placeholder(R.mipmap.ic_below)
					.error(R.mipmap.ic_hone_new_2).bitmapTransform(new RoundedCornersTransformation(UserViewActivity.this,30,0,RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(imageView);
			    newboolean = false;//.priority(Priority.HIGH)优先级

			}

	};
		public void viewGlide(){
		new Thread(){
			@Override
			public void run() {
				super.run();
				try {
					Message msg = Message.obtain();
					msg.obj =  AccountStatic.ImageUrl+AccountStatic.account.getHead();
					Log.e("sss",AccountStatic.ImageUrl+AccountStatic.account.getHead());
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

	}
		/**
		 *
		 * �˵�
		 *
		 */
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {

			  getMenuInflater().inflate(R.menu.menu, menu);
			return true;
		}

		/**
	     *
	     * 点击菜单
	     *
	     */
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {

			switch (item.getItemId()) {
			case R.id.action_websearch:
				finish();
				break;

			case R.id.action_multiFileUpload:
                if(newboolean_two)
					multiFileUpload();
				break;

			}
			return super.onOptionsItemSelected(item);
		}

		/**
		 *
		 * 跳转
		 *
		 */
		@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {

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

		 /**
		 *
		 * 长按点击菜单
		 *
	     */
		@Override
		public boolean onContextItemSelected(MenuItem item) {

			AdapterContextMenuInfo info
			= (AdapterContextMenuInfo)
			item.getMenuInfo();
			switch(item.getItemId()){

			case R.id.camera:

				camera(true);
				return true;
			case R.id.userview:

				camera(false);
				return false;
				case R.id.severview:
					if(newboolean) {
                        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        sendImage(image, true);
                    }
                    else{
                        downloadFile(AccountStatic.ImageUrl+AccountStatic.account.getHead());
                    }
					return super.onContextItemSelected(item);
			default:
				return super.onContextItemSelected(item);
			}

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
		if (!file.exists())
		{
			Toast.makeText(UserViewActivity.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
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
				.execute(new MyStringCallback(true));
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
					Toast.makeText(UserViewActivity.this, "开始上传",Toast.LENGTH_SHORT).show();
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

					Toast.makeText(UserViewActivity.this, "上传失败",Toast.LENGTH_SHORT).show();
				}
				//完成

				@Override
				public void onResponse(String response, int id)
				{
					if(!b) {
						Log.v("sss", "1=" +AccountStatic.ImageUrl+re);
						AccountStatic.account.setHead(re);

					Toast.makeText(UserViewActivity.this, "上传成功",Toast.LENGTH_SHORT).show();
//						Glide.with(UserViewActivity.this).load(AccountStatic.ImageUrl+ AccountStatic.account.getHead()).placeholder(R.mipmap.ic_yes_true)
//								.error(R.mipmap.ic_hone_new_1).bitmapTransform(new RoundedCornersTransformation(UserViewActivity.this,30,0,RoundedCornersTransformation.CornerType.BOTTOM)).crossFade(1000).into(imageView);


					}
					else {
                            re = response;
							post(response);
					}
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
			OkHttpUtils.post().url(url).params(params).build().execute(new UserViewActivity.MyStringCallback(false));

	}

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
				imageView.setImageBitmap(bm);
				sendImage(bm,false);
                newboolean = true;
                newboolean_two = true;
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
    	public void sendImage(Bitmap bitmap,boolean send_boolean) {
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
			if(send_boolean)
			Toast.makeText(UserViewActivity.this, "保存成功",Toast.LENGTH_SHORT).show();

		} catch (Exception e) {
			if(send_boolean)
			Toast.makeText(UserViewActivity.this, "保存失败",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		// 通知图库更新
		sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
	}



    public String fileNameNew =null;
    public void downloadFile(String url)
    {
        File appDir = new File(Environment.getExternalStorageDirectory(),"zxing_image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "zxing_image" + ".png";
        Log.e("sss","on:"+appDir.getAbsolutePath()+" "+fileName);

        fileNameNew  = fileName;

        OkHttpUtils//
                .get()//
                .url(url)//
                .build()//
                .execute(new FileCallBack(appDir.getAbsolutePath()+"/", fileName)//
                {
                    @Override
                    public void onBefore(Request request, int id) {
                      }

                    @Override
                    public void inProgress(float progress, long total, int id) {
//                        mProgressBar.setProgress((int) (100 * progress));
//                        Log.e("sss", "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("sss", "onError :" + e.getMessage());

                        Toast.makeText(UserViewActivity.this,"保存失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e("sss", "onResponse :" + file.getAbsolutePath());
                        newfile = file;
                        try {
                            MediaStore.Images.Media.insertImage(UserViewActivity.this.getContentResolver(), file.getAbsolutePath(), fileNameNew, null);
                            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
                            Toast.makeText(UserViewActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(UserViewActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}