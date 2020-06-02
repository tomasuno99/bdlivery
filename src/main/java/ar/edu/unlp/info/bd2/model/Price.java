package ar.edu.unlp.info.bd2.model;

import java.util.Calendar;



import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

public class Price implements PersistentObject{
	@BsonId
	private ObjectId id;
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
		this.id = new ObjectId();
		this.price = price;
		this.startDate = startDate;
		this.actualPrice = true;
	}
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setActualPrice(Boolean actualPrice) {
		this.actualPrice = actualPrice;
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

	@Override
	public ObjectId getObjectId() {
		return this.id;
	}

	@Override
	public void setObjectId(ObjectId objectId) {
		this.id = objectId;
		
	}
	
}
