package com.planfirma.filter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import com.planfirma.dto.ResponseDto;
import com.planfirma.utility.TokenUtility;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

@Service
public class JsonRequestFilter   implements ContainerRequestFilter  {

	private TokenUtility tokenUtility=new TokenUtility();

	public ContainerRequest filter(ContainerRequest request) 
	{
		String token=request.getHeaderValue("usertoken");
	//	List<PathSegment> segments1=request.getPathSegments();
	
		System.out.println("in filter ");
		
		ResponseDto responsedto=new ResponseDto();
		
		if(token==null || token=="")
		{
			responsedto.setResponseCode(400);
			responsedto.setResponseMsg("given token cannot be empty.");
			Response denied = Response.status(Response.Status.OK).entity(responsedto).type("application/json").build();
			throw new WebApplicationException(denied);
		}
		else if(!tokenUtility.validateRedisUsertoken(token))
		{
			responsedto.setResponseCode(400);
			responsedto.setResponseMsg("given token is not valid");
			Response denied = Response.status(Response.Status.OK).entity(responsedto).type("application/json").build();
			throw new WebApplicationException(denied);
		}
		else
		{
			tokenUtility.updateRedisToken(token);
			
			//add in session
		}

		return request;
	}
	
}
