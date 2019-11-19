package cn.enjoy.sys.model;

import java.io.Serializable;

public class SelectModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7220149795636342347L;

	private String key;
	
	private String value;


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
