package com.emanas.middleware.config;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.emanas.middleware.utility.LoadConfig;

@Component
@Validated
//@ConfigurationProperties("emanas.api")
@Service
public class ConfigurationUrl {

	public String url;

	public ConfigurationUrl()

	{
		new LoadConfig();
		if ((LoadConfig.getConfigValue("ENVIRONMENT").equalsIgnoreCase("DEV"))) {
			setUrl(LoadConfig.getConfigValue("DEV_EMANAS_API_URL"));
		}

		if ((LoadConfig.getConfigValue("ENVIRONMENT").equalsIgnoreCase("TEST"))) {
			setUrl(LoadConfig.getConfigValue("TEST_EMANAS_API_URL"));
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
