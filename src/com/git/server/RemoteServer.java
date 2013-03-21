package com.git.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import com.git.common.Config;
import com.git.handler.ExcuteHandler;

import net.sf.json.JSONObject;

public class RemoteServer extends UnicastRemoteObject implements IRemoteServer {

	private static final long serialVersionUID = 1L;
	
	private static Map<String, String> actionHandlerMap;
	private ExcuteHandler excuteHandler;

	public RemoteServer() throws RemoteException {
		super();
	}

	@Override
	public JSONObject handleRequest(JSONObject object) throws RemoteException {
		String status = (String) object.get("status");
		System.out.println(status);
		
		String handler = object.getString("handler");
		JSONObject jo = null;
		try {
			String handlerClass = actionHandlerMap.get(handler);
			excuteHandler = (ExcuteHandler) Class.forName(handlerClass).newInstance();
			jo =excuteHandler.handleRequest(object);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return jo;
	}

	@Override
	public void connTest() throws RemoteException {
		System.out.println("connection is success");
	}
	
	public static void run() {
		try {
			System.out.println("Load config");
			Config.getInstance().loadConfig();
			actionHandlerMap = Config.getInstance().getActionHandlerMap();
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
