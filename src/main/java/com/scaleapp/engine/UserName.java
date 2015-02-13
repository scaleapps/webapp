package com.scaleapp.engine;

import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


public class UserName {
    private UUID uid = null;
    private String username = null;
    
    public UserName() {
    }
    
    public UUID getUid() {
        return uid;
    }

    public String getUserName(){
        return username;
    }
    
    public void setUid(UUID uid){
        this.uid = uid;     
    }
    
    public void setUserName(String name){
        username = name;
    }    
    
    static public void insert(UserName userName) {
    	Session session = CasClient.getSession();
    	PreparedStatement st = session.prepare("INSERT INTO mykeyspace.usernames "
    			+ "(uid, username) "
    			+ " VALUES (?, ?);");
		session.execute( new BoundStatement(st).bind(
		   userName.getUid(),
		   userName.getUserName()
		   ));
    }

    static public void delete(String userName) {
    	Session session = CasClient.getSession();
		
    	PreparedStatement st = session.prepare("DELETE FROM mykeyspace.usernames "
		   + " WHERE username = ?;");
		session.execute( new BoundStatement(st).bind(
				userName
		   ));
    }
    
    static public UserName lookup(String name) {
    	Session session = CasClient.getSession();
		
    	PreparedStatement st = session.prepare("SELECT * FROM mykeyspace.usernames "
		   + " WHERE username = ?;");
    	
		ResultSet rs = session.execute( new BoundStatement(st).bind(
				name
		   ));
		Row row = rs.one();
		UserName userName = new UserName();
		userName.setUid(row.getUUID("uid"));
		userName.setUserName(row.getString("username"));
		return userName;
	}
       
   	public static void createSchema() {
   		CasClient.getSession().execute("CREATE TABLE mykeyspace.usernames ("
 			   + "uid uuid,"
 			   + "username text PRIMARY KEY);");
   		
   		CasClient.getSession().execute("CREATE INDEX username_index ON mykeyspace.usernames (username);");
   	}

   	public static void deleteSchema() {
   		CasClient.getSession().execute("DROP INDEX IF EXISTS mykeyspace.username_index;");
   		CasClient.getSession().execute("DROP TABLE IF EXISTS mykeyspace.usernames;");
   	}
}
