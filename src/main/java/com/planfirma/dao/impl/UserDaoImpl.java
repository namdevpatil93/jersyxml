package com.planfirma.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.planfirma.dao.DataAccessLayerException;
import com.planfirma.dao.UserDao;
import com.planfirma.dto.UserDto;

@Repository
public class UserDaoImpl extends JdbcDaoSupport  implements UserDao
{
	public UserDto getUserDetails(Long userId) throws DataAccessLayerException {
	UserDto userdto=null;
		
		try
		{
			StringBuilder query=new StringBuilder();
			query.append("select * from h_user where  user_id=? ");
			
			userdto=getJdbcTemplate().queryForObject(query.toString(), new Object[] {userId},new RowMapper<UserDto>(){

				public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				
					UserDto user=new UserDto();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("location"));
					user.setFirstName(rs.getString("firstname"));
					user.setEmail(rs.getString("email"));
					
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
