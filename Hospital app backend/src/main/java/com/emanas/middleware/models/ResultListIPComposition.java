package com.emanas.middleware.models;

import java.util.List;

import com.emanas.middleware.bmr.IPBMRCompositionModel;

public class ResultListIPComposition {
	
	List<IPBMRCompositionModel> resultSet ;

	public List<IPBMRCompositionModel> getResultSet() {
		return resultSet;
	}

	public void setResultSet(List<IPBMRCompositionModel> resultSet) {
		this.resultSet = resultSet;
	}

}
