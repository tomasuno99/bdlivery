package ar.edu.unlp.info.bd2.model;

import java.util.Date;


import org.springframework.data.annotation.Id;

public abstract class OrderStatus {
	@Id
	protected long id;
	protected boolean isActual = true;
	protected Date date;
	
	
	
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

	public abstract String getStatus();
	
}
