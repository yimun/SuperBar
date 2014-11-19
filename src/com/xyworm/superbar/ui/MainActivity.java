package com.xyworm.superbar.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.xyworm.superbar.R;
import com.xyworm.superbar.util.MyContext;

public class MainActivity extends Activity {
	SharedPreferences userInfo;
	Button btn_change;
	EditText edt_name;
//	String initDeviceName = "bluetooth remote4 ";
	final String initDeviceName = "MTK"; // 默认的鼠标名称

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		this.setTitle("设置SuperBar的交互方式");
		userInfo = getSharedPreferences("user_info", 0);
		initUI();
	}

	@Override
	protected void onResume() {
		// 根据配置文件�?��带有传感器监听的service
		super.onResume();
		// startSevice();
		MyContext.startDaemonSevice(this, false);
	}


	private void initUI() {

		btn_change = (Button) findViewById(R.id.btn_change);
		edt_name = (EditText) findViewById(R.id.editDeviceName);

		String deviceName = userInfo.getString("device_name", "none");
		if (deviceName.equalsIgnoreCase("none")) {
			deviceName = initDeviceName;
			userInfo.edit().putString("device_name", initDeviceName).commit();
		}
		edt_name.setText(deviceName);
		btn_change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edt_name.getText().toString() != null
						&& !edt_name.getText().toString().equalsIgnoreCase("")) {
					userInfo.edit()
							.putString("device_name",
									edt_name.getText().toString()).commit();
					Toast.makeText(MainActivity.this, "修改成功，重启服务",
							Toast.LENGTH_SHORT).show();
					MyContext.startDaemonSevice(MainActivity.this, true);

				} else {
					Toast.makeText(MainActivity.this, "输入有误",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		RadioGroup group = (RadioGroup) this.findViewById(R.id.radioGroup1);
		int pattern = userInfo.getInt("pattern", 1);
		// 1 是开启虚拟按键；2 是开启手势操作		
		if (pattern == 1) {
			group.check(R.id.radio0);
		} else if (pattern == 2) {
			group.check(R.id.radio1);
		}

		// 绑定匿名监听		
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				// 获取变更后的选中项的ID
				int radioButtonId = arg0.getCheckedRadioButtonId();
				switch (radioButtonId) {
				case R.id.radio0: // 使用虚拟按键
					userInfo.edit().putInt("pattern", 1).commit();
					Log.v("", "" + userInfo.getInt("pattern", 1));
					Toast.makeText(MainActivity.this, "切换到虚拟按键",
							Toast.LENGTH_SHORT).show();
					MyContext.startDaemonSevice(MainActivity.this, false);
					break;
				case R.id.radio1: // 使用手势
					userInfo.edit().putInt("pattern", 2).commit();
					Toast.makeText(MainActivity.this, "切换到手势服务",
							Toast.LENGTH_SHORT).show();
					MyContext.startDaemonSevice(MainActivity.this, false);
					break;
				}
			}
		});
	}
}