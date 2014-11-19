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
		// launcher�����Ĺ㲥
		if (action.equals(MyContext.HOME_BROADCAST)) {

			// �ر�home��̧ͷ����Ӧ
			if (intent.getExtras().getString(MyContext.FORBID_SHOW)
					.equals(MyContext.TRUE)) {
				BaseSensorService.setEnable(true);
				MyContext.startDaemonSevice(context,false);
			}
			
			// ����home��̧ͷ����Ӧ
			if (intent.getExtras().getString(MyContext.FORBID_SHOW)
					.equals(MyContext.FALSE)) {
				BaseSensorService.setEnable(false);
			}
		}

		// �ֶ����Ƶ����Ĺ㲥����ʵ�֣�
		if (action.equals(MyContext.CONTROL_BROADCAST)) {
			
		}
		
		// �����Զ�����
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
