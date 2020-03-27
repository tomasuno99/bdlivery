package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	private long id;
	private Date dateOfOrder;
	private String address;
	private Float coordX; 
	private Float coordY;
	private User client;
	private ArrayList<OrderProduct> products = new ArrayList();
	private ArrayList<OrderStatus> statusHistory = new ArrayList();
	private DeliveryUser deliveryUser;
	// es mejor modelar el deliveryUser aparte del User? deliveryUser es un User
	
	public User getDeliveryUser() {
		return this.deliveryUser;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public ArrayList<OrderStatus> getStatus() {
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


	public ArrayList<OrderProduct> getProducts() {
		return products;
	}


	public void setProducts(ArrayList<OrderProduct> products) {
		this.products = products;
	}
	


	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		OrderStatus pendingStatus = new Pending();
		this.setStatus(pendingStatus);
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = client;
		return this;
	}
}
