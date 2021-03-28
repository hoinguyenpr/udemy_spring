package com.dpm.entity;

/**
 * 
 * @author ThuanLV6 Ajax response body
 *
 */
public class ScaleCalibrationAjaxResponseBody {

	private String msg;
	private Object data;
	public ScaleCalibrationAjaxResponseBody() {
		
	}

	public ScaleCalibrationAjaxResponseBody(String msg, Object data) {
		super();
		this.msg = msg;
		this.data = data;
	}


	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "ScaleCalibrationAjaxResponseBody [msg=" + msg + ", data=" + data + "]";
	}
	

}
