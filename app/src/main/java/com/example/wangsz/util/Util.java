package com.example.wangsz.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.wangsz.R;
import com.example.wangsz.data.Const;
import com.example.wangsz.model.IAlertDialogButtonListener;

public class Util {

	private static AlertDialog mAlertDialog;

	public static View getView(Context context, int layoutId) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//用于寻找xml文件，动态载入界面
		View layout = inflater.inflate(layoutId, null);

		return layout;
	}

	/**
	 * 界面跳转
	 * 
	 * @param //content
	 *            当前页面
	 * @param desti
	 *
	 *            目标页面Class
	 */
	public static void StartActivity(Context context, Class desti) {
		Intent intent = new Intent();
		intent.setClass(context, desti);
		context.startActivity(intent);

		// 关闭当前Activity
		((Activity) context).finish();
	}

	/**
	 * 显示提示对话框
	 * 
	 * @param context
	 *            标题
	 * @param message
	 *            提示内容
	 * @param listener
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	//消息框
	public static void showDialog(Context context, String message,
			final IAlertDialogButtonListener listener) {
		View dialogView = null;

		AlertDialog.Builder builder = new AlertDialog.Builder(context,
				R.style.Theme_Transparent);
		dialogView = getView(context, R.layout.dialog_view);

		ImageButton btnOkView = (ImageButton) dialogView
				.findViewById(R.id.btn_ok);
		ImageButton btnCancelView = (ImageButton) dialogView
				.findViewById(R.id.btn_cancel);
		TextView textMessageView = (TextView) dialogView
				.findViewById(R.id.tv_dialog_message);

		textMessageView.setText(message);

		btnOkView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 关闭对话框
				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}

				// 事件回调
				if (listener != null) {
					listener.onClick();
				}
			}
		});

		btnCancelView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 关闭对话框
				if (mAlertDialog != null) {
					mAlertDialog.cancel();
				}
			}
		});

		// 为dialog设置View
		builder.setView(dialogView);
		mAlertDialog = builder.create();

		// 显示对话框
		mAlertDialog.show();
	}

	/**
	 * 数据保存
	 * 
	 * @param context
	 * @param stageIndex
	 *            关数
	 * @param coins
	 *            金币数
	 */
	public static void saveData(Context context, int stageIndex, int coins) {
		FileOutputStream fos = null;

		try {
			fos = context.openFileOutput(Const.FILE_NAME_SAVE_DATA,
					Context.MODE_PRIVATE);
			DataOutputStream dos = new DataOutputStream(fos);

			dos.writeInt(stageIndex);
			if (coins != -1) {
				dos.writeInt(coins);
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

	/**
	 * 读取数据
	 * 
	 * @param context
	 * @return
	 */
	public static int[] loadData(Context context) {
		FileInputStream fis = null;
		int[] datas = { Const.BEGIN_STAGE, Const.TOTAL_COINT };

		try {
			fis = context.openFileInput(Const.FILE_NAME_SAVE_DATA);
			DataInputStream dis = new DataInputStream(fis);

			datas[Const.INDEX_LOAD_DATA_STAGE] = dis.readInt();
			datas[Const.INDEX_LOAD_DATA_COINS] = dis.readInt();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return datas;
	}
}
