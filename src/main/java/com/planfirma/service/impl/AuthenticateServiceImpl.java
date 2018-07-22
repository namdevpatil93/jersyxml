package com.planfirma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planfirma.dao.AuthenticateDao;
import com.planfirma.dao.DataAccessLayerException;
import com.planfirma.dto.UserDto;
import com.planfirma.service.ApplicationException;
import com.planfirma.service.AuthenticateService;

@Service
public class AuthenticateServiceImpl implements AuthenticateService
{

	@Autowired
	private AuthenticateDao authenticateDao;

	public UserDto authenticateUser(UserDto user) throws ApplicationException 
	{
		try {
			return authenticateDao.authenticateUser(user);
		} catch (DataAccessLayerException e) {
		
			throw new ApplicationException("error in service "+e.getMessage(),e);
		}
		
	}


	
	
	
}
