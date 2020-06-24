package ar.edu.unlp.info.bd2.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Supplier;

@Repository
@Transactional
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

}
