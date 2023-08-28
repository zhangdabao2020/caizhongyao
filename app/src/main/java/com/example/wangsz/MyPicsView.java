package com.example.wangsz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.example.wangsz.data.Const;
import com.example.wangsz.util.Util;

import com.example.wangsz.R;

public class MyPicsView extends Activity implements OnClickListener {
	ImageSwitcher imageSwitcher; // 声明ImageSwitcher对象，图片显示区域
	Gallery gallery; // 声明Gallery对象，图片列表索引
	int imagePosition; // 标记图片数组下标，用于循环显示
	// 声明图片整型数组
	private String[] images;

	// 返回按钮
	private ImageButton backIBtn;

	// 保存图片
	private ImageButton saveIBtn;

	// 当前图片
	private Bitmap mMyPic;
	// 当前图片名
	private String mMyPicName;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_pictures_view);

		images = getPicsNames();

		// 隐藏右上角图标
		LinearLayout view = (LinearLayout) findViewById(R.id.title_bar_coins);
		view.setVisibility(View.INVISIBLE);

		saveIBtn = (ImageButton) findViewById(R.id.save_pic);
		saveIBtn.setVisibility(View.VISIBLE);
		saveIBtn.setOnClickListener(this);

		backIBtn = (ImageButton) findViewById(R.id.back);
		backIBtn.setOnClickListener(this);

		// 通过控件的ID获得imageSwitcher的对象
		imageSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
		// 设置自定义的图片显示工厂类
		imageSwitcher.setFactory(new MyViewFactory(this));
		// 通过控件的ID获得gallery的对象
		gallery = (Gallery) findViewById(R.id.gallery);
		// 设置自定义的图片适配器
		gallery.setAdapter(new ImageAdapter(this));
		// 实现被选中的事件监听器
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// 通过求余数，循环显示图片
				Bitmap bitmap = getBitMap(images[position % images.length]);
				mMyPic = bitmap;
				mMyPicName = images[position % images.length];
				BitmapDrawable bd = new BitmapDrawable(bitmap);
				imageSwitcher.setImageDrawable(bd);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}

	// 初始化图片名称
	private String[] getPicsNames() {
		// String[] datas = null;
		int stageIndex = Util.loadData(this)[Const.INDEX_LOAD_DATA_STAGE];
		String[] datas = new String[stageIndex + 2];
		Log.e("1111111", stageIndex + "");
		for (int i = 0; i <= stageIndex + 1; i++) {
			String[] stage = Const.QUESTION_ANSWER[i];
			datas[i] = stage[Const.QUESTION_INDEX];
			Log.e("22222", datas[i]);
		}
		return datas;
	}

	// 得到图片
	private Bitmap getBitMap(String s) {
		AssetManager assets = getAssets();
		InputStream is = null;
		try {
			is = assets.open(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
		return bitmap;
	}

	// 自定义图片适配器，继承BaseAdapter
	class ImageAdapter extends BaseAdapter {
		private Context context; // 定义上下文

		// 参数为上下文的构造方法
		public ImageAdapter(Context context) {
			this.context = context;
		}

		// 得到图片的大小
		@Override
		public int getCount() { // 设置为整型的最大数
			return images.length;
		}

		// 得到指定图片的对象
		@Override
		public Object getItem(int position) {
			return position;
		}

		// 得到指定图片的对象的ID
		@Override
		public long getItemId(int position) {
			return position;
		}

		// 显示图标列表
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(context); // 创建ImageView对象
			iv.setImageBitmap(getBitMap(images[position % images.length])); // 设置循环显示图片
			iv.setAdjustViewBounds(true); // 图片自动调整显示
			// 设置图片的宽和高
			iv.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			return iv; // 返回ImageView对象
		}
	}

	// 自定义图片显示工厂类，继承ViewFactory
	class MyViewFactory implements ViewFactory {
		private Context context; // 定义上下文

		// 参数为上下文的构造方法
		public MyViewFactory(Context context) {
			this.context = context;
		}

		// 显示图标区域
		@Override
		public View makeView() {
			ImageView iv = new ImageView(context); // 创建ImageView对象
			iv.setScaleType(ImageView.ScaleType.FIT_CENTER); // 图片自动居中显示
			// 设置图片的宽和高
			iv.setLayoutParams(new ImageSwitcher.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			return iv; // 返回ImageView对象
		}
	}

	/**
	 * 返回退出事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Util.StartActivity(MyPicsView.this, MainActivity.class);
			return false;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			Util.StartActivity(MyPicsView.this, MainActivity.class);
			break;
		case R.id.save_pic:
			saveBitmap(mMyPic);
			break;
		}
	}

	/**
	 * 保存图片至本地
	 * 
	 * @param bitmap
	 */
	private void saveBitmap(Bitmap bitmap) {
		File fileDir = new File(Environment.getExternalStorageDirectory()
				+ "/wangsz");
		FileOutputStream fos = null;
		if (!fileDir.exists()) {
			fileDir.mkdir();
		}

		File file = new File(fileDir, mMyPicName);
		
		if (file.exists()) {
			file.delete();
		}
		
		try {
			fos = new FileOutputStream(file);
			if (bitmap != null) {
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				Toast toast = Toast.makeText(getApplicationContext(), 
						"图片已保存已保存在"+ Environment.getExternalStorageDirectory() + "/wangsz",
						Toast.LENGTH_SHORT);
				toast.show();
			} 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}