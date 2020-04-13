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
	@Column(name="price_id", nullable=false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	@Column(name="price", nullable=false)
	private Float price;
	@Column(name="startdate", nullable=false)
	private Date startDate;
	@Column(name="enddate")
	private Date endDate;
	@Column(name="actual_price")
	private Boolean actualPrice;
	
	public Price() {}
	
	public Price(float price, Date startDate) {
		this.price = price;
		this.startDate = startDate;
		this.actualPrice = true;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public Boolean getActualPrice() {
		return this.actualPrice;
	}
	
	
	public void finalizePrice(Date startDate) {
		if (this.endDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			this.endDate = cal.getTime();
		}
		this.actualPrice = false;
	}
	
}
