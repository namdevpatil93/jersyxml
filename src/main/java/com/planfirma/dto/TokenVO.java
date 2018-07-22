package com.planfirma.dto;

import java.util.Map;

public class TokenVO {
	private String userToken;
	private String activeTime;
	
	// store client specific custom parameters.
	// for voayger storing sessionid and vsessionid.
	private Map<String, String> customParams;	

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public Map<String, String> getCustomParams() {
		return customParams;
	}

	public void setCustomParams(Map<String, String> customParams) {
		this.customParams = customParams;
	}
}
