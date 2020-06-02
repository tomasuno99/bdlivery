package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;


import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;


public class Product implements PersistentObject{

	@BsonId
	private ObjectId id;
	private String name;
	
	
	private List<Price> prices = new ArrayList<>();
	//private double price = prices.get(prices.size()).getPrecio();
	private Float weight;
	
	private Supplier supplier;
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Product() {
		this.prices = new ArrayList<Price>();
	}
	
	public Product(String name, Float price, Float weight, Supplier supplier) {
		this.id = new ObjectId();
		Price p = new Price(price, Calendar.getInstance().getTime());
		this.prices.add(p);
		this.name = name;
		this.weight = weight;
		this.supplier = supplier;
		this.date = Calendar.getInstance().getTime();
	}
	
	public Product(String name, Float price, Float weight, Supplier supplier, Date date) {
		this.id = new ObjectId();
		Price p = new Price(price, date);
		this.prices.add(p);
		this.name = name;
		this.weight = weight;
		this.supplier = supplier;
		this.date = date;
	}
	
	//public Product updateProductPrice(double idProd,float price, Date startDate) {
	//	
	//}
	
	public Price getActualPrice() {
		Price p = null;
		int i=0;
		while((i < this.prices.size()) && (p == null)) {
			if (this.prices.get(i).getActualPrice() == true) {
				p=this.prices.get(i);
			}
			i++;
		}
		return p;
	}
	
	public Product updatePrice(Float price, Date startDate) {
		this.getActualPrice().finalizePrice(startDate);
		Price priceVar = new Price(price, startDate);
		this.getPrices().add(priceVar);
		return this;
	}
	
	public Float getPrice() {
		return this.getActualPrice().getPrice();
	}
	
	public Float getPriceAt(Date day) {
		int i=0;
		while(i < this.prices.size()) {
			if(this.prices.get(i).getEndDate() == null){
				if (day.compareTo(this.prices.get(i).getStartDate()) >= 0 ){
				return this.prices.get(i).getPrice();
				}
			}else {
				if (day.compareTo(this.prices.get(i).getStartDate()) >= 0 && day.compareTo(this.prices.get(i).getEndDate()) <= 0) {
					return this.prices.get(i).getPrice();
				}
			}
			i++;
		}
		return null;
	}
	
	public ObjectId getId() {
		return this.getObjectId();
	}

	public void setId(ObjectId id) {
		this.setObjectId(id);
	}
	@BsonIgnore
	public List<Price> getPrices() {
		return prices;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public ObjectId getObjectId() {
		return this.id;
	}

	@Override
	public void setObjectId(ObjectId objectId) {
		this.id = objectId;
		
	}


	
}
