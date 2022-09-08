package com.emanas.middleware.models;

import java.util.List;

import com.emanas.middleware.bmr.IPBMRCompositionModel;

public class ResultListRMComp {
	
	List<Restraint> resultSet ;

	public List<Restraint> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<Restraint> resultSet) {
		this.resultSet = resultSet;
	}

}
