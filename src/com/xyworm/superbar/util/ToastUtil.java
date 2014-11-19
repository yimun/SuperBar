package com.xyworm.superbar.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xyworm.superbar.R;

public class ToastUtil {

	public static void showToastHome(Context ui) {
		adoShowToask(ui, R.drawable.hy_home, " ");
	}
	
	public static void showToastBack(Context ui) {
		adoShowToask(ui, R.drawable.hy_back, " ");
	}

	public static void adoShowToask(final Context ui, int icon, String message) {
		LayoutInflater inflater = LayoutInflater.from(ui);
		View toastView = inflater.inflate(R.layout.toast_gesture_show, null);
		((ImageView) toastView.findViewById(R.id.icon))
				.setImageResource(icon);
		Toast toast = Toast.makeText(ui, null, Toast.LENGTH_SHORT);
		toast.setView(toastView);
		toast.setGravity(Gravity.CENTER, 0, 0);
		((TextView) toastView.findViewById(R.id.toastMsg)).setText(message);
		toast.show();
	}

}
