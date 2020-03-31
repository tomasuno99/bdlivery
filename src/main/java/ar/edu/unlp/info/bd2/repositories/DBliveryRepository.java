package ar.edu.unlp.info.bd2.repositories;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

<<<<<<< HEAD
import ar.edu.unlp.info.bd2.model.Supplier;
=======
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;
>>>>>>> b9ffd087aa746ac6180d4b61cac6903ba2833ee1

public class DBliveryRepository {

		@Autowired
		private SessionFactory sessionFactory;
		
<<<<<<< HEAD
		
		public Supplier storeSupplier(Supplier supplier){
		    sessionFactory.getCurrentSession().save(supplier);
		    return supplier;
		}
=======
		  /* saves a new user and returns it */
		 public Product storeProduct(Product product){
		   sessionFactory.getCurrentSession().save(product);
		   return product;
		 }
>>>>>>> b9ffd087aa746ac6180d4b61cac6903ba2833ee1
}
