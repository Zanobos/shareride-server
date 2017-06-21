package it.zano.shareride.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.zano.shareride.base.model.Constants;

public class PropertiesLoader {

	private static final Logger log = Logger.getLogger(PropertiesLoader.class.getName());
	
	private static Properties loadProperties() {
		
		Properties props = new Properties();

		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(Constants.FileSystem.DIR_CONFIG + Constants.FileSystem.SEPARATOR
						+ Constants.FileSystem.FILE_PROPERTIES);
		try {
			props.load(inputStream);
		} catch (IOException e) {
			log.log(Level.SEVERE, "Can not load properties", e);
		}
		
		return props;

	}
	
	public static synchronized String getProperty(String key) {
		Properties properties = loadProperties();
		return properties.getProperty(key);
	}
	
	public static Integer getPropertyInt(String key) {
		String prop = getProperty(key);
		return Integer.parseInt(prop);
	}

}
