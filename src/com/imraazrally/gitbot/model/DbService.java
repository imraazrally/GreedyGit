package com.imraazrally.gitbot.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbService {
	private static Connection dbConnection;
	
	public static Connection getConnection(String className, String connectionString){

		if(dbConnection==null){
			try {
				Class.forName(className);
				dbConnection=DriverManager.getConnection(connectionString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return dbConnection;
	
	}
}
