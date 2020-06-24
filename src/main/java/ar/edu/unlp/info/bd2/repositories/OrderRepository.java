package ar.edu.unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
