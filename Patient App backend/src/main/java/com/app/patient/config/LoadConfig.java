package com.app.patient.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.apache.log4j.PropertyConfigurator;

public class LoadConfig {
	static Logger logger = LoggerFactory.getLogger(LoadConfig.class);

	public String CONFIG_PATH = null;
	static Properties prop = null;

	public static String getConfigValue(String key) {
		return prop.getProperty(key);

	}

	public static void setProperties(Properties prop1) {
		prop = prop1;
	}

	public LoadConfig() {

		System.setProperty("org.mhmsconfig", "/home/ubuntu/release");
		CONFIG_PATH = System.getProperty("org.mhmsconfig");

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(CONFIG_PATH + "/" + "credentials.properties");
			prop.load(input);

			LoadConfig.setProperties(prop);
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
