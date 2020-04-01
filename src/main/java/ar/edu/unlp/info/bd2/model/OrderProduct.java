package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_product")
public class OrderProduct {
	@Id
	@Column(name="order_product_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column(name="quantity")
	private int quantity;
	@OneToOne
	private Product product;
	
	public OrderProduct() {}
	
	public OrderProduct(int quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
