package com.hswarov.intelligentparking.util;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Log和Toast统一管理类
 * 
 * @author way
 * 
 */
public class L
{

	private L()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static boolean isDebug = false;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "tag";

	// 下面四个是默认tag的函数
	public static void i(String msg)
	{
		if (isDebug)
			Log.i(TAG, msg);
	}

	public static void d(String msg)
	{
		if (isDebug)
			Log.d(TAG, msg);
	}

	public static void e(String msg)
	{
		if (isDebug)
			Log.e(TAG, msg);
	}

	public static void v(String msg)
	{
		if (isDebug)
			Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void d(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}

	public static void v(String tag, String msg)
	{
		if (isDebug)
			Log.i(tag, msg);
	}
	
	/**
	 * 
	 * @param context
	 * @param test
	 * @param time
	 *            0为短Toast，其它为长Toast
	 */
	public static void toast(Context context, String test, int time) {
		switch (time) {
		case 0:
			time = Toast.LENGTH_SHORT;
			break;
		default:
			time = Toast.LENGTH_LONG;
			break;
		}
		if (test != null && context!= null) {
			if (!Thread.currentThread().getName().equals("main")) {
				Looper.prepare();
				Toast.makeText(context, test, time).show();
				Looper.loop();
			} else {
				Toast.makeText(context, test, time).show();
			}
		}
	}
}