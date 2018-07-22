package com.planfirma.dao;

import com.planfirma.dto.UserDto;

public interface AuthenticateDao {
	UserDto authenticateUser(UserDto user) throws DataAccessLayerException;
	
}
