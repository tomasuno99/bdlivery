package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;



import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

public class Order implements PersistentObject {
	@BsonId
	private ObjectId id;
	private Date dateOfOrder;
	private String address;
	private Float coordX;
	private Float coordY;
	
	@BsonIgnore
	private User client;
	
	@BsonIgnore
	private List<OrderProduct> products;
	
	private List<OrderStatus> statusHistory;
	
	@BsonIgnore
	private User deliveryUser;
	
	public Order() {}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		this.id = new ObjectId();
		this.statusHistory = new ArrayList<OrderStatus>();
		this.products = new ArrayList<OrderProduct>();
		OrderStatus pendingStatus = new Pending();
		pendingStatus.setDate(dateOfOrder);
		this.statusHistory.add(pendingStatus);
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = client;
	}
	
	public OrderStatus getActualStatusObject() {
		int i=0;
		while(i < this.statusHistory.size()) {
			if (this.statusHistory.get(i).isActual()) {
				return this.statusHistory.get(i);
			}
			i++;
		}
		return null;	
	}

	public String getActualStatus() {
		return this.getActualStatusObject().getStatus();
	}
	
	public Float getAmount() {
		Float total = 00.0f;
		int i=0;
		while(i < this.products.size()) {
			total += this.products.get(i).getAmount(this.dateOfOrder);
			i++;
		}
		return total;	
	}
	
	public User getDeliveryUser() {
		return this.deliveryUser;
	}
	
	
	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public ObjectId getObjectId() {
		return id;
	}


	public void setObjectId(ObjectId id) {
		this.id = id;
	}


	public List<OrderStatus> getStatus() {
		return this.statusHistory;
	}


	public void setStatus(OrderStatus status) {
		this.statusHistory.add(status);
	}


	public Date getDateOfOrder() {
		return dateOfOrder;
	}


	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Float getCoordX() {
		return coordX;
	}


	public void setCoordX(Float coordX) {
		this.coordX = coordX;
	}


	public Float getCoordY() {
		return coordY;
	}


	public void setCoordY(Float coordY) {
		this.coordY = coordY;
	}


	public User getClient() {
		return client;
	}


	public void setClient(User client) {
		this.client = client;
	}


	public List<OrderProduct> getProducts() {
		return products;
	}


	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}

	
}
