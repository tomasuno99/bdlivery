package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Price {
	@Id
	private long id;
	private Float price;
	private Date startDate;
	private Date endDate;
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
