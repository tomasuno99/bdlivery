package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Supplier {
	@Id
	private long id;
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	@DBRef
	private List<Product> products;
	
	public Supplier() {}
	
	public Supplier(String name, String cuil, String adress, Float coordx, Float coordy) {
		this.products = new ArrayList<Product>();
		this.name= name;
		this.cuil = cuil;
		this.address = adress;
		this.coordX = coordx;
		this.coordY = coordy;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	
}
