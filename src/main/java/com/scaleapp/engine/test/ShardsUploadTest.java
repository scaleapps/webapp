package com.scaleapp.engine.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scaleapp.engine.ShardConf;
import com.scaleapp.lib.HttpConn;
import com.scaleapp.lib.SLog;

public class ShardsUploadTest {
    private static final Logger log = LoggerFactory.getLogger(ShardsUploadTest.class);
	public static void main( String[] args ) {
		SLog.startDefault();
		
		try {
			ShardConf conf = ShardConf.generate("node", "db", "usr", "usr_pass", 1, 1, 42);
			HttpConn.put("http", "localhost", 8080, "/shards/" + 1, conf.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("exception=", e);
		}
	}
}
