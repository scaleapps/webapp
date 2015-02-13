package com.scaleapp.lib;

import com.scaleapp.lib.nshttp.NsHttpRequest;
import com.scaleapp.lib.nshttp.NsHttpResponse;


public interface INsHttpServerHandler {
	NsHttpResponse handle(NsHttpRequest request);
}
