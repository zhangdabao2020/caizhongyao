package com.example.wangsz.myui;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;


import com.example.wangsz.R;
import com.example.wangsz.model.IWordButtonClickListener;
import com.example.wangsz.model.WordButton;
import com.example.wangsz.util.Util;

public class MyGridView extends GridView {
	public final static int COUNTS_WORDS = 24;
	
	private ArrayList<WordButton> mArrayList = new ArrayList<WordButton>();

	private MyGridAdapter mAdapter;
	
	private Context mContext;
	
	private IWordButtonClickListener mWordButtonClickListener;
	
	public MyGridView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		
		mContext = context;
		
		mAdapter = new MyGridAdapter();
		this.setAdapter(mAdapter);
	}
	
	public void updateData(ArrayList<WordButton> list) {
		mArrayList = list;
		
		// 重新设置数据�?		
		setAdapter(mAdapter);
	}

	class MyGridAdapter extends BaseAdapter {
		public int getCount() {
			return mArrayList.size();
		}

		public Object getItem(int pos) {
			return mArrayList.get(pos);
		}

		public long getItemId(int pos) {
			return pos;
		}

		public View getView(int pos, View v, ViewGroup p) {
			final WordButton holder;
			
			if (v == null) {
				v = Util.getView(mContext, R.layout.self_ui_gridview_item);
				
				holder = mArrayList.get(pos);
				
				holder.mIndex = pos;
				holder.mViewButton = (Button)v.findViewById(R.id.item_btn);
				holder.mViewButton.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						mWordButtonClickListener.onWordButtonClick(holder);
					}				
				});
				
				v.setTag(holder);
			} else {
				holder = (WordButton)v.getTag();
			}
			
			holder.mViewButton.setText(holder.mWordString);
			
			return v;
		}
	}
	
	/**
	 * 注册监听接口
	 * 
	 * @param listener
	 */
	public void registOnWordButtonClickListener(
			IWordButtonClickListener listener) {
		mWordButtonClickListener = listener;
	}
}
