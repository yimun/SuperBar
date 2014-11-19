package com.xyworm.superbar.input;

/**
 * Describe the device for input
 * 设备结点的描述信息
 * @author linwei
 *
 */
public class Device {
	
	private int id;
	private String event;
	private String name;
	
	public Device(int id,String event,String name)
	{
		
		this.id = id;
		this.event = event;
		this.name = name;
	}
	
	public Device() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", event=" + event + ", name=" + name + "]";
	}


}
