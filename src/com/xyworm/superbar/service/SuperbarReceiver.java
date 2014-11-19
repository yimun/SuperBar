package com.xyworm.superbar.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xyworm.superbar.util.CursorUtils;
import com.xyworm.superbar.util.MyApp;
import com.xyworm.superbar.util.MyContext;

public class SuperbarReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		
		String action = intent.getAction();
		// launcher发来的广播
		if (action.equals(MyContext.HOME_BROADCAST)) {

			// 关闭home，抬头可响应
			if (intent.getExtras().getString(MyContext.FORBID_SHOW)
					.equals(MyContext.TRUE)) {
				BaseSensorService.setEnable(true);
				MyContext.startDaemonSevice(context,false);
			}
			
			// 开启home，抬头不响应
			if (intent.getExtras().getString(MyContext.FORBID_SHOW)
					.equals(MyContext.FALSE)) {
				BaseSensorService.setEnable(false);
			}
		}

		// 手动控制弹出的广播（待实现）
		if (action.equals(MyContext.CONTROL_BROADCAST)) {
			
		}
		
		// 开机自动启动
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			MyContext.startDaemonSevice(context,true);
		}
		
		if(action.equals(CursorUtils.ACTION_CUSTCURSOR_HIDE))
			MyApp.isCustomCursorShow = false;
		
		if(action.equals(CursorUtils.ACTION_CUSTCURSOR_SHOW))
			MyApp.isCustomCursorShow = true;
		
		if(action.equals(CursorUtils.ACTION_SENSORMOUSE_HIDE))
			MyApp.isSensorMouseShow = false;
		
		if(action.equals(CursorUtils.ACTION_SENSORMOUSE_SHOW))
			MyApp.isSensorMouseShow = true;
		
		
	}
	
}
