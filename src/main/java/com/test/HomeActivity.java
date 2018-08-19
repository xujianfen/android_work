package com.test;


import com.test.banner.R;
import com.test.left.LeftMenu;
import com.test.left.MainUI;
import com.test.middle.MiddleMenu;
import com.test.right.RightMenu;
import com.user.AccountStatic;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class HomeActivity extends AppCompatActivity implements OnClickListener {
    private MainUI mainUI;
    private LeftMenu leftMenu;
    private MiddleMenu middleMenu;
    private RightMenu rightMenu;
    private ImageView imagebutton_left;
    private ImageView imagebutton_middle;
    private ImageView imagebutton_right;
    private Button button;
    private ActionMode actionMode;//使用ActioonMode完成菜单操作
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
    //		requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_home);

    //		EventBus.getDefault().post(new Account());
    //		mainUI = new MainUI(this);
    //		setContentView(mainUI);
            initButton();
            mainUI = (MainUI)findViewById(R.id.home_mainui);
            leftMenu = new LeftMenu();
            middleMenu = new MiddleMenu();
            rightMenu = new RightMenu();
            mainUI.setImageButton(imagebutton_left,imagebutton_middle,imagebutton_right);
            leftMenu.setActivity(this);
            rightMenu.setActivity(this);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.left_id,leftMenu).commit();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.middle_id,middleMenu).commit();
            getSupportFragmentManager().beginTransaction().
                    add(R.id.right_id,rightMenu).commit();


            middleMenu.setactionMode(this,actionMode,actionCallback);



        }
    public void setInit(){
        middleMenu.initokhttp();
    }
        private void initButton(){
            imagebutton_left = (ImageView)findViewById(R.id.home_left_image);
            imagebutton_middle = (ImageView)findViewById(R.id.home_middle_image);
            imagebutton_right = (ImageView)findViewById(R.id.home_right_image);
            imagebutton_left.setOnClickListener(this);
            imagebutton_middle.setOnClickListener(this);
            imagebutton_right.setOnClickListener(this);

        }
        @Override
        public void onClick(View view) {

            Toast.makeText(HomeActivity.this,"点击了我",Toast.LENGTH_SHORT).show();
            mainUI.onclickScroll(view);

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.newmenu, menu);
            return true;
        }
         private ActionMode.Callback actionCallback = new ActionMode.Callback(){

        @Override
        public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

            actionMode = null;
            //把actionMode = null传递给给MiddleMenu
            middleMenu.setActionMode(actionMode,true);

        }
        //表示我们要加载的菜单，从XML中加载
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.login, menu);

            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
                case R.id.delete:
                    delete(item);
                    break;
                default:

            }
            return false;
        }
        public boolean OnCreateContextMenuListener(Menu menu){
            getMenuInflater().inflate(R.menu.login, menu);
            return true;
        }

        public void delete(MenuItem item){

        }
        public boolean onCreatOptionsMenu(Menu menu){
            return false;
        }
    };

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
        rightMenu.banner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
        rightMenu.banner.stopAutoPlay();
    }

    /**
     *
     * 回调
    * @param requestCode
    * @param resultCode
    * @param data
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode){
//            case AccountStatic.PARSONAL:
//                leftMenu.setImage_title();
//            case AccountStatic.USERVIEWACTIVITY:
//                leftMenu.setImage_title();
//                break;
//
//        }
        leftMenu.setImage_title();
    }
}
