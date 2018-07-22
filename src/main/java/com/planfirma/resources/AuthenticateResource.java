package com.planfirma.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.planfirma.dto.ResponseDto;
import com.planfirma.dto.UserDto;
import com.planfirma.service.AuthenticateService;
import com.planfirma.utility.ErrorMessageBuilder;
import com.planfirma.utility.TokenUtility;

@Component
@Path("/authenticate")
public class AuthenticateResource
{
	@Autowired
	private AuthenticateService authenticateService;
	
	@Autowired
	private TokenUtility tokenUtility;
	
	
	@Path("/loginuser") 
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response userLogin(UserDto user){
		Response response = null;
		
		try {
			
			System.out.println("username "+user.getUsername());
			System.out.println("password" +user.getPassword());
			
			UserDto userresponse=authenticateService.authenticateUser(user);
			
			if(userresponse==null)
			{
				ResponseDto responsedto=new ResponseDto();
				responsedto.setResponseMsg("username or password is invalid");
				response=Response.ok(responsedto).build();
			}
			else
			{
				userresponse.setUsertoken(TokenUtility.generateUsertoken(userresponse));
				
				tokenUtility.getInstance().makeRedisToken(userresponse.getUsertoken());
				
				response=Response.ok(userresponse).build();
			}
			
		} catch (Exception e)
		{
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(ErrorMessageBuilder.buildJsonMessage(e)).build();
		}
		
		return response;
	}
}
