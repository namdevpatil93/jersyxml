package com.planfirma.dao;

import com.planfirma.dto.UserDto;

public interface UserDao {
	public UserDto getUserDetails(Long userId) throws DataAccessLayerException;
}
