package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="order")
public class Order {
	@Id
	@Column(name="order_id")
	private long id;
	@Column(name="date_of_order")
	private Date dateOfOrder;
	@Column(name="address")
	private String address;
	@Column(name="coord_x")
	private Float coordX;
	@Column(name="coord_y")
	private Float coordY;
	@ManyToOne
	@JoinColumn(name="id_user")
	private User client;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="order_id")
	private List<OrderProduct> products;
	//RESOLVER ESTA COLECCION
	//@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	//@JoinColumn(name="order_id")
	//private List<OrderStatus> statusHistory;
	@ManyToOne
	@JoinColumn(name="id_deliveryUser")
	private DeliveryUser deliveryUser;
	// es mejor modelar el deliveryUser aparte del User? deliveryUser es un User
	
	public Order() {}
	
	public Order(Date dateOfOrder, String address, Float coordX, Float coordY,User client) {
		OrderStatus pendingStatus = new Pending();
		//this.setStatus(pendingStatus);
		this.dateOfOrder = dateOfOrder;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.client = client;
	}
	
	public User getDeliveryUser() {
		return this.deliveryUser;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	//public List<OrderStatus> getStatus() {
	//	return this.statusHistory;
	//}


	//public void setStatus(OrderStatus status) {
	//	this.statusHistory.add(status);
	//}


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
