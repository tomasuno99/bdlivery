package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="supplier")
public class Supplier {
	@Id
	private long id;
	@Column(name="name")
	private String name;
	@Column(name="cuil")
	private String cuil;
	@Column(name="address")
	private String address;
	@Column(name="coord_x")
	private Float coordX;
	@Column(name="coord_y")
	private Float coordY;
	@OneToMany(mappedBy="supplier")
	private ArrayList<Product> products = new ArrayList();
	
	public Supplier() {}
	
	public Supplier(String name, String cuil, String adress, Float coordx, Float coordy) {
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
