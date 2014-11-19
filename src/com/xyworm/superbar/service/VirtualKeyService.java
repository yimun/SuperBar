package com.xyworm.superbar.service;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xyworm.ringsdk.OnRingEventListener;
import com.xyworm.ringsdk.RingManager;
import com.xyworm.ringsdk.utils.RawEvent;
import com.xyworm.superbar.R;
import com.xyworm.superbar.util.CursorUtils;
import com.xyworm.superbar.util.MyApp;

public class VirtualKeyService extends BaseSensorService implements
		OnClickListener, OnRingEventListener {
	// 获取屏幕的相关信息
	WindowManager mWindowManager = null;
	// 设置悬浮窗口的一些属性
	WindowManager.LayoutParams mLayoutParams = null;
	// 显示的悬浮窗口
	private static View mMenuViewUI;
	// home
	private ImageView mMenuHome;
	// back�?
	private ImageView mMenuBack;
	// setting设置键，为了特殊的应用设置
	private TextView mMenuSetting;
	// 这个相当于一个可以移动的对话框
	private PopupWindow mPopupWindow;
	private ImageView mMenuHome2;
	private ImageView mMenuBack2;
	// 控制是否载入扩展按钮的静态变量
	public static boolean sShowGallery = false;
	// 判断操作鼠标是否由本程序自己启动
	private boolean showMyself = false;
	//
	private int currentFocusIndex;
	private static final int BTN_BACK = 0;
	private static final int BTN_HOME = 1;
	private static final int BTN_MAX = 2;
	private RingManager mRingManager = new RingManager();

	@Override
	public void onCreate() {
		super.onCreate();
		setUpMenuView();
		createView();

	}

	@Override
	public void onDestroy() {
		mWindowManager.removeView(mMenuViewUI);
		super.onDestroy();
	}

	private void setUpMenuView() {
		// 按情况加载所需要的布局文件
		// 当没有向程序发送请求的时候
		if (!sShowGallery) {
			mMenuViewUI = LayoutInflater.from(this).inflate(R.layout.floatmenu,
					null);
			this.setFloatView(mMenuViewUI);
		} else {// 当接收到扩展请求后
			mMenuViewUI = LayoutInflater.from(this).inflate(
					R.layout.menu4gallery, null);
			mMenuSetting = (TextView) mMenuViewUI
					.findViewById(R.id.btn_setting4gallery);
			mMenuSetting.setOnClickListener(this);
		}

		mMenuHome = (ImageView) mMenuViewUI.findViewById(R.id.btn_home);
		mMenuHome2 = (ImageView) mMenuViewUI.findViewById(R.id.btn_home2);
		mMenuBack = (ImageView) mMenuViewUI.findViewById(R.id.btn_back);
		mMenuBack2 = (ImageView) mMenuViewUI.findViewById(R.id.btn_back2);
		mMenuHome.setOnClickListener(this);
		mMenuHome2.setOnClickListener(this);
		mMenuBack.setOnClickListener(this);
		mMenuBack2.setOnClickListener(this);

		// 底部检测View监听事件
		RelativeLayout back_detect = (RelativeLayout) mMenuViewUI
				.findViewById(R.id.menu);
		back_detect.setOnClickListener(this);
	}

	private void createView() {
		mWindowManager = (WindowManager) getApplicationContext()
				.getSystemService("window");
		mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, LayoutParams.TYPE_SYSTEM_ERROR,
				LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_FULLSCREEN
						| LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				PixelFormat.TRANSPARENT);
		mWindowManager.addView(mMenuViewUI, mLayoutParams);
		hideWindows();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		/*case R.id.btn_back:
		case R.id.btn_back2:
			pressBack();
			hideWindows();
			break;

		case R.id.btn_home:
		case R.id.btn_home2:
			pressHome();
			hideWindows();
			break;*/
		case R.id.btn_setting4gallery:
			Toast.makeText(getApplicationContext(), "setting",
					Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu: // 底部View的监听
			pressByTabIndex();
			break;

		default:
			if (mPopupWindow != null && mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			}
			break;
		}
	}

	@Override
	public void onHeadUP() {
		showWindows();
		judgeShowACursor();
		// 开始监听飞鼠移动事件
		SharedPreferences userInfo = getSharedPreferences("user_info", 0);
		String deviceName = userInfo.getString("device_name", "none");
		mRingManager.setRingName(deviceName);
		mRingManager.setRingEventListener(this);
		mRingManager.runListener();
		currentFocusIndex = 0;
		switchTab(0);
	}

	@Override
	public void onHeadDown() {
		super.onHeadDown();
		// 自己显示自己隐藏
		if (showMyself) {
			CursorUtils.hideCustCursor(this);
		}
		mRingManager.killListener();

	}

	void judgeShowACursor() {
		// 如果两个鼠标都没有显示，则显示默认的鼠标用来点击
		if (!MyApp.isCustomCursorShow && !MyApp.isSensorMouseShow) {
			CursorUtils.showCustCursor(this);
			showMyself = true;
		} else {
			showMyself = false;
		}
	}

	/**
	 * 根据当前焦点的选项进行按键注入
	 */
	void pressByTabIndex() {
		switch (currentFocusIndex) {
		case BTN_HOME:
			pressHome();
			break;
		case BTN_BACK:
			pressBack();
			break;
		}
		hideWindows();
	}

	/**
	 * 切换图标选中状态
	 * @param deta 位移
	 */
	void switchTab(int deta) {
		if ((deta == -1 && currentFocusIndex == 0)
				|| (deta == 1 && currentFocusIndex == BTN_MAX - 1)) {
			return;
		}
		currentFocusIndex += deta;
		refreshBg();
		switch (currentFocusIndex) {
		case BTN_HOME:
			mMenuHome.setImageResource(R.drawable.hy_home);
			mMenuHome2.setImageResource(R.drawable.hy_home);

			break;
		case BTN_BACK:
			mMenuBack.setImageResource(R.drawable.hy_back);
			mMenuBack2.setImageResource(R.drawable.hy_back);
			break;
		}
	}

	
	/**
	 * 重置tab图标
	 */
	void refreshBg() {
		mMenuHome.setImageResource(R.drawable.hy_home_pressed);
		mMenuHome2.setImageResource(R.drawable.hy_home_pressed);
		mMenuBack.setImageResource(R.drawable.hy_back_pressed);
		mMenuBack2.setImageResource(R.drawable.hy_back_pressed);
	}

	@Override
	public void onDirectEvent(int deviceDataStruct) {
		// TODO Auto-generated method stub
		switch (deviceDataStruct) {
		case 2: // LEFT
			switchTab(-1);
			break;
		case 3: // RIGHT
			switchTab(1);
			break;

		}
	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onRawEvent(RawEvent event) {
		// TODO Auto-generated method stub
		
	}
}
