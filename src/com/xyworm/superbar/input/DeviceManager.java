package com.xyworm.superbar.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xyworm.superbar.util.MyContext;


/**
 * 获取所有的设备结点
 * 并能根据名称获取相应的设别结点名
 * 
 * @author linwei
 * 
 */
public class DeviceManager {

	private ArrayList<Device> mlist = new ArrayList<Device>();

	public ArrayList<Device> getAllDevices() {
		parseAll();
		return mlist;
	}

	/**
	 * 解析所有设备 核心：getevent -S
	 */
	public void parseAll() {
		mlist.clear();
		Process exec;
		try {
			exec = Runtime.getRuntime().exec(new String[] { "su", "-c", "getevent -S" });
			InputStreamReader r = new InputStreamReader(exec.getInputStream());
			BufferedReader br = new BufferedReader(r);

			boolean find = false;
			String s;
			Device dv = new Device();
			while ((s = br.readLine()) != null) {
				if (!find)
					find = parseFirst(s, dv);
				else {
					parseSecnd(s, dv);
					find = false;
					mlist.add(dv);
					dv = new Device();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * prase device id and event
	 * @param s
	 * @param dv
	 * @return
	 */
	private boolean parseSecnd(String s, Device dv) {
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("\"(.*?)\"");
		Matcher m = p.matcher(s);
		if (m.find()) {
			dv.setName(m.group(1));
			return true;
		} else
			return false;

	}

	/**
	 * prase device name
	 * @param s
	 * @param dv
	 * @return
	 */
	private boolean parseFirst(String s, Device dv) {
		
		Pattern p = Pattern.compile("add device (.*?): (.*)");
		Matcher m = p.matcher(s);
		if (m.find()) {
			dv.setId(Integer.valueOf(m.group(1)));
			dv.setEvent(m.group(2));
			return true;
		} else
			return false;
	}
	
	/**
	 * 通过设备名称寻找设备结点名
	 * @param name
	 * @return
	 */
	public String findEventByName(String name){
		if(name==null)
			name = "BT3.0 & 2.4G Air keyboard";
		for(Device item : mlist){
			if(item.getName().contains(name)) return item.getEvent();
		}
		return null;
	}

}
