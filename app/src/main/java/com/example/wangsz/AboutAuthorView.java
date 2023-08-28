package com.example.wangsz;

import com.example.wangsz.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.wangsz.R;

/**
 * 关于界面
 * @author zhang
 *
 */
public class AboutAuthorView extends Activity {
	
	//返回按钮
	private ImageButton backIBtn;
	
	@Override
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.about_author_view);
		
		backIBtn = (ImageButton) findViewById(R.id.about_back);
		backIBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Util.StartActivity(AboutAuthorView.this, MainActivity.class);
			}
			
		});
	}
	
	/**
	 * 返回退出事件，处理键盘推出事件
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
