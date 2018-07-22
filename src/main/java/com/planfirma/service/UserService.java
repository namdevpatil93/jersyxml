package com.planfirma.service;

import com.planfirma.dto.UserDto;

public interface UserService 
{
	UserDto getUserDetails(Long userId) throws ApplicationException;
}
