package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;


import java.util.Date;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

public class Order implements PersistentObject {
	@BsonId
	private ObjectId objectId;
//	private Float amount = 0.0F;
	private Date dateOfOrder;
	private String address;
	private Float coordX;
	private Float coordY;
	private List<OrderProduct> products = new ArrayList<OrderProduct>();
	
	@BsonIgnore
	private User client;
	
	private List<OrderStatus> statusHistory= new ArrayList<OrderStatus>();
	
	@BsonIgnore
	private User deliveryUser;
	
	private Point position;
	
	public Order() {}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		this.objectId = new ObjectId();
		OrderStatus os = new Pending();
		this.statusHistory.add(os);
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		Position pos = new Position(coordX, coordY); 
		this.position = new Point(pos);
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

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	@BsonIgnore
	public Float getAmount() {
		Float total = 00.0f;
		int i=0;
		while(i < this.products.size()) {
			total += this.products.get(i).getAmount(this.dateOfOrder);
			i++;
		}
		return total;	
	}
	
	public String getActualStatus() {
		return this.getActualStatusObject().getStatus();
	}
	
	public User getDeliveryUser() {
		return this.deliveryUser;
	}
	
	public void changeStatus(OrderStatus statusNew) {
		this.getActualStatusObject().setActual(false);
		this.statusHistory.add(statusNew);
	}
	
	public void setDeliveryUser(User deliveryUser) {
		this.deliveryUser = deliveryUser;
	}

	public ObjectId getObjectId() {
		return objectId;
	}


	public void setObjectId(ObjectId id) {
		this.objectId = id;
	}


//	public List<OrderStatus> getStatus() {
//		return this.statusHistory;
//	}
//
//
//	public void setStatus(OrderStatus status) {
//		this.statusHistory.add(status);
//	}


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


	public List<OrderStatus> getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(List<OrderStatus> statusHistory) {
		this.statusHistory = statusHistory;
	}
	@BsonIgnore
	public List<OrderStatus> getStatus(){
		return this.statusHistory;
	}

	
}
