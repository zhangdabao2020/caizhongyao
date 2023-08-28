package com.example.wangsz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 描述：操作SharePreferences的工具类
 * 
 * @author zhang
 * 
 */
public class SPHelper {
	
	/**
	 * 存储boolean数据
	 * 
	 * @param context
	 * @param spName
	 * @param key 关键字
	 * @param value 值
	 */
	public static void saveBoolean(Context context, String spName, String key, boolean value) {
		Editor editor = context.getSharedPreferences(spName, 0).edit();
		editor.putBoolean(key, value);
		editor.commit();// 提交修改
	}

	/**
	 * 获取sp中的boolean数据
	 * @param context
	 * @param spName 
	 * @param key 关键字
	 * @return
	 */
	public static boolean getBoolean(Context context, String spName, String key) {
		SharedPreferences sp = context.getSharedPreferences(spName, 0);
		if (sp != null && key != null) {
			return sp.getBoolean(key, true);
		}
		return true;
	}
}