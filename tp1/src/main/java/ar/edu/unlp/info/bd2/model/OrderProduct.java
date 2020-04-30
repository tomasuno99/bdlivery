package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order_product")
public class OrderProduct {
	@Id
	@Column(name="order_product_id", nullable=false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	@Column(name="quantity", nullable=false)
	private Long quantity;
	@JoinColumn(name="product_id", nullable=false)
	@OneToOne(fetch=FetchType.LAZY) 
	private Product product;
	
	public OrderProduct() {}
	
	public OrderProduct(Long quantity, Product product) {
		this.quantity = quantity;
		this.product = product;
	}
	
	public Float getAmount(Date day) {
		return this.product.getPriceAt(day) * this.quantity;
	}
	
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
