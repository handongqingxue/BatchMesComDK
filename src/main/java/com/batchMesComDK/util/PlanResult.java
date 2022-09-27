package com.batchMesComDK.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PlanResult {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//定义jackson对象  
    private static final ObjectMapper MAPPER = new ObjectMapper();  
    private Boolean success; 
	//响应业务状态 
    private Integer status;  
    //相应消息  
    private String msg;  
    //相应中的数据  
    private Object data;  
    
    private String url;
  
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static ObjectMapper getMAPPER() {  
        return MAPPER;  
    }
	 
    public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "PlanResult [status=" + status + ", msg=" + msg + ", data=" + data + ", url=" + url + "]";
	}  
}
