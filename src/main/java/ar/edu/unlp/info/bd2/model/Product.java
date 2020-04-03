package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;

import ar.edu.unlp.info.bd2.repositories.DBliveryException;

@Entity
@Table(name="product")
public class Product {

	@Id
	@Column(name="product_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column(name="name")
	private String name;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="product_id")
	private List<Price> prices;
	//private double price = prices.get(prices.size()).getPrecio();
	@Column(name="weight")
	private Float weight;
	@ManyToOne
	@JoinColumn(name="id_supplier")
	private Supplier supplier;
	
	public Product() {}
	
	public Product(String name, Float price, Float weight, Supplier supplier) {
		this.prices = new ArrayList<Price>();
		Price p = new Price(price, Calendar.getInstance().getTime());
		this.prices.add(p);
		this.name = name;
		this.weight = weight;
		this.supplier = supplier;
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
	
	public Float getPrice() {
		return this.getActualPrice().getPrice();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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


	
}
