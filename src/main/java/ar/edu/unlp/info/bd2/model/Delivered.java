package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Delivered extends OrderStatus {

	public Delivered() {}
	
	public Delivered(Date date) {
		super.date = date;
	}

	@Override
	public String getStatus() {
		return "Delivered";
	}

}
