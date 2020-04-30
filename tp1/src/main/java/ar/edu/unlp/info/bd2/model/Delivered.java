package ar.edu.unlp.info.bd2.model;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("3")
public class Delivered extends OrderStatus {

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return "Delivered";
	}

}
