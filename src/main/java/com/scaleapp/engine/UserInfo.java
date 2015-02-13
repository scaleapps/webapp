package com.scaleapp.engine;

import java.util.UUID;

public class UserInfo {
	public String username;
	public UUID uid;

	public static UserInfo parseString(String json) {
		UserInfo result = new JsonTransformer().parse(json, UserInfo.class);
		return result;
	}

	public String toString() {
		return new JsonTransformer().render(this);
	}
}
