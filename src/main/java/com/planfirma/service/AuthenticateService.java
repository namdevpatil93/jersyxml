package com.planfirma.service;

import com.planfirma.dto.UserDto;

public interface AuthenticateService {

	UserDto authenticateUser(UserDto user) throws ApplicationException;
	
}
