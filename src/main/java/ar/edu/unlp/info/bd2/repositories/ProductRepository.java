package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Product;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {

	
	public List<Product> findByNameContaining(String name);
}
