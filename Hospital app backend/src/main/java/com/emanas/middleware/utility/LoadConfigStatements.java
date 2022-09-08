package com.emanas.middleware.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadConfigStatements {
	static Logger logger = LoggerFactory.getLogger(LoadConfigStatements.class);

	public String CONFIG_PATH = null;
	static Properties prop = null;

	public static String getConfigValue(String key) {
		return prop.getProperty(key);
	}

	public static void setProperties(Properties prop1) {
		prop = prop1;
	}

	public LoadConfigStatements() {
		// CONFIG_PATH = System.getProperty("org.mhmsconfig");
		CONFIG_PATH = "/home/iiitb/mhms/config/";
		logger.info("config path.... :- " + CONFIG_PATH);
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(CONFIG_PATH + "/" + "config.properties");
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			LoadConfigStatements.setProperties(prop);

		} catch (IOException ex) {
			logger.error("", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}
}