package com.emanas.middleware.models;

import java.util.List;

import com.emanas.middleware.bmr.OPBMRCompositionModel;

public class ResultSetClass {

	List<OPBMRCompositionModel> resultSet ;

	@Override
	public String toString() {
		return "ResultSetClass [resultSet=" + resultSet + "]";
	}

	public List<OPBMRCompositionModel> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<OPBMRCompositionModel> resultSet) {
		this.resultSet = resultSet;
	} 
	
}
