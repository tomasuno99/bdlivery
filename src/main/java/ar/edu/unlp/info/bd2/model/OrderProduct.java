package ar.edu.unlp.info.bd2.model;

import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
public class OrderProduct {
	@Id
	private long id;
	private Long quantity;
	@DBRef 
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
