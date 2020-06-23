package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Pending extends OrderStatus {

	public Pending() {}
	
	public Pending(Date date) {
		super.date = date;
	}

	@Override
	public String getStatus() {
		return "Pending";
	}

}
