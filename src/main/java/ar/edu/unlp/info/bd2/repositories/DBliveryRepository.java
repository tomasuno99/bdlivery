package ar.edu.unlp.info.bd2.repositories;

import java.util.Calendar;
import java.util.Date;
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
			String txt="select u from User u join u.orders as o"
					+ "			             join o.products as op"
					+ "						 join op.price as p"
					+ "	group by u having sum(p.price * op.quantity) > :amount";
			Session session= sessionFactory.getCurrentSession();
            List<User> resultList = session.createQuery(txt).setParameter("amount",amount.doubleValue()).getResultList();
            return resultList;
		}
		
		
		/**
		 * Obtiene los <code>n</code> proveedores que más productos tienen en ordenes que están siendo enviadas
		 * @param n
		 * @return una lista con los <code>n</code> proveedores que satisfagan la condición
		 */
		public List<Supplier> getTopNSuppliersInSentOrders(int n){
			String txt="select s "
					+ "from Order o join o.products as op "
					+ "join op.product as prod "
					+ "join o.statusHistory as os "
					+ "join prod.supplier as s "
					+ "where os.class = 2 "
					+ "group by s.id "
					+ "order by count(*) desc";
			Session session= sessionFactory.getCurrentSession();
            List<Supplier> resultList = session.createQuery(txt).setMaxResults(n).getResultList();
            return resultList;
		}
		
		 /**
		  * Obtiene el listado de las ordenes enviadas y no entregadas
		  */
		public List<Order> getSentOrders(){
			String txt="select o from Order o join o.statusHistory as os where os.class = 2 and not exists(select o2 from Order o2 join o2.statusHistory as os2 where o2.id = o.id AND os2.class = 3)";
			Session session= sessionFactory.getCurrentSession();
            List<Order> resultList = session.createQuery(txt).getResultList();
            return resultList;
		}

		@Transactional
		public List<Order> getPendingOrders() {
			String txt = "select o from Order as o join o.statusHistory as os"
			  +" where os.class=1 and os.isActual is true";
			Session session= sessionFactory.getCurrentSession();
            List<Order> resultList = session.createQuery(txt).getResultList();
            return resultList;
		}
		
		/**
		  * Obtiene todas las ordenes entregadas entre dos fechas
		  * @param startDate
		  * @param endDate
		  * @return una lista con las ordenes que satisfagan la condición
		 */
		public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate){
			String txt="select o from Order o join o.statusHistory as os where os.class = 3 AND os.date >= :startDate AND os.date <= :endDate";
			Session session= sessionFactory.getCurrentSession();
            Query query = session.createQuery(txt);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            List<Order> resultList = query.getResultList();
            return resultList;
		}
		
		public List<Product> getTop10MoreExpensiveProducts() {
			String txt="select p "
					+ "from Product p join p.prices as price "
					+ "where price.actualPrice is true "
					+ "order by price.price desc";
			Session session= sessionFactory.getCurrentSession();
            List<Product> resultList = session.createQuery(txt).setMaxResults(10).getResultList();
            return resultList;
		}

		public List<User> getTop6UsersMoreOrders() {
			String txt="select u "
					+ "from User u join u.orders as o "
					+ "group by u "
					+ "order by count(*) desc";
			Session session= sessionFactory.getCurrentSession();
            List<User> resultList = session.createQuery(txt).setMaxResults(6).getResultList();
            return resultList;
		}
		
		public List<Order> getCancelledOrdersInPeriod(Date startDate, Date endDate){
			String txt="select o from Order o join o.statusHistory as os where os.class = 4 AND os.date >= :startDate AND os.date <= :endDate";
			Session session= sessionFactory.getCurrentSession();
            Query query = session.createQuery(txt);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            List<Order> resultList = query.getResultList();
            return resultList;
		}
		//PREGUNTAR EL TEMA DE LOS DIAS
		public List<Order> getDeliveredOrdersForUser(String username) {
			String txt="select o "
					+ "from Order o join o.client as u "
					+ "				join o.statusHistory as os "
					+ "where u.username=:username and "
					+ "os.class=3";
			Session session= sessionFactory.getCurrentSession();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -10);
			Date date = cal.getTime();
            List<Order> resultList = session.createQuery(txt).setParameter("username", username).getResultList();
            return resultList;
		}
		// Preguntar por las horas
		public List<Order> getSentMoreOneHour() {
			String txt="select o "
					+ "from Order o join o.statusHistory as os "
					+ "where "
					+ "os.class=2 and "
					+ "os.date > o.dateOfOrder";
			Session session= sessionFactory.getCurrentSession();
            List<Order> resultList = session.createQuery(txt).getResultList();
            return resultList;
		}
		
		public List<User> get5LessDeliveryUsers(){
			String txt="select o.deliveryUser "
					+ "from Order o join o.statusHistory as os "
					// deberia chequear que os.class = 3 ??? 
					+ "group by o.deliveryUser.id "
					+ "order by count(*) asc";
			Session session= sessionFactory.getCurrentSession();
            List<User> resultList = session.createQuery(txt).setMaxResults(5).getResultList();
            return resultList;
		}
		
		public Product getBestSellingProduct() {
			String txt="select p "
					+ "from Order o join o.products as op "
					+ 				"join op.product as p " 
					+ "group by op.product.id "
					+ "order by sum(op.price) desc";
			Session session= sessionFactory.getCurrentSession();
	        Product p = (Product) session.createQuery(txt).setMaxResults(1).uniqueResult();
	        return p;
		}
		
		
}
