package ar.edu.unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;

public class DBliveryRepository {

		@Autowired
		private SessionFactory sessionFactory;
		
		  /* saves a new user and returns it */
		 public Product storeProduct(Product product){
		   sessionFactory.getCurrentSession().save(product);
		   return product;
		 }
}
