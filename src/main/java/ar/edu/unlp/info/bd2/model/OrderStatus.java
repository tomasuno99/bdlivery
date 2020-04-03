package ar.edu.unlp.info.bd2.model;

import javax.persistence.Column;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "order_status")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="order_status_type", discriminatorType=DiscriminatorType.INTEGER)
public abstract class OrderStatus {
	@Id
	@Column(name="order_status_id")
	@GeneratedValue(strategy= GenerationType.AUTO)
	private long id;
	@Column(name="is_actual")
	private boolean isActual = true;
	
	public boolean isActual() {
		return isActual;
	}

	public void setActual(boolean isActual) {
		this.isActual = isActual;
	}

	public abstract String getStatus();
	
}
