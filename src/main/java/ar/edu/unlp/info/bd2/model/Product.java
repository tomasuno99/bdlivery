package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;

public class Product {

	private String name;
	private Float price;
	private Float weight;
	private Supplier supplier;
	
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.supplier = supplier;
		
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getWeight() {
		return weight;
	}

	public void setWeight(Float weight) {
		this.weight = weight;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}


	
}
