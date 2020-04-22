package ar.edu.unlp.info.bd2.model;

import java.util.Date;

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
	@Column(name="order_status_id", nullable=false)
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	protected long id;
	@Column(name="is_actual", nullable=false)
	protected boolean isActual = true;
	@Column(name="date")
	protected Date date;
	
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActual() {
		return isActual;
	}

	public void setActual(boolean isActual) {
		this.isActual = isActual;
	}

	public abstract String getStatus();
	
}
