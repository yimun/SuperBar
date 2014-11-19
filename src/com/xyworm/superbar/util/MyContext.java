package com.xyworm.superbar.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.xyworm.superbar.service.RingGestureService;
import com.xyworm.superbar.service.VirtualKeyService;

public class MyContext
{
	// const
	public static final String HOME_BROADCAST = "android.intent.HOME.FORBID";
	public static final String CONTROL_BROADCAST = "com.dx.superbar.CONTROL";
	public static final String PASTE_FLAG_BAR= "superbar";
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String FORBID_SHOW = "home";
	public static final int UI_NOEXECING = 0;
	public static final int UI_STARTVIRTUALKEY = 1;
	public static final int UI_STARTGESTURE = 2;
//	public static final String BLUETOOTH_NAME = "bluetooth remote";
	
	
	// 是否使用签名的事件注入模式（否则是普通手机的注入模式）
	public static final boolean UseSystemUidToInject = true;
	
	
	// variable
	public static boolean isServiceRunning = false;
	
	/**根据配置文件选择启动的service,手势或者按键*/
	public static void startDaemonSevice(Context context,boolean showToast) {
		SharedPreferences userInfo = context.getSharedPreferences("user_info", 0);
		int pattern = userInfo.getInt("pattern", 1);
		// 1 是开启虚拟按键；2 是开启手势操作
		if (pattern == UI_STARTVIRTUALKEY) {
			clearService(context);
			Intent intent = new Intent(context,
					VirtualKeyService.class);
			context.startService(intent);
			if(showToast)
				Toast.makeText(context, "开启虚拟按键服务", Toast.LENGTH_SHORT).show();
		} else if (pattern == UI_STARTGESTURE) {
			clearService(context);
			Intent intent = new Intent(context,
					RingGestureService.class);
			context.startService(intent);
			if(showToast)
				Toast.makeText(context, "开启手势服务", Toast.LENGTH_SHORT).show();
		}
	}

	public static void clearService(Context context) {
		Intent intent1 = new Intent(context, VirtualKeyService.class);
		Intent intent2 = new Intent(context, RingGestureService.class);
		context.stopService(intent1);
		context.stopService(intent2);
	}
}
