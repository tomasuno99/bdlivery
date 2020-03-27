package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;

public class Price {
	private float price;
	private Date fechaInicio = Calendar.getInstance().getTime();
	private Date fechaFin;
	
	public Price(float price) {
		this.price = price;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public void finalizePrice() {
		this.fechaFin = Calendar.getInstance().getTime();
	}
	
}
