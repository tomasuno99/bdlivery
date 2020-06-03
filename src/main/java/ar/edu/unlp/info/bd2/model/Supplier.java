package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;


import java.util.List;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;


import ar.edu.unlp.info.bd2.mongo.PersistentObject;

public class Supplier implements PersistentObject{
	@BsonId
	private ObjectId objectId;
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	
	@BsonIgnore
	private List<Product> products;
	
	public Supplier() {}
	
	public Supplier(String name, String cuil, String adress, Float coordx, Float coordy) {
		this.objectId = new ObjectId();
		this.name= name;
		this.cuil = cuil;
		this.address = adress;
		this.coordX = coordx;
		this.coordY = coordy;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCuil() {
		return cuil;
	}
	public void setCuil(String cuil) {
		this.cuil = cuil;
	}
	public String getAdress() {
		return address;
	}
	public void setAdress(String adress) {
		this.address = adress;
	}
	public Float getCoordx() {
		return coordX;
	}
	public void setCoordx(Float coordx) {
		this.coordX = coordx;
	}
	public Float getCoordy() {
		return coordY;
	}
	public void setCoordy(Float coordy) {
		this.coordY = coordy;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}

	@Override
	public ObjectId getObjectId() {
		return this.objectId;
	}

	@Override
	public void setObjectId(ObjectId objectId) {
		this.objectId = objectId;
		
	}
	
	
}
