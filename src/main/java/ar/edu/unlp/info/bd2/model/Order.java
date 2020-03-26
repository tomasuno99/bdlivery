package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class Order {
	Date dateOfOrder;
	String address;
	Float coordX; 
	Float coordY;
	User client;

	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = client;
		
		return this;
	}
}
