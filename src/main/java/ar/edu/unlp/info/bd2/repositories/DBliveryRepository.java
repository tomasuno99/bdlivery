package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

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
		 
		 
		 public Order storeOrder(Order order) {
			 sessionFactory.getCurrentSession().save(order);
			 return order;
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
		
		 public Order getOrderById(Long idOrder) {
			 String txt="from Order o where o.id=:idOrder";
	         Session session= sessionFactory.getCurrentSession();
	         Order o = (Order) session.createQuery(txt).setParameter("idOrder",idOrder).uniqueResult();
	         return o;
		 }
		 
		public User getUserByEmail(String email) {
			String txt="from User u where u.email like :UEmail";
			Session session= sessionFactory.getCurrentSession();
	        User u = (User) session.createQuery(txt).setParameter("UEmail",email).uniqueResult();
	        return u;
		}

		public User getUserById(long idUser) {
			String txt="from User u where u.id=:idUser";
			Session session= sessionFactory.getCurrentSession();
	        User u = (User) session.createQuery(txt).setParameter("idUser",idUser).uniqueResult();
	        return u;
		}
		
		public List<Product>getProductByName(String nameProd) {
			String txt="from Product p where p.name like CONCAT('%', :nameProd, '%')";
			Session session= sessionFactory.getCurrentSession();
            List<Product> p = (List<Product>) session.createQuery(txt).setParameter("nameProd",nameProd).getResultList();
            return p;
		}
		 
		public User getUserByUsername(String aUsername) {
			String txt="from User u where u.username like :aUsername";
			Session session= sessionFactory.getCurrentSession();
            User u = (User) session.createQuery(txt).setParameter("aUsername",aUsername).uniqueResult();
            return u;
		}
}
