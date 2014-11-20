package com.xyworm.superbar.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

import com.xyworm.superbar.R;

/**
 * 
 * @author linwei
 *
 * 用于绘制手势轨迹的View
 */
public class TrajectoryView extends View {

	public static final int UP = 0;
	public static final int DOWN = 1;
	private Paint paint;
	private int[] colors;
	private PathEffect effect = new PathEffect();
	private Path path;
	private WindowManager mWM;
	private ImageView mMouseView;
	private LayoutParams mParams;

	public TrajectoryView(Context context) {
		super(context);
		init();

	}

	public TrajectoryView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TrajectoryView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init() {
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10);
		colors = new int[] { Color.BLACK, Color.BLUE, Color.CYAN, Color.GREEN,
				Color.MAGENTA, Color.RED, Color.YELLOW };
		effect = new CornerPathEffect(10);
		path = null;
		initMouseView();
	}

	public void setPath(Path path) {
		this.path = path;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 将背景填充成白色
		// canvas.drawColor(Color.WHITE);
		if (path == null){
			Log.i("TrajectoryView","null");
			return;
		}

		// 绘制红色轨迹
		paint.setStyle(Paint.Style.STROKE);
		paint.setPathEffect(effect);
		paint.setColor(colors[2]);
		canvas.drawPath(path, paint);
		invalidate();
	}

	// 创建鼠标视图
	public void initMouseView() {
		mWM = (WindowManager) this.getContext().getSystemService(
				Context.WINDOW_SERVICE);
		mMouseView = new ImageView(this.getContext());
		mMouseView.setBackgroundResource(R.drawable.mouse);
		mParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
			//	WindowManager.LayoutParams.TYPE_NAVIGATION_BAR , // 2010  2018
				2010,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		mParams.gravity = Gravity.LEFT | Gravity.TOP;

		mParams.x = 0;
		mParams.y = 0;
		mWM.addView(mMouseView, mParams);
		mMouseView.setVisibility(View.GONE);
		// 否则listener中的onTouch方法会首先消费触摸事件，无法接收到action move等事件
		this.setOnClickListener(null); 
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			mMouseView.setVisibility(View.VISIBLE);
			break;
		case MotionEvent.ACTION_UP:
			mMouseView.setVisibility(View.GONE);
			break;
		case MotionEvent.ACTION_MOVE:
			mParams.x = (int) event.getX();
			mParams.y = (int) event.getY();
			mWM.updateViewLayout(mMouseView, mParams);
			break;
		default:
			break;
		}*/
		return super.onTouchEvent(event);
	
	}
	
	

}