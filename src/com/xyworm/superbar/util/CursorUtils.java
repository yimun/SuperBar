package com.xyworm.superbar.util;

import android.content.Context;
import android.content.Intent;

/***
 * 
 * 关于交互方式中鼠标显示隐藏的工具类
 * 主要为外部广播，由外部应用发送，后台服务接受并响应
 * @author linwei
 * @date 2014/09/19
 */

public class CursorUtils {

	/**Superbar手势界面需要临时隐藏*/
	public static final String ACTION_TEMP_SHOW = "com.xyworm.allcursor.TEMP_SHOW_CURSOR";
	public static final String ACTION_TEMP_HIDE = "com.xyworm.allcursor.TEMP_HIDE_CURSOR";
	
	/**头部控制鼠标的控制显示隐藏广播*/
	public static final String ACTION_SENSORMOUSE_SHOW = "com.xyworm.sensormouse.SHOW_CURSOR";
	public static final String ACTION_SENSORMOUSE_HIDE = "com.xyworm.sensormouse.HIDE_CURSOR";
	
	/**普通戒指控制的鼠标的显示隐藏广播*/
	public static final String ACTION_CUSTCURSOR_SHOW = "com.xyworm.custcursor.SHOW_CURSOR";
	public static final String ACTION_CUSTCURSOR_HIDE = "com.xyworm.custcursor.HIDE_CURSOR";

	
	/**
	 * 以下为调用方法：
	 * 显示：在 onResume() 中调用 CursorUtils.show___();
	 * 隐藏：在onPause() 中调用 CursorUtils.hide___();
	 * 
	 * 注：需要显示其中之一的应用才需要手动调用显示和隐藏，但比如艺龙的游戏两个鼠标都不需要，
	 * 因此他的应用不需要发送任何广播
	 */
	
	//-----------------------------------------------------------------------------------
	
	/**以下需要调用的应用有：伟健的相册、雷哥的视频应用*/
	public static void showCustCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_CUSTCURSOR_SHOW));
	}
	public static void hideCustCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_CUSTCURSOR_HIDE));
	}
	
	
	/**以下需要调用的应用有：杜鑫的设置*/
	public static void showSensorMouse(Context con){
		con.sendBroadcast(new Intent(ACTION_SENSORMOUSE_SHOW));
	}
	public static void hideSensorMouse(Context con){
		con.sendBroadcast(new Intent(ACTION_SENSORMOUSE_HIDE));
	}
	
	/**临时显示隐藏superbar*/
	public static void tempShowCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_TEMP_SHOW));
	}
	public static void tempHideCursor(Context con){
		con.sendBroadcast(new Intent(ACTION_TEMP_HIDE));
	}
}
