package ar.edu.unlp.info.bd2.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
