package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
public class OrderProduct {

	private Long quantity;
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
