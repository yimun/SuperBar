package com.xyworm.superbar.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import com.xyworm.ringsdk.OnRingEventListener;
import com.xyworm.ringsdk.RingManager;
import com.xyworm.ringsdk.utils.RawEvent;
import com.xyworm.superbar.R;
import com.xyworm.superbar.input.RingTrajectory;
import com.xyworm.superbar.ui.TrajectoryView;
import com.xyworm.superbar.util.CursorUtils;
import com.xyworm.superbar.util.ToastUtil;

/**
 * @author linwei
 * 
 *         戒指设备的监听服务主类，实例化类 负责对戒指手势输入事件的收集和处理
 * 
 */

public class RingGestureService extends BaseSensorService implements OnRingEventListener{

	private static RingTrajectory trajAnalyst;
	public int KEY_TO_LISTEN = RawEvent.BTN_LEFT; //
	public boolean isCollecting = false;

	/** 是否监听处于监听模式中，抬头开启 */
	public static boolean isInGestureMode = false;
	private static View mGestureView;// 透明窗体
	private TrajectoryView tragView;
	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mLayoutParams;
	RingManager mRingManager = new RingManager();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		trajAnalyst = new RingTrajectory(this); // 初始化轨迹检测
		SharedPreferences userInfo = getSharedPreferences("user_info", 0);
		// 设置蓝牙hid设备的名称
		String deviceName = userInfo.getString("device_name", "none");
		mRingManager.setRingName(deviceName);
		mRingManager.setRingEventListener(this);
		createFloatView();
	}

	@Override
	public void onDestroy() {
		mWindowManager.removeView(mGestureView);
		super.onDestroy();
	}

	private void createFloatView() {
		mGestureView = LayoutInflater.from(this).inflate(R.layout.gesture_main,
				null);
		this.setFloatView(mGestureView);

		mGestureView.getBackground().setAlpha(240);
		mWindowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, LayoutParams.TYPE_SYSTEM_ERROR,
				LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_FULLSCREEN
						| LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				PixelFormat.TRANSPARENT);
		mWindowManager.addView(mGestureView, mLayoutParams);
		tragView = (TrajectoryView) mGestureView.findViewById(R.id.trajview);
		hideWindows();
	}

	

	public void setGestureOn() {
		isInGestureMode = true;
		mRingManager.runListener();
	}

	public void setGestureOff() {
		isInGestureMode = false;
		mRingManager.killListener();
	}

	public void onGestureBegin() {
		Log.i("****", "onGestureBegin");

	}

	private void onGestureOver() {
		Log.i("****", "onGestureOver");
		if (!RingTrajectory.RT_Gesture_Valid)
			return;
		// 刷新手势显示
		
		tragView.setPath(trajAnalyst.getPathAfterAdapte());
		tragView.invalidate();
		 
		// 相应操作
		String gesName = trajAnalyst.getGestureName();
		if (gesName.equals(RingTrajectory.GES_TYPE_BACK)) {
			ToastUtil.showToastBack(this);
			pressBack();
			delayHideWindow(1500);

		}else if (gesName.equals(RingTrajectory.GES_TYPE_HOME)) {
			ToastUtil.showToastHome(this);
			pressHome();
			delayHideWindow(1500);
		}else{
			Toast.makeText(this, gesName,Toast.LENGTH_SHORT).show();
		}
	}

	public static void saveCurrGesture(String name) {
		// TODO Auto-generated method stub
		trajAnalyst.addGesture(name);
	}

	@Override
	public void onHeadUP() {
		// 鼠标隐藏
		hideGlobalCursor();
		setGestureOn();
		showWindows();
		tragView.init();
	}

	@Override
	public void showWindows() {
		/*Bitmap bm = Blur.backblur(this, 12);

		if (bm == null)
			Toast.makeText(this, "毛玻璃渲染失败", Toast.LENGTH_SHORT).show();
		else {
			if (isOri()) {
				Matrix m = new Matrix();
				m.setRotate(-90f, (float) bm.getWidth() / 2,
						(float) bm.getHeight() / 2);
				try {
					bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
							bm.getHeight(), m, true);

				} catch (OutOfMemoryError ex) {
					
				}

			}

			mGestureView.setBackgroundDrawable(new BitmapDrawable(bm));
		}*/
		super.showWindows();
	}

	@Override
	public void onHeadDown() {
		super.onHeadDown();
		showGlobalCursor();

	}

	@Override
	public void hideWindows() { // 其中调用onHeadDown
		super.hideWindows();
		setGestureOff();
	}

	public void hideGlobalCursor() {
		CursorUtils.tempHideCursor(this);
	}

	public void showGlobalCursor() {
		CursorUtils.tempShowCursor(this);
	}

	public boolean isOri() {
		WindowManager wm = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		int width, height;
		width = wm.getDefaultDisplay().getWidth() - 7; // 防止鼠标被完全隐藏
		height = wm.getDefaultDisplay().getHeight() - 7;
		return width > height;
	}

	@Override
	public void onDirectEvent(int deviecDataStruct) {
		
	}

	@Override
	public void onError(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onRawEvent(RawEvent ev) {
		if (!isInGestureMode)
			return;
		// 开始收集标志
		if (!isCollecting && ev.getType() == RawEvent.EV_KEY
				&& ev.getCode() == KEY_TO_LISTEN
				&& ev.getValue() == RawEvent.DOWN) {
			isCollecting = true;
			trajAnalyst.onActionDown();
			this.onGestureBegin();
		}

		// 结束收集标志
		if (isCollecting && ev.getType() == RawEvent.EV_KEY
				&& ev.getCode() == KEY_TO_LISTEN
				&& ev.getValue() == RawEvent.UP) {
			isCollecting = false;
			trajAnalyst.onActionUp();
			this.onGestureOver();

		}

		// 正在收集的点
		if (isCollecting && ev.getType() == RawEvent.EV_REL) {
			trajAnalyst.onInputEventValues(ev);
		}
	}
}
