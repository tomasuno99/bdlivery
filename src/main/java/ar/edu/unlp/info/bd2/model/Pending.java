package ar.edu.unlp.info.bd2.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Pending extends OrderStatus {
	
	public String getStatus() {
		return "Pending";
	}
	
}
