package com.scaleapp.lib;

public interface IBytesDumpable {
	byte[] toBytes();
	boolean parseBytes(byte[] bytes);
}
