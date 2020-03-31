package ar.edu.unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;

public class DBliveryRepository {

		@Autowired
		private SessionFactory sessionFactory;
		
		
		public Supplier storeSupplier(Supplier supplier){
		    sessionFactory.getCurrentSession().save(supplier);
		    return supplier;
		}
		  /* saves a new user and returns it */
		 public Product storeProduct(Product product){
		   sessionFactory.getCurrentSession().save(product);
		   return product;
		 }
}
