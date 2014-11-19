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
	
	
	// �Ƿ�ʹ��ǩ�����¼�ע��ģʽ����������ͨ�ֻ���ע��ģʽ��
	public static final boolean UseSystemUidToInject = true;
	
	
	// variable
	public static boolean isServiceRunning = false;
	
	/**���������ļ�ѡ��������service,���ƻ��߰���*/
	public static void startDaemonSevice(Context context,boolean showToast) {
		SharedPreferences userInfo = context.getSharedPreferences("user_info", 0);
		int pattern = userInfo.getInt("pattern", 1);
		// 1 �ǿ������ⰴ����2 �ǿ������Ʋ���
		if (pattern == UI_STARTVIRTUALKEY) {
			clearService(context);
			Intent intent = new Intent(context,
					VirtualKeyService.class);
			context.startService(intent);
			if(showToast)
				Toast.makeText(context, "�������ⰴ������", Toast.LENGTH_SHORT).show();
		} else if (pattern == UI_STARTGESTURE) {
			clearService(context);
			Intent intent = new Intent(context,
					RingGestureService.class);
			context.startService(intent);
			if(showToast)
				Toast.makeText(context, "�������Ʒ���", Toast.LENGTH_SHORT).show();
		}
	}

	public static void clearService(Context context) {
		Intent intent1 = new Intent(context, VirtualKeyService.class);
		Intent intent2 = new Intent(context, RingGestureService.class);
		context.stopService(intent1);
		context.stopService(intent2);
	}
}
