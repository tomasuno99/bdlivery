package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;


import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

<<<<<<< HEAD
=======

>>>>>>> a2ea524303128cf3f664730b17a3113c5e23c9c3
@Document
public class User {
	@Id
	private long id;
	private String email;
	private String password;
	private String username;
	private String name;
	private Date dateOfBirth;
<<<<<<< HEAD
	@DBRef
=======
>>>>>>> a2ea524303128cf3f664730b17a3113c5e23c9c3
	private List<Order> orders;
	private boolean isDelivery;
	
	public User() {}
	
	public User(String email, String pass, String username, String name, Date date) {
		//this.id = id;
		this.email = email;
		this.password = pass;
		this.name = name;
		this.dateOfBirth = date;
		this.username = username;
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public boolean isDelivery() {
		return isDelivery;
	}

	public void setDelivery(boolean isDelivery) {
		this.isDelivery = isDelivery;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
}
