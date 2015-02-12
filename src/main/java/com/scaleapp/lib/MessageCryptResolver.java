package com.scaleapp.lib;

public interface MessageCryptResolver {
	public KeyQuery getUserCurrentPubKey(long uid);
	public KeyQuery getPubKeyById(long uid, long keyId);
}
