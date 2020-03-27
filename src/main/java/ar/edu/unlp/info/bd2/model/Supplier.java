package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;

public class Supplier {
	private long id;
	private String name;
	private String cuil;
	private String address;
	private Float coordX;
	private Float coordY;
	private ArrayList<Product> products = new ArrayList();
	
	public Supplier createSupplier(	String name, String cuil, String adress, Float coordx, Float coordy) {
		this.name= name;
		this.cuil = cuil;
		this.address = adress;
		this.coordX = coordx;
		this.coordY = coordy;
		
		return this;
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

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	
}
