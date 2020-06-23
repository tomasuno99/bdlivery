package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Cancelled extends OrderStatus {
	
	public Cancelled() {}

	public Cancelled(Date date) {
		super.date = date;
	}

	@Override
	public String getStatus() {
		return "Cancelled";
	}

}
