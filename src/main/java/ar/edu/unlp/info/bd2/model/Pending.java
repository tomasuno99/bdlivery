package ar.edu.unlp.info.bd2.model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class Pending extends OrderStatus {
	
	public String getStatus() {
		return "Pending";
	}
	
}
