package com.scaleapp.engine;

import java.security.SecureRandom;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.scaleapp.lib.BCrypt;
import com.scaleapp.lib.SLog;


public class User {
    private static final String TAG = "User";
	private UUID uid = null;
    private String username = null;
    private String hashp = null;
    private UserSession session = null;
    
    public User() {
    	uid = UUID.randomUUID();
    }
    
    public UserSession getSession() {
    	return session;
    }
    
    public void setSession(UserSession session) {
    	this.session = session;
    }
    
    public String getHashp() {
    	return this.hashp;
    }
    
    public void setHashp(String hashp) {
    	this.hashp = hashp;
    }
    
    public void setPassword(String password) {
    	hashp = BCrypt.hashpw(password, BCrypt.gensalt(12, new SecureRandom()));
    }

    public boolean checkPassword(String password) {
    	return BCrypt.checkpw(password, hashp);
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
    
    public void setUserName(String s){
        username = s;
    }    
    
    static public void insert(User user) {
    	Session session = CasClient.getSession();
    	PreparedStatement st = session.prepare("INSERT INTO mykeyspace.users "
    			+ "(uid, username, hashp) "
    			+ " VALUES (?, ?, ?);");
		session.execute( new BoundStatement(st).bind(
		   user.getUid(),
		   user.getUserName(),
		   user.getHashp()
		   ));
    }

    static public void delete(UUID uid) {
    	Session session = CasClient.getSession();
		
    	PreparedStatement st = session.prepare("DELETE FROM mykeyspace.users "
		   + " WHERE uid = ?;");
		session.execute( new BoundStatement(st).bind(
				uid
		   ));
    }
    
    static public User lookup(UUID uid) {
    	Session session = CasClient.getSession();
		
    	PreparedStatement st = session.prepare("SELECT * FROM mykeyspace.users "
		   + " WHERE uid = ?;");
    	
		ResultSet rs = session.execute( new BoundStatement(st).bind(
		   UUID.randomUUID()
		   ));
		Row row = rs.one();
		User user = new User();
		user.setUid(row.getUUID("uid"));
		user.setHashp(row.getString("hashp"));
		user.setUserName(row.getString("username"));
		return user;
	}
    
    public UserInfo toUserInfo() {
    	UserInfo inf = new UserInfo();
    	inf.username = this.username;
    	inf.uid = this.uid;
    	return inf;
    }
    
   	public static void createSchema() {
   		CasClient.getSession().execute("CREATE TABLE mykeyspace.users ("
 			   + "uid uuid PRIMARY KEY,"
 			   + "username text,"
 			   + "hashp text);");
   		
   		CasClient.getSession().execute("CREATE INDEX uid_index ON mykeyspace.users (uid);");
   	}

   	public static void deleteSchema() {
   		SLog.i(TAG, "dropping index");
   		CasClient.getSession().execute("DROP INDEX IF EXISTS mykeyspace.uid_index;");
   		SLog.i(TAG, "dropping table");
   		CasClient.getSession().execute("DROP TABLE IF EXISTS mykeyspace.users;");
   	}
}
