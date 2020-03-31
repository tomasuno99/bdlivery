package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="price")
public class Price {
	@Column(name="price")
	private Float price;
	@Column(name="fecha_inicio")
	private Date fechaInicio = Calendar.getInstance().getTime();
	@Column(name="fecha_fin")
	private Date fechaFin;
	
	public Price() {}
	
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
