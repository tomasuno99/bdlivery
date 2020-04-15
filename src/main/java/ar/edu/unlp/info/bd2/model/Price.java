package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.DateTime;

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
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

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
			DateTime sdate = new DateTime (startDate);
			this.endDate = sdate.minusDays(1).toDate();
		}
		this.actualPrice = false;
	}
	
}
