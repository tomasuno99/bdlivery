package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Sended extends OrderStatus {

	public Sended() {}
	
	public Sended(Date date) {
		super.date = date;
	}

	@Override
	public String getStatus() {
		return "Sended";
	}

}
