package com.emanas.middleware.Demog;

import org.springframework.stereotype.Component;

@Component
public class DemogFactory {

	public DemogFactory() {
	}

	public static DemogService selectDemogService(DemogServiceType service) {
		DemogService demogService = null;
		switch (service) {
		case DEFAULT:
			demogService = new DefaultDemogService();
			break;
		case ABARK:
			demogService = new AbArkDemogService();
			break;
		case EHOSPITAL:
			demogService = new EHospitalDemogService();
			break;
		case MOSIP:
			demogService = new MosipDemogService();
		}
		return demogService;
	}

	public static void getDemogServices() {
		for (DemogServiceType serType : DemogServiceType.values()) {
		}
	}
}
