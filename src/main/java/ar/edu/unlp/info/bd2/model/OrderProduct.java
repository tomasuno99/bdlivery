package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
public class OrderProduct {
	private Long quantity;
	private ObjectId product;
	
	public OrderProduct() {}
	
	public OrderProduct(Long quantity, Product product) {
		this.quantity = quantity;
		this.product = product.getObjectId();
	}
	
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public ObjectId getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product.getObjectId();
	}
}
