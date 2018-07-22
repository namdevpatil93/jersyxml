package com.planfirma.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter  {

	public CORSFilter() { }

	public void init(FilterConfig fConfig) throws ServletException { }

	public void destroy() {	}

	public void doFilter(
		ServletRequest request, ServletResponse response, 
		FilterChain chain) throws IOException, ServletException {

		((HttpServletResponse)response).addHeader(
			"Access-Control-Allow-Origin", "*"
		);
		((HttpServletResponse)response).addHeader(
				//OPTIONS header not required.
				//"Access-Control-Allow-Methods", "GET, POST, OPTIONS"
				"Access-Control-Allow-Methods", "GET,POST,PUT"
			);
		((HttpServletResponse)response).addHeader(
				"Access-Control-Allow-Headers", "Content-Type,usertoken,authToken,userId,wpsess,Authorization,status,resource_type,pageStartIndex,rows,pageEndIndex,searchText,sortColumn,sortBy,instituteId,fromDate,toDate,orderNo,country"
			);		
		chain.doFilter(request, response);
	}

}
