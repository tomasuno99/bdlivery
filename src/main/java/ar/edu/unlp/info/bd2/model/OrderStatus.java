package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

@BsonDiscriminator
public abstract class OrderStatus implements PersistentObject{
	@BsonId
	protected ObjectId id;
	protected boolean isActual = true;
	protected Date date;
	
	public ObjectId getObjectId() {
		return this.id;
	}
	
	public void setObjectId(ObjectId objectId) {
		this.id = objectId;
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

	public abstract String getStatus();
	
}
