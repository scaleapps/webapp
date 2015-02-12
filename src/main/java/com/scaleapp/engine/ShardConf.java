package com.scaleapp.engine;

import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.scaleapp.lib.Base64;
import com.scaleapp.lib.Base64DecoderException;
import com.scaleapp.lib.FileOps;
import com.scaleapp.lib.JsonHelper;
import com.scaleapp.lib.Utils;

public class ShardConf {
	private static final Logger log = LoggerFactory.getLogger(ShardConf.class);
	public String db = null;
	public String usr = null;
	public Integer vsid = null;
	public String usr_pass = null;
	public String node = null;
	public String nodeIds = null;
	public int portNumber = -1;
	
	public List<String> nodeIdsList = new ArrayList<String>();
	
	
	public List<BigInteger> getIntNodeIds() throws Base64DecoderException {
		List<BigInteger> iids = new ArrayList<BigInteger>();
		for (String id : nodeIdsList) {
			iids.add(new BigInteger(Base64.decode(id)));
		}
		
		return iids;
	}
	
	public static ShardConf generate(String node, String db, String usr, String usr_pass, int vsid, int numIds, int portNumber) {
		ShardConf conf = new ShardConf();
		conf.node = node;
		conf.db = db;
		conf.usr = usr;
		conf.usr_pass = usr_pass;
		conf.vsid = vsid;
		conf.portNumber = portNumber;
		
		for (int i = 0; i < numIds; i++) {
			conf.nodeIdsList.add(Utils.getRndString(32));
		}
		
		return conf;
	}
	
	public static ShardConf loadFromString(String json) {
		ShardConf conf = new JsonTransformer().parse(json, ShardConf.class);
		//conf.nodeIdsList = JsonHelper.stringToStringList(conf.nodeIds);
		return conf;
	}
	
	public static ShardConf loadFromFile(File file) {
		String json = FileOps.readFile(file);
		return loadFromString(json);
	}
	
	public String toString() {
		return new JsonTransformer().render(this);
	}
}
