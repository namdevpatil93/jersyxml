package com.planfirma.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.planfirma.dao.AuthenticateDao;
import com.planfirma.dao.DataAccessLayerException;
import com.planfirma.dto.UserDto;

@Repository
public class AuthenticateDaoImpl extends JdbcDaoSupport implements AuthenticateDao 
{

	
	public UserDto authenticateUser(UserDto user) throws DataAccessLayerException {
		UserDto userdto=null;
		
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("select * from h_user where username=? and password=? ");
			
			userdto=getJdbcTemplate().queryForObject(query.toString(), new Object[] {user.getUsername(),user.getPassword()},new RowMapper<UserDto>(){

				public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				
					UserDto user=new UserDto();
					user.setUsername(rs.getString("firstname"));
					user.setUsername(rs.getString("email"));
					return user;
				}	
			});
			return userdto;	
		}
		catch(Exception e)
		{
			throw new DataAccessLayerException("error in dao"+e.getMessage(),e);
		}
	}


	

}
