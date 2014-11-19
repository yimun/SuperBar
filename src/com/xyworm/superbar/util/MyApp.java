package com.xyworm.superbar.util;

import android.app.Application;
import android.view.WindowManager;

public class MyApp extends Application {
	
	public static boolean isCustomCursorShow;
	public static boolean isSensorMouseShow;
	
	private WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();

	public WindowManager.LayoutParams getMywmParams() {
		return wmParams;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		isCustomCursorShow = false;
		isSensorMouseShow = false;
	}
	
	
}
