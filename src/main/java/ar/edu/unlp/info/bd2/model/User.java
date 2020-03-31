package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
	@Id
	@Column(name="user_id")
	private long id;
	@Column(name="email")
	private String email;
	@Column(name="password")
	private String password;
	@Column(name="username")
	private String username;
	@Column(name="name")
	private String name;
	@Column(name="date_of_birth")
	private Date dateOfBirth;
	@OneToMany(mappedBy="client")
	private ArrayList<Order> orders = new ArrayList();
	
	public User(String email, String pass, String username, String name, Date date) {
		//this.id = id;
		this.email = email;
		this.password = pass;
		this.name = name;
		this.dateOfBirth = date;
		this.username = username;
	}
	
	public ArrayList<Order> getOrders() {
		return orders;
	}
	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
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
