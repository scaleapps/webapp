package com.scaleapp.lib;

public interface IClientKey {
	public long getRemoteId();
	public long getId();
	public KeyQuery getPublicKey(); 
	public KeyQuery getPrivateKey(); 
	public KeyQuery getSessionKey();

}
