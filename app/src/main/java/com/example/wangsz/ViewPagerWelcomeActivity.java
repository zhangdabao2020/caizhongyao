package com.example.wangsz;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangsz.adapter.ViewPagerAdapter;
import com.example.wangsz.data.Const;
import com.example.wangsz.util.SPHelper;
import com.example.wangsz.util.Util;

import com.example.wangsz.R;

/**
 * 引导页面
 * @author zhanglb
 *
 */
public class ViewPagerWelcomeActivity extends Activity implements
		OnClickListener, OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;

	// 引导图片资源
	private static final int[] pics = { R.drawable.a, R.drawable.b,
			R.drawable.c, R.drawable.d };

	// 底部小店图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;

	// 开始游戏
	private ImageButton mBeginGame;
	private TextView mStarteText1;
	private TextView mStarteText2;
	private TextView mStarteText3;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome_view_page);

		//判断是否为第一次进入，是否需要打开引导页面
		//不是第一次进入直接跳至游戏主界面
		boolean isFirstTime = SPHelper.getBoolean(this, Const.IS_FIRST_TIME_GAME, 
				Const.IS_FIRST_TIME_GAME);
		if (isFirstTime) {
			//是第一次进入，直接引导页面，并且存储值为false，供在下次进入游戏的时候判断
			SPHelper.saveBoolean(this, Const.IS_FIRST_TIME_GAME, 
					Const.IS_FIRST_TIME_GAME, false);
		} else {
			// 跳转至游戏主界面
			//Util.StartActivity(ViewPagerWelcomeActivity.this,
				//	MainActivity.class);
		}
		mStarteText1 =(TextView) findViewById(R.id.starttext1);
		mStarteText2 =(TextView) findViewById(R.id.starttext2);
		mStarteText3 =(TextView) findViewById(R.id.starttext3);
		mBeginGame = (ImageButton) findViewById(R.id.begin_game);
		mBeginGame.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 跳转至游戏主界面
				//Util.StartActivity(ViewPagerWelcomeActivity.this,
						//MainActivity.class);
				//跳转登录界面
				Util.StartActivity(ViewPagerWelcomeActivity.this,
                        LoginActivity.class);
			}
		});

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(mParams);
			iv.setImageResource(pics[i]);
			views.add(iv);
		}
		vp = (ViewPager) findViewById(R.id.viewpager);
		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		// 初始化底部小点
		initDots();

	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

		dots = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);

		if (arg0 == pics.length - 1) {
			mBeginGame.setVisibility(View.VISIBLE);
		} else {
			mBeginGame.setVisibility(View.INVISIBLE);
		}
		if(arg0 == pics.length - 2){
			mStarteText3.setVisibility(View.VISIBLE);
		}else{
			mStarteText3.setVisibility(View.INVISIBLE);
		}
		if(arg0 == pics.length - 3){
			mStarteText2.setVisibility(View.VISIBLE);
		}else{
			mStarteText2.setVisibility(View.INVISIBLE);
		}
		if(arg0 == pics.length - 4){
			mStarteText1.setVisibility(View.VISIBLE);
		}else{
			mStarteText1.setVisibility(View.INVISIBLE);
		}

	}

	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}
}