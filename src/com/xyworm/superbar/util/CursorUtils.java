package com.xyworm.superbar.util;

import android.content.Context;
import android.content.Intent;

/***
 * 
 * ���ڽ�����ʽ�������ʾ���صĹ�����
 * ��ҪΪ�ⲿ�㲥�����ⲿӦ�÷��ͣ���̨������ܲ���Ӧ
 * @author linwei
 * @date 2014/09/19
 */

public class CursorUtils {

	/**Superbar���ƽ�����Ҫ��ʱ����*/
	public static final String ACTION_TEMP_SHOW = "com.xyworm.allcursor.TEMP_SHOW_CURSOR";
	public static final String ACTION_TEMP_HIDE = "com.xyworm.allcursor.TEMP_HIDE_CURSOR";
	
	/**ͷ���������Ŀ�����ʾ���ع㲥*/
	public static final String ACTION_SENSORMOUSE_SHOW = "com.xyworm.sensormouse.SHOW_CURSOR";
	public static final String ACTION_SENSORMOUSE_HIDE = "com.xyworm.sensormouse.HIDE_CURSOR";
	
	/**��ͨ��ָ���Ƶ�������ʾ���ع㲥*/
	public static final String ACTION_CUSTCURSOR_SHOW = "com.xyworm.custcursor.SHOW_CURSOR";
	public static final String ACTION_CUSTCURSOR_HIDE = "com.xyworm.custcursor.HIDE_CURSOR";

	
	/**
	 * ����Ϊ���÷�����
	 * ��ʾ���� onResume() �е��� CursorUtils.show___();
	 * ���أ���onPause() �е��� CursorUtils.hide___();
	 * 
	 * ע����Ҫ��ʾ����֮һ��Ӧ�ò���Ҫ�ֶ�������ʾ�����أ���������������Ϸ������궼����Ҫ��
	 * �������Ӧ�ò���Ҫ�����κι㲥
	 */
	
	//-----------------------------------------------------------------------------------
	
	/**������Ҫ���õ�Ӧ���У�ΰ������ᡢ�׸����ƵӦ��*/
	public static void showCustCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_CUSTCURSOR_SHOW));
	}
	public static void hideCustCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_CUSTCURSOR_HIDE));
	}
	
	
	/**������Ҫ���õ�Ӧ���У����ε�����*/
	public static void showSensorMouse(Context con){
		con.sendBroadcast(new Intent(ACTION_SENSORMOUSE_SHOW));
	}
	public static void hideSensorMouse(Context con){
		con.sendBroadcast(new Intent(ACTION_SENSORMOUSE_HIDE));
	}
	
	/**��ʱ��ʾ����superbar*/
	public static void tempShowCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_TEMP_SHOW));
	}
	public static void tempHideCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_TEMP_HIDE));
	}
}
