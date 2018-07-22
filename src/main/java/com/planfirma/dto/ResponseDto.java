package com.planfirma.dto;

import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.planfirma.constants.AppConstants;
/**
 * @author namdev
 * 
 *
 */
@Component
@XmlRootElement
public class ResponseDto{

	private static final long serialVersionUID = 1;

	private Integer responseCode=AppConstants.SUCCESS;
	private String responseMsg="";
	private  Object result;
	private static  String timestamp=new Timestamp(new Date().getTime())+"";
	
	
	public Integer getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public static String getTimestamp() {
		return timestamp;
	}
	public static void setTimestamp(String timestamp) {
		ResponseDto.timestamp = timestamp;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
