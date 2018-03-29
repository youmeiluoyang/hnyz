package com.dg11185.hnyz.bean.common;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义统一的网络纯数据交互报文响应格式
 * @version 1.0
 * @since 2016-12-14
 */
public class ResponseForm {




    /**
     * 返回的类型
     */
    public enum ResponseStatus{
        SUCCESS("success"),
        ERROR("error"),
        TIP("TIP");//表示需要提醒
        private String name;
        private ResponseStatus(String name){
            this.name = name;
        }
        public String getName(){
            return  this.name;
        }
    }


    private String tip = "";           // 可以给用户看到的提示
    private String code = "";           // 接口执行码，有时候前端需要判断这个内容
    private String status = "success"; // 接口执行状态，若处理成功，则返回"success"，否则，返回"error"，默认"success"
    private String message = "操作成功"; // 接口处理结果说明，操作失败时的提示内容，根据具体业务规则设置
    private Map<String, Object> data = new HashMap<String,Object>(); // 接口数据集合
    
    public ResponseForm() {
		super();
	}

	public ResponseForm(String status, String massage, Map<String, Object> data) {
    	this.status = status;
    	this.message = massage;
    	this.data = data;
    }


    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setDataEntry(String name,Object value){
        data.put(name,value);
    }
    public Object getDataEntry(String name){
       return data.get(name);
    }
    @Override
    public String toString(){
        return JSONObject.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
}
