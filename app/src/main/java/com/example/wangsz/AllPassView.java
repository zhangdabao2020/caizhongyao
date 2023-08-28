package com.example.wangsz;

import com.example.wangsz.data.Const;
import com.example.wangsz.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wangsz.R;

/**
 * 通过界面
 * 
 * @author wangsz
 *
 */
public class AllPassView extends Activity implements OnClickListener{
	
	// 微信分享View
	private ImageButton mViewSharedDB;
	
	// 联系我们View
	private ImageButton mViewMailToUs;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.all_pass_view);
		
		//隐藏右上角图标
		LinearLayout view = (LinearLayout) findViewById(R.id.title_bar_coins);
		view.setVisibility(View.INVISIBLE);
		
		Util.saveData(this, Const.BEGIN_STAGE, -1);
		
		//控件点击事件绑定
		mViewSharedDB = (ImageButton) findViewById(R.id.pass_share);
		mViewSharedDB.setOnClickListener(this);
		
		mViewMailToUs = (ImageButton) findViewById(R.id.btn_mail);
		mViewMailToUs.setOnClickListener(this);
		
		Util.saveData(this, Const.BEGIN_STAGE, -1);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		case R.id.back:
			Util.StartActivity(this, MainActivity.class);
			break;
		case R.id.pass_share:
			Toast.makeText(getApplicationContext(), "恭喜通关，有建议欢迎发邮件",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn_mail:
			Toast.makeText(getApplicationContext(), 
					"尚未联网，如有宝贵意见请联系邮箱353227876@qq.com",
					Toast.LENGTH_LONG).show();
			break;
		}
	}
	
	/**
	 * 返回退出事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Util.StartActivity(this, MainActivity.class);
			return false;
		}
		return false;
	}

}
