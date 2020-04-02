package ar.edu.unlp.info.bd2.repositories;

import javax.persistence.TypedQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.Order;
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
	            String txt="from Product p where p.id=:idProd";
	            Session session= sessionFactory.getCurrentSession();
	            Product p = (Product) session.createQuery(txt).setParameter("idProd",idProd).uniqueResult();
	            return p;
	     }
		 
		 public Order storeOrder(Order order) {
			 sessionFactory.getCurrentSession().save(order);
			 return order;
		 }
		 
		 public Order getOrderById(Long idOrder) {
			 String txt="from Order o where o.id=:idOrder";
	         Session session= sessionFactory.getCurrentSession();
	         Order o = (Order) session.createQuery(txt).setParameter("idOrder",idOrder).uniqueResult();
	         return o;
		 }
}
