package com.emanas.middleware.Demog;

import java.util.UUID;

public class TransactionID {

	private UUID transID;

	public TransactionID() {

		this.transID = UUID.randomUUID();
	}

	public String getTransID() {
		return transID.toString();
	}

	@Override
	public String toString() {
		return "DemogTransactionID [transID=" + transID + "]";
	}

}
