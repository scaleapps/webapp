package com.scaleapp.engine;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.scaleapp.lib.Utils;


public class UserSession {
    private UUID uid = null;
    private String value = null;
    public long expires = -1;
    
    public UserSession() {
    }
    
    public UUID getUid() {
        return uid;
    }

    public String getValue(){
        return value;
    }
    
    public void setUid(UUID uid){
        this.uid = uid;     
    }
    
    public void setValue(String value){
    	this.value = value;
    }    
    
    public long getExpires() {
    	return this.expires;
    }
    
    public void setExpires(long expires) {
    	this.expires = expires;
    }
    
    static public void insert(UserSession usession) {
    	Session session = CasClient.getSession();
    	PreparedStatement st = session.prepare("INSERT INTO mykeyspace.sessions "
    			+ "(uid, value, expires) "
    			+ " VALUES (?, ?, ?);");
		session.execute( new BoundStatement(st).bind(
				usession.getUid(),
				usession.getValue(),
				usession.getExpires()
		   ));
    }

    static public void delete(String value) {
    	Session session = CasClient.getSession();
		
    	PreparedStatement st = session.prepare("DELETE FROM mykeyspace.sessions "
		   + " WHERE value = ?;");
		session.execute( new BoundStatement(st).bind(
				value
		   ));
    }
    
    static public UserSession lookup(String value) {
    	Session session = CasClient.getSession();
		
    	PreparedStatement st = session.prepare("SELECT * FROM mykeyspace.sessions "
		   + " WHERE value = ?;");
    	
		ResultSet rs = session.execute( new BoundStatement(st).bind(
				value
		   ));
		Row row = rs.one();
		UserSession usession = new UserSession();
		usession.setUid(row.getUUID("uid"));
		usession.setValue(row.getString("value"));
		usession.setExpires(row.getLong("expires"));
		return usession;
	}
    
   
	public static UserSession parseString(String json) {
		UserSession result = new JsonTransformer().parse(json, UserSession.class);
		return result;
	}

	public String toString() {
		return new JsonTransformer().render(this);
	}
	
	public static UserSession generate(UUID uid, long expiresDelta) {
		UserSession session = new UserSession();
		session.value = Utils.getRndString(16);
		session.expires = System.currentTimeMillis() + expiresDelta;
		session.uid = uid;
		return session;
	}
	
   	public static void createSchema() {
   		CasClient.getSession().execute("CREATE TABLE mykeyspace.sessions ("
 			   + "uid uuid PRIMARY KEY,"
 			   + "value text,"
 			   + "expires bigint);");		
   		CasClient.getSession().execute("CREATE INDEX session_uid_index ON mykeyspace.sessions (uid);");
   	}

   	public static void deleteSchema() {
   		CasClient.getSession().execute("DROP INDEX IF EXISTS mykeyspace.session_uid_index;");
   		CasClient.getSession().execute("DROP TABLE IF EXISTS mykeyspace.sessions;");
   	}
   	
}

