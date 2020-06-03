package ar.edu.unlp.info.bd2.model;

import java.util.Date;


import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

public class OrderStatus {
	boolean isActual = true;
	Date date;
	String status;
	
	public OrderStatus() {}
	
	public OrderStatus(Date date, String status) {
		this.date = date;
		this.status = status;
	}
	
	public OrderStatus(String status) {
		this.date = new Date();
		this.status = status;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActual() {
		return isActual;
	}

	public void setActual(boolean isActual) {
		this.isActual = isActual;
	}

	public String getStatus() {
		return this.status;
	};
	
}
