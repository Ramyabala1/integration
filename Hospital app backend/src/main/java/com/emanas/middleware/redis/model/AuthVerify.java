package com.emanas.middleware.redis.model;

public class AuthVerify {
	String status;
	DemogDetails demodetail;
	String transactionId;
	String error;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DemogDetails getDemodetail() {
		return demodetail;
	}

	public void setDemodetail(DemogDetails demodetail) {
		this.demodetail = demodetail;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
