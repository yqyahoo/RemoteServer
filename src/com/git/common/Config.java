package com.git.common;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Config {
	
	private static Config instance = null;
	
	private static final String PATH = "config.xml";
	
	private SAXReader reader;
	private Document document;
	
	private Map<String, String> actionHandlerMap;
	
	private Config() {
		init();
	}
	
	public static Config getInstance() {
		if(instance == null) {
			synchronized(Config.class) {
				if(instance == null) {
					instance = new Config();
				}
			}
		}
		return instance;
	}
	
	private void init() {
		try {
			URL url = Config.class.getResource("/");
			String configPath = url.getPath() + PATH;
			reader = new SAXReader();
			document = reader.read(new File(configPath));
			
			actionHandlerMap = new HashMap<String, String>();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		Element rootElement = document.getRootElement();
		
		Element handlerElement = rootElement.element("actionhandler");
		initHandlerParam(handlerElement);
	}
	
	private void initHandlerParam(Element handlerElement) {
		List<Element> subElements = handlerElement.elements();
		String key = null;
		String value = null;
		for(Element element : subElements) {
			key = element.attributeValue("name");
			value = element.getText();
			actionHandlerMap.put(key, value);
		}
	}
	
	public Map<String, String> getActionHandlerMap() {
		return actionHandlerMap;
	}
	
	public static void main(String[] args) {
		Config.getInstance().loadConfig();
	}

}
