package com.planfirma.utility;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Pattern;

import com.planfirma.dto.TokenVO;



public class Utility {

	private static final Pattern numeric = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+$");
	private static Utility utility;	
	public static Utility getInstance() {
		if (utility == null)
			utility = new Utility();

		return utility;
	}


	public static TokenVO createToken(String token) {
		TokenVO subToken = new TokenVO();
		subToken.setActiveTime(new Timestamp(new Date().getTime()) + "");
		subToken.setUserToken(token);
		return subToken;
	}



	
}