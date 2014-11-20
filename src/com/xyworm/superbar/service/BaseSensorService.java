package com.xyworm.superbar.service;

import java.io.IOException;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.xyworm.superbar.input.InputCmd;
import com.xyworm.superbar.util.MyContext;
import com.xyworm.superbar.util.RotateUtil;
/**
 * @ClassName  BaseSensorService 
 * @Description  ���������࣬̽���û�̧ͷ����������������
 * @author linweizh@qq.com
 * @date 2014-11-20
 */
public abstract class BaseSensorService extends Service implements
		SensorEventListener {
	private SensorManager mSensorManager;

	private static final int HEAD_DOWN = 0;
	private static final int HEAD_UP = 1;
	private static final float HEAD_BETA_THRESHOLD = 35f;
	private static boolean isEnable = true;
	private int mHeadState = HEAD_DOWN;

	private View mFloatView;
	private static Handler delayHandler = new Handler();
	
	private Runnable delayRunnable = new Runnable(){
		public void run(){
			hideWindows();
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();
		initSensor();
		isEnable = true;
		MyContext.isServiceRunning = true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSensorManager.unregisterListener(this);
		MyContext.isServiceRunning = false;
	}

	/**
	 * �����������Ƿ���Ӧ
	 * 
	 * @param bool
	 */
	public static void setEnable(boolean bool) {
		isEnable = bool;
	}

	/**
	 * �������������ϵ���ͼ
	 * 
	 * @param view
	 */
	public void setFloatView(View view) {
		this.mFloatView = view;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// Log.i(TAG,"onSensorChanged");

		// ���ڲ���Ӧ״̬����launcher�� ֱ�ӷ���
		if (!isEnable) {
			// mSensorManager.unregisterListener(this);
			return;
		}

		// �ж��Ƿ�����̧ͷ����
		int type = event.sensor.getType();
		if (type == Sensor.TYPE_ORIENTATION) {
			float beta = RotateUtil.getHeadBeta(event.values);
			if (beta > HEAD_BETA_THRESHOLD) {
				if (HEAD_DOWN == mHeadState) {
					this.onHeadUP();
				}
				mHeadState = HEAD_UP;
			}
		}
	}

	/**
	 * ̧ͷ���������ص�
	 */
	public abstract void onHeadUP();

	/**
	 * ��ͷ���������ص�������Ļص������ɴ��������ƣ�����ʱ����
	 */
	public void onHeadDown() {
		mHeadState = HEAD_DOWN;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	/**
	 * ��ʼ��������
	 */
	private void initSensor() {
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		boolean success = false;
		success = mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
		if (!success)
			Toast.makeText(this, "Can not init sensor", Toast.LENGTH_SHORT)
					.show();
	}

	public void hideWindows() {
		if (mFloatView != null)
			mFloatView.setVisibility(View.GONE);
		this.onHeadDown();
	}

	public void showWindows() {
		if (mFloatView != null) {
			mFloatView.setVisibility(View.VISIBLE);
			delayHideWindow(4000);
		}
	}

	/* ����һ��ʱ���޲����Ļ������ڻ��Զ���ʧ */
	protected void delayHideWindow(long time) {
		delayHandler.removeCallbacks(delayRunnable);
		delayHandler.postDelayed(delayRunnable, time);
	}

	/**
	 * ע�뷵�ؼ��¼�
	 */
	public void pressBack() {
		if (MyContext.UseSystemUidToInject) {
			new Thread() {
				public void run() {
					InputCmd.runKeyEvent(KeyEvent.KEYCODE_BACK);
				}
			}.start();
		} else {
			try {
				Runtime.getRuntime().exec(
						new String[] { "su", "-c",
								"input keyevent " + KeyEvent.KEYCODE_BACK });
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ע��Home���¼�
	 */
	public void pressHome() {
		if (MyContext.UseSystemUidToInject) {
			new Thread() {
				public void run() {
					InputCmd.runKeyEvent(KeyEvent.KEYCODE_HOME);
				}
			}.start();
		} else {
			try {
				Runtime.getRuntime().exec(
						new String[] { "su", "-c",
								"input keyevent " + KeyEvent.KEYCODE_HOME });
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
