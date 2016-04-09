package cn.sibat.warn.controller;

/**
 * JSON model
 * 
 */
public class Xing implements java.io.Serializable {
	private static final long serialVersionUID = 6386677569018448529L;
	private boolean success = false;// is successful
	private String msg = "";// message
	private Object data = null;// other info

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

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
