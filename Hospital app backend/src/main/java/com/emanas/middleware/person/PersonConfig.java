//package com.emanas.middleware.person;
//
//import org.springframework.beans.factory.FactoryBean;
//import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//
//@Configuration
//public class PersonConfig {
//	@Bean
//	public FactoryBean<?> getBean() {
//		ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
//		factoryBean.setServiceLocatorInterface(PersonRegistry.class);
//		return factoryBean;
//	}
//}
