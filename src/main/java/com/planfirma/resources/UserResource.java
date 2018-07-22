package com.planfirma.resources;



import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.planfirma.service.UserService;
import com.planfirma.utility.ErrorMessageBuilder;

@Component
@Path("/user")
public class UserResource 
{

	@Autowired
	private UserService userService;
	
	
	@Path("/getuserdetails/{userid}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public Response getUserDetails(@PathParam("userid") final Long userId)
	{
		Response res=null;
		try
		{
			System.out.println("came in user");
			res=Response.ok(userService.getUserDetails(userId)).build();
		}
		catch(Exception e)
		{
			res=Response.status(Status.INTERNAL_SERVER_ERROR).entity(ErrorMessageBuilder.buildJsonMessage(e)).build();
		}
		return res;
		
	}
	
}
