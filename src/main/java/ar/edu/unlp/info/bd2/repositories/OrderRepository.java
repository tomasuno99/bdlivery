package ar.edu.unlp.info.bd2.repositories;


import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;

@Repository
@Transactional
public interface OrderRepository extends CrudRepository<Order, Long> {

	@Query("select s from Order o join o.statusHistory as s where s.isActual is true and o.id=:orderId")
	public OrderStatus getActualStatus(@Param("orderId") Long orderId);
	

	@Query("select o from Order o where o.client.username = :username")
	public List<Order> getAllOrdersMadeByUser(@Param("username") String username);

	@Query("select o from Order o join o.statusHistory as os where os.class=2 and os.isActual is true")
	public List<Order> getSentOrders();
	
	@Query("select o from Order as o join o.statusHistory as os where os.class=1 and os.isActual is true")
	public List<Order> getPendingOrders();

	@Query("select o "
					+ "from Order o join o.client as u "
					+ "				join o.statusHistory as os "
					+ "where u.username=:username and "
					+ "os.class=3")
	public List<Order> getDeliveredOrdersForUser(@Param("username") String username);
	
	@Query("select o "
		+ "from Order o join o.statusHistory as os "
		+ "where os.class = 3 AND os.date >= :startDate AND os.date <= :endDate")
	public List<Order> getDeliveredOrdersInPeriod(@Param("startDate") Date startDate,@Param("endDate") Date finishDate);
}
