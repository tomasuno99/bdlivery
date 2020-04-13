package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;
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
		 
		 public Product updateProduct(Product product){
			 sessionFactory.getCurrentSession().update(product);
			 return product;
		 }
		 
		 public Order storeOrder(Order order) {
			 sessionFactory.getCurrentSession().save(order);
			 return order;
		 }
		 
		 public Order updateOrder(Order order) {
			 sessionFactory.getCurrentSession().update(order);
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
		 
		 public OrderStatus getActualStatus(Long idOrder) {
			 String txt="select s from Order o join o.statusHistory as s where s.isActual is true and o.id=:idOrder";
	         Session session= sessionFactory.getCurrentSession();
	         OrderStatus os = (OrderStatus) session.createQuery(txt).setParameter("idOrder",idOrder).uniqueResult();
	         return os;
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
			String txt="from Product p where p.name like :nameProd";
			Session session= sessionFactory.getCurrentSession();
            List<Product> p = (List<Product>) session.createQuery(txt).setParameter("nameProd","%"+nameProd+"%").getResultList();
            return p;
		}
		 
		public User getUserByUsername(String aUsername) {
			String txt="from User u where u.username like :aUsername";
			Session session= sessionFactory.getCurrentSession();
            User u = (User) session.createQuery(txt).setParameter("aUsername",aUsername).uniqueResult();
            return u;
		}
		
		public List<Order> getAllOrdersMadeByUser(String aUsername){
			String txt="select o from Order o join o.client as c where c.username like :aUsername";
			Session session= sessionFactory.getCurrentSession();
            List<Order> resultList = session.createQuery(txt).setParameter("aUsername",aUsername).getResultList();
            return resultList;
		}
		public List<User> getUsersSpendingMoreThan(Float amount){
			String txt="select u from User u inner join u.orders o"
					+ "			             inner join o.products op"
					+ "						 inner join op.product p"
					+ "						 inner join p.prices pr "
					+ "	where pr.actualPrice is true "
					+ "	GROUP BY u HAVING sum(pr.price) > :amount";
			Session session= sessionFactory.getCurrentSession();
            List<User> resultList = session.createQuery(txt).setParameter("amount",amount).getResultList();
            return resultList;
		}
		
		
		/**
		 * Obtiene los <code>n</code> proveedores que más productos tienen en ordenes que están siendo enviadas
		 * @param n
		 * @return una lista con los <code>n</code> proveedores que satisfagan la condición
		 */
		public List<Supplier> getTopNSuppliersInSentOrders(int n){
			String txt="select prod.supplier, AVG(op.quantity) from Order o join o.products as op join op.product as prod join o.statusHistory as os where os.class = Sended GROUP BY prod.supplier ORDER BY AVG(op.quantity)";
			Session session= sessionFactory.getCurrentSession();
            List<Supplier> resultList = session.createQuery(txt).setMaxResults(n).getResultList();
            return resultList;
		}
		
		 /**
		  * Obtiene el listado de las ordenes enviadas y no entregadas
		  */
		public List<Order> getSentOrders(){
			String txt="select o from Order o join o.statusHistory as os where os.class = 2 AND NOT EXISTS (select o2 from Order o2 join o2.statusHistory as os2 where o2.id = o.id AND os2.class = 3";
			Session session= sessionFactory.getCurrentSession();
            List<Order> resultList = session.createQuery(txt).getResultList();
            return resultList;
		}
		
}
