package com.git.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import net.sf.json.JSONObject;

public class RemoteServer extends UnicastRemoteObject implements IRemoteServer {

	private static final long serialVersionUID = 1L;

	public RemoteServer() throws RemoteException {
		super();
	}

	@Override
	public JSONObject handleRequest(JSONObject object) throws RemoteException {
		String status = (String) object.get("status");
		System.out.println(status);
		
		JSONObject jo = new JSONObject();
		jo.accumulate("status", 1);
		return jo;
	}

	@Override
	public void connTest() throws RemoteException {
		System.out.println("connection is success");
	}
	
	public static void run() {
		try {
			System.out.println("Start server...");
			System.setSecurityManager(new RMISecurityManager());
			IRemoteServer remoteServerObject = new RemoteServer();
			Registry registry = LocateRegistry.createRegistry(9999);
			registry.rebind("OravenServer", remoteServerObject);
			System.out.println("Server is ok...");
		} catch (RemoteException e) {
			e.printStackTrace();
			System.err.println("Server occur error, can't startup");
		}
	}
	
	public static void main(String[] args) {
		run();
	}

}
