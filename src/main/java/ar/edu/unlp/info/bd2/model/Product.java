package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Date;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;

public class Product {

	private long id;
	private String name;
	private ArrayList<Price> prices = new ArrayList();
	//private double price = prices.get(prices.size()).getPrecio();
	private Float weight;
	private Supplier supplier;
	
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {

		this.setPrice(price);
		this.name = name;
		this.weight = weight;
		this.supplier = supplier;
		
		return this;
	}
	
	//public Product updateProductPrice(double idProd,float price, Date startDate) {
	//	
	//}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ArrayList<Price> getPrices() {
		return prices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return prices.get(prices.size()).getPrice();
	}

	public void setPrice(Float price) {
		Price p = new Price(price);
		this.prices.get(prices.size()).finalizePrice();
		this.prices.add(p);
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
