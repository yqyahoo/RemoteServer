package com.git.handler;

import net.sf.json.JSONObject;

public abstract class ExcuteHandler {
	
	public abstract JSONObject handleRequest(JSONObject jo);

}
