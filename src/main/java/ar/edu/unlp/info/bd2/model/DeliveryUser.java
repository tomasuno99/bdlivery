package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="delivery_user")
public class DeliveryUser extends User {
	
	@OneToMany(mappedBy="delivery_user")
	private ArrayList<Order> ordersDelivered = new ArrayList();

	public DeliveryUser(String email, String pass, String username, String name, Date date) {
		super(email, pass, username, name, date);
		// TODO Auto-generated constructor stub
	}


}
