package com.scaleapp.engine;


import java.util.regex.Matcher;

import com.scaleapp.lib.NsHttpRequest;
import com.scaleapp.lib.NsHttpResponse;

public interface UriHandler {
	public NsHttpResponse handle(Matcher match, NsHttpRequest request) throws Exception;
}
