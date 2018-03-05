package com.dxc.restcontroller.model;

import java.io.Serializable;

import com.google.gson.Gson;

public class ResponseModel implements Serializable {

	private String result;
	private Object data;

	public ResponseModel() {
	}

	public ResponseModel(String result, Object data) {
		super();
		this.result = result;
		this.data = data;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String toJSON() {
		Gson json = new Gson();
		
		return json.toJson(this);
	}
	
}
