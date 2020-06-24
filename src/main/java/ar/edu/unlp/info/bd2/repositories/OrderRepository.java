package ar.edu.unlp.info.bd2.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	@Query("select s from Order o join o.statusHistory as s where s.isActual is true and o.id=?1")
	public OrderStatus getActualStatus(Long orderId);
	

	@Query("select o from Order o where o.client.username = ?1")
	public List<Order> getAllOrdersMadeByUser(String username);

	@Query("select o from Order o join o.statusHistory as os where os.class=2 and os.isActual is true")
	public List<Order> getSentOrders();
	
	@Query("select o from Order as o join o.statusHistory as os where os.class=1 and os.isActual is true")
	public List<Order> getPendingOrders();

	@Query("select o "
					+ "from Order o join o.client as u "
					+ "				join o.statusHistory as os "
					+ "where u.username=?1 and "
					+ "os.class=3")
	public List<Order> getDeliveredOrdersForUser(String username);
}
