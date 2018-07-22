package com.planfirma.service.impl;

import org.springframework.stereotype.Service;

import com.planfirma.dao.DataAccessLayerException;
import com.planfirma.dao.UserDao;
import com.planfirma.dao.impl.UserDaoImpl;
import com.planfirma.dto.UserDto;
import com.planfirma.service.ApplicationException;
import com.planfirma.service.UserService;

@Service
public class UserServiceImpl  implements UserService
{
	private UserDao userDao=new UserDaoImpl();
	
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
