package com.git.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import net.sf.json.JSONObject;

public interface IRemoteServer extends Remote {

	public JSONObject handleRequest(JSONObject object) throws RemoteException;
	
	public void connTest() throws RemoteException;
}
