package com.test.left;

//import com.example.fen.R;




import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;

import com.test.banner.R;
import com.test.middle.MiddleRecyclerViewAdapter;

public class MainUI extends RelativeLayout {
	private Context context;
	private FrameLayout leftMenu;
	private FrameLayout middleMenu;
	private FrameLayout rightMenu;
	private FrameLayout middleMask;
	private Scroller mScroller;
	private final int LEFT_NUM = 1;
	private final int MIDEELE_NUM = 2;
	private final int RIGHT_NUM = 3;
	private int mainUINum =  MIDEELE_NUM;
//	public static final int LEFT_ID = 0xaabbcc;
//	public static final int MIDEELE_ID = 0xaaccbb;
//	public static final int RIGHT_ID = 0xccbbaa;

	public MainUI(Context context) {
		super(context);
		initView(context);
	}

	public MainUI(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);

	}

	 private void initView(Context context) {
		this.context = context;
		mScroller = new Scroller(context, new DecelerateInterpolator());
		leftMenu = new FrameLayout(context);
		middleMenu = new FrameLayout(context);
		rightMenu = new FrameLayout(context);
		middleMask = new FrameLayout(context);
		leftMenu.setBackgroundColor(0x88000000);
		middleMenu.setBackgroundColor(Color.WHITE);
		rightMenu.setBackgroundColor(0x88000000);
		middleMask.setBackgroundColor(0x88000000);
		leftMenu.setId(R.id.left_id);
		middleMenu.setId(R.id.middle_id);
		rightMenu.setId(R.id.right_id);
		addView(leftMenu);
		addView(middleMenu);
		addView(rightMenu);
		addView(middleMask);
		middleMask.setAlpha(0);
	}

	public float onMiddleMask(){
		System.out.println("闁繑妲戞惔锟�:"+middleMask.getAlpha());
		return middleMask.getAlpha();
	}

	 @Override
	 public void scrollTo(int x, int y) {
		super.scrollTo(x, y);
		onMiddleMask();
		int curX = Math.abs(getScrollX());
		float scale = curX/(float)leftMenu.getMeasuredWidth();
		middleMask.setAlpha(scale);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		middleMenu.measure(widthMeasureSpec, heightMeasureSpec);
		middleMask.measure(widthMeasureSpec, heightMeasureSpec);
		int realWidth = MeasureSpec.getSize(widthMeasureSpec);
		int tempWidthMeasure = MeasureSpec.makeMeasureSpec(
				(int) (realWidth * 0.8f), MeasureSpec.EXACTLY);
		leftMenu.measure(tempWidthMeasure, heightMeasureSpec);
		rightMenu.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		middleMenu.layout(l, t, r, b);
		middleMask.layout(l, t, r, b);
		leftMenu.layout(l - leftMenu.getMeasuredWidth(), t, r, b);
		rightMenu.layout(
				l + middleMenu.getMeasuredWidth(),
				t,
				l + 2*middleMenu.getMeasuredWidth()
				, b);
		/*+ rightMenu.getMeasuredWidth()*/
	}
	/**
	 * 閹恒儲鏁归弶銉ㄥ殰HomeActiviy閻ㄥ嫭甯舵禒锟�
	 *
	 */
	private ImageView left;
	private ImageView middle;
	private ImageView right;
	private View v;
	public void setImageButton(ImageView left,ImageView middle,ImageView
			right){
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	private boolean isTestCompete;
	private boolean isleftrightEvent;
	/**
	 * 閻愮懓鍤懣婊冨礋鐟欙箑褰傛禍瀣╂
	 *
	 */

	private View view;
	public void onclickScroll(View view){
		this.view = view;
		scroll();
	}
	 public void scroll(){
		 if(!MiddleRecyclerViewAdapter.pressed){
			 return;
		 }
		switch (view.getId()) {
			case R.id.home_left_image:

				System.out.println("dddd");
				if(mainUINum != LEFT_NUM){
					mScroller.startScroll(0, 0,
							-leftMenu.getMeasuredWidth(), 0,
							200);
					/**
					 * 閹兼粍鎼锋担锟�1
					 */
					mainUINum = LEFT_NUM;
					if(left!=null&&middle!=null&&right!=null){
						left.setImageResource(R.mipmap.ic_left_true);
						middle.setImageResource(R.mipmap.ic_home_false);
						right.setImageResource(R.mipmap.ic_right_false);
					}
					System.out.println("mainUINum:"+mainUINum);
				}

				break;
			case R.id.home_middle_image:

				if(mainUINum != MIDEELE_NUM){
					mScroller.startScroll(0, 0,
							0, 0,
							200);
					/**
					 * 閹兼粍鎼锋担锟�3
					 *
					 */
					mainUINum = MIDEELE_NUM;
					if(left!=null&&middle!=null&&right!=null){
						left.setImageResource(R.mipmap.ic_left_false);
						middle.setImageResource(R.mipmap.ic_home_true);
						right.setImageResource(R.mipmap.ic_right_false);
					}
					System.out.println("mainUINum:"+mainUINum);
				}
				break;
			case R.id.home_right_image:

				if(mainUINum != RIGHT_NUM){
					mScroller.startScroll(0, 0,
							middleMenu.getMeasuredWidth(), 0,
							200);
					/**
					 * 閹兼粍鎼锋担锟�2
					 */
					mainUINum = RIGHT_NUM;
					if(left!=null&&middle!=null&&right!=null){
						left.setImageResource(R.mipmap.ic_left_false);
						middle.setImageResource(R.mipmap.ic_home_false);
						right.setImageResource(R.mipmap.ic_right_true);

					}

					System.out.println("mainUINum:"+mainUINum);

				}
				break;
		}
		invalidate();

	}
	 @Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		 if(!MiddleRecyclerViewAdapter.pressed){
			 return false;
		 }
		if (!isTestCompete) {
			getEventType(ev);
			return true;
		}
		if (isleftrightEvent) {

			switch (ev.getActionMasked()) {
					case MotionEvent.ACTION_MOVE:

						int curScrollX = getScrollX();
						int dis_x = (int) (ev.getX() - point.x);
						int expectX = -dis_x + curScrollX;
						int finalX = 0;
						if (expectX < 0) {
							finalX = Math.max(expectX, -leftMenu.getMeasuredWidth());
						} else {
							finalX = Math.min(expectX, middleMenu.getMeasuredWidth());
						}
						scrollTo(finalX, 0);
						point.x = (int) ev.getX();
					break;

				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:

					curScrollX = getScrollX();
					if (Math.abs(curScrollX) > leftMenu.getMeasuredWidth() >> 1) {
						if (curScrollX < 0) {
							mScroller.startScroll(curScrollX, 0,
									-leftMenu.getMeasuredWidth() - curScrollX, 0,
									200);
							/**
							 * 閹兼粍鎼锋担锟�1
							 */
							mainUINum = LEFT_NUM;
							if(left!=null&&middle!=null&&right!=null){
								left.setImageResource(R.mipmap.ic_left_true);
								middle.setImageResource(R.mipmap.ic_home_false);
								right.setImageResource(R.mipmap.ic_right_false);

							}
							System.out.println("mainUINum:"+mainUINum);
						} else {
							if (Math.abs(curScrollX) > middleMenu.getMeasuredWidth() >> 1){
								mScroller.startScroll(curScrollX, 0,
										middleMenu.getMeasuredWidth() - curScrollX, 0,
										200);
								/**
								 * 閹兼粍鎼锋担锟�2
								 */
								mainUINum = RIGHT_NUM;
								if(left!=null&&middle!=null&&right!=null){

									left.setImageResource(R.mipmap.ic_left_false);
									middle.setImageResource(R.mipmap.ic_home_false);
									right.setImageResource(R.mipmap.ic_right_true);
								}
								System.out.println("mainUINum:"+mainUINum);
							}
							else{
								mScroller.startScroll(curScrollX, 0, -curScrollX, 0, 200);
								/**
								 * 閹兼粍鎼锋担锟�3
								 *
								 */
								mainUINum = MIDEELE_NUM;
								if(left!=null&&middle!=null&&right!=null){
									left.setImageResource(R.mipmap.ic_left_false);
									middle.setImageResource(R.mipmap.ic_home_true);
									right.setImageResource(R.mipmap.ic_right_false);
								}
								System.out.println("mainUINum:"+mainUINum);

							}
						}

					} else {
						mScroller.startScroll(curScrollX, 0, -curScrollX, 0, 200);
						mainUINum = MIDEELE_NUM;
						if(left!=null&&middle!=null&&right!=null){
							left.setImageResource(R.mipmap.ic_left_false);
							middle.setImageResource(R.mipmap.ic_home_true);
							right.setImageResource(R.mipmap.ic_right_false);
						}
						System.out.println("mainUINum:"+mainUINum);
						System.out.println("mainUINum:"+mainUINum);

					}
					invalidate();
					isleftrightEvent = false;
					isTestCompete = false;
					break;
			}
		} else {
			switch (ev.getActionMasked()) {
				case MotionEvent.ACTION_UP:
					isleftrightEvent = false;
					isTestCompete = false;
					break;

				default:
					break;
			}
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if (!mScroller.computeScrollOffset()) {
			return;
		}
		int tempX = mScroller.getCurrX();
		scrollTo(tempX, 0);
	}

	private Point point = new Point();
	private static final int TEST_DIS = 20;

	private void getEventType(MotionEvent ev) {
		switch (ev.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				point.x = (int) ev.getX();
				point.y = (int) ev.getY();
				super.dispatchTouchEvent(ev);
				break;

			case MotionEvent.ACTION_MOVE:
				int dX = Math.abs((int) ev.getX() - point.x);
				int dY = Math.abs((int) ev.getY() - point.y);
				if (dX >= TEST_DIS && dX > dY) { //瀹革箑褰稿鎴濆З
					isleftrightEvent = true;
					isTestCompete = true;
					point.x = (int) ev.getX();
					point.y = (int) ev.getY();
				} else if (dY >= TEST_DIS && dY > dX) { // 娑撳﹣绗呭鎴濆З
					isleftrightEvent = false;
					isTestCompete = true;
					point.x = (int) ev.getX();
					point.y = (int) ev.getY();
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				super.dispatchTouchEvent(ev);
				isleftrightEvent = false;
				isTestCompete = false;
				break;
		}
	}

}
