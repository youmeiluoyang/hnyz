package com.dg11185.hnyz.bean.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class APIResponse {
	public static final String SUCCESS = "SUCCESS";	// 调用成功
	public static final String PARAMS_ERROR = "PARAMS_ERROR";	// 参数异常，不完整或非法
	public static final String INNER_ERROR = "INNER_ERROR";	// 系统繁忙，请稍后再试
	public static final String ERROR = "ERROR";	// 其他错误
	
	private String status = SUCCESS;	// 状态
	private String message = "调用成功";	// 状态说明
	private Map<String, Object> data;	// 数据
	
	public APIResponse(){}
	
	public APIResponse(String status) {
		validStatus(status);
	}
	public APIResponse(String status, String message, Map<String, Object> data) {
		validStatus(status);
		if (StringUtils.isNotBlank(message))
			this.message = message;
		this.data = data;
	}

	public String toJSONString() {
		return JSONObject.toJSONString(this);
	}
	
	public String getStatus() {
		return status;
	}

	public APIResponse setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public APIResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public APIResponse setData(Map<String, Object> data) {
		this.data = data;
		return this;
	}
	
	/**
	 * 若data未初始化，则生成对象并赋值；若data已被初始化，则直接返回data;
	 * @return
	 * @author luosiyin
	 * 2017年3月23日
	 */
	public Map<String, Object> initedData() {
		if (this.data == null) {
			this.data = new HashMap<String, Object>();
		}
		return this.data;
	}

	/**
	 * 规范化status
	 * @param status
	 * @return
	 * @author luosiyin
	 * 2017年3月23日
	 */
	private void validStatus(String status) {
		if (SUCCESS.equalsIgnoreCase(status)) {
			this.status = SUCCESS;
			this.message = "调用成功";
		} else if (PARAMS_ERROR.equalsIgnoreCase(status)) {
			this.status = PARAMS_ERROR;
			this.message = "参数异常，不完整或非法";
		} else if (INNER_ERROR.equalsIgnoreCase(status)) {
			this.status = INNER_ERROR;
			this.message = "系统繁忙，请稍后再试";
		} else if (ERROR.equalsIgnoreCase(status)) {
			this.status = ERROR;
			this.message = "其他错误";
		}
	}

}
