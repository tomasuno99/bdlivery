package ar.edu.unlp.info.bd2.model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Sended extends OrderStatus {

	public String getStatus() {
		return "Sended";
	}
	
}
