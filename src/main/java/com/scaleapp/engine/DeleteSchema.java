package com.scaleapp.engine;

import com.scaleapp.lib.SLog;

public class DeleteSchema {
	public static void main( String[] args )
	{
		SLog.startDefault();
		SLog.getInstance().setStdout(true);
		User.deleteSchema();
		//UserName.deleteSchema();
		//UserSession.deleteSchema();
	}
	
}
