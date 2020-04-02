package ar.edu.unlp.info.bd2.repositories;

import javax.persistence.TypedQuery;
import org.hibernate.query.Query;

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
		 public Product storeProduct(Product product){
			 sessionFactory.getCurrentSession().save(product);
			 return product;
		 }
		 
		 public User storeUser(User user){
			    sessionFactory.getCurrentSession().save(user);
			    return user;
		 }
		
		public Product getProductById(long idProd) {
			String txt = "SELECT p FROM product p WHERE p.product_id = :idProd";
			TypedQuery<Product> query = sessionFactory.getCurrentSession().createQuery(txt, Product.class);
			query.setParameter("idProd", idProd);
			return ((Query<Product>) query).uniqueResult();
		}
}
