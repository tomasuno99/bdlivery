package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="price")
public class Price {
	@Id
	@Column(name="price_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column(name="price")
	private Float price;
	@Column(name="startdate")
	private Date startDate;
	@Column(name="enddate")
	private Date endDate;
	
	public Price() {}
	
	public Price(float price, Date startDate) {
		this.price = price;
		this.startDate = startDate;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public void finalizePrice() {
		this.endDate = Calendar.getInstance().getTime();
	}
	
}
