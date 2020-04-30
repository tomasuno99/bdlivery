package ar.edu.unlp.info.bd2.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("4")
public class Cancelled extends OrderStatus {

	public String getStatus() {
		return "Cancelled";
	}
		
}
