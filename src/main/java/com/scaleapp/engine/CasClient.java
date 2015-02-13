package com.scaleapp.engine;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.scaleapp.lib.SLog;

public class CasClient {
	private static final String TAG = "CasClient";
	private Cluster cluster;
	private Session session;
	private static CasClient client = null;
	
	private CasClient() {
		
	}
	
	public static CasClient getInstance() {
		if (client != null)
			return client;

		synchronized(CasClient.class) {
			if (client == null) {
				client = new CasClient();
				client.connect();
			}
		}
		return client;
	}
	
	public static Session getSession() {
		return getInstance().session;
	}
	
   	private void connect() {
	   cluster = Cluster.builder()
            .addContactPoints("54.93.107.120", "54.93.49.184", "54.93.72.213", "54.93.89.220")
            .withLoadBalancingPolicy(new RoundRobinPolicy())
            .withRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE)
            .withReconnectionPolicy(new ConstantReconnectionPolicy(100L))
            .withSocketOptions(new SocketOptions().setKeepAlive(true).setTcpNoDelay(true))
            .build();
	   	SLog.i(TAG, "connecting");
      	session = cluster.connect();
      	SLog.i(TAG, "connected");
   	}

   	public void close() {
   		session.close();
   		cluster.close();
   	}
}