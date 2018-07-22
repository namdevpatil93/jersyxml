package com.planfirma.dao;

public class DataAccessLayerException extends Exception
{
	private static final long serialVersionUID = 18349323234328L;

	public DataAccessLayerException(String errorMessage, Throwable cause) 
	{
		super(errorMessage, cause);
	}
}
