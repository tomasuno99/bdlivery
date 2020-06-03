package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
public class OrderProduct {
	@BsonId
	private ObjectId objectId;
	private Long quantity;
	private Product product;
	
	public OrderProduct() {}
	
	public OrderProduct(Long quantity, Product product) {
		this.objectId = new ObjectId();
		this.quantity = quantity;
		this.product = product;
	}
	public ObjectId getObjectId() {
		return objectId;
	}

	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
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
