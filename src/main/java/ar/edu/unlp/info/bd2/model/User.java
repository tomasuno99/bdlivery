package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;



import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.mongo.PersistentObject;

public class User implements PersistentObject {
	private ObjectId id;
	private String email;
	private String password;
	private String username;
	private String name;
	private Date dateOfBirth;
	
	@BsonIgnore
	private List<Order> orders;
	private boolean isDelivery;
	
	public User() {}
	
	public User(String email, String pass, String username, String name, Date date) {
		this.id = new ObjectId();
		this.email = email;
		this.password = pass;
		this.name = name;
		this.dateOfBirth = date;
		this.username = username;
	}
	
	public Document createDocument(User user) {
		Document doc = new Document("_id", user.getObjectId());
		doc.append("email", user.getEmail());
		doc.append("password", user.getPassword());
		doc.append("username", user.getUsername());
		doc.append("name", user.getName());
		doc.append("date_of_birth", user.getDateOfBirth());
		return doc;
	}
	
	
	public ObjectId getId() {
		return this.getObjectId();
	}

	public void setId(ObjectId id) {
		this.setObjectId(id);
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

	@Override
	public ObjectId getObjectId() {
		return this.id;
	}

	@Override
	public void setObjectId(ObjectId objectId) {
		this.id = objectId;
	}
	
}
