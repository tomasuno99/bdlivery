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
}
