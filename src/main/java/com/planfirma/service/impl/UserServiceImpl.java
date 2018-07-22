package com.planfirma.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planfirma.dao.DataAccessLayerException;
import com.planfirma.dao.UserDao;
import com.planfirma.dto.UserDto;
import com.planfirma.service.ApplicationException;
import com.planfirma.service.UserService;

@Service
public class UserServiceImpl  implements UserService
{
	@Autowired
	private UserDao userDao;

	public UserDto getUserDetails(Long userId) throws ApplicationException {
		
		try
		{
			return userDao.getUserDetails(userId);
		}
		catch(DataAccessLayerException e)
		{
			throw new ApplicationException("error in service  "+e.getMessage(),e);
		}
	
}
}
