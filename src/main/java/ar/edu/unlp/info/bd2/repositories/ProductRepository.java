package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Product;

@Repository
@Transactional
public interface ProductRepository extends CrudRepository<Product, Long> {

	
	public List<Product> findByNameContaining(String name);
	
	@Query("select p "
		 + "from Product p join p.prices as price "
		 + "where not exists (select price2 "
		 + "				  from Product p2 join p2.prices as price2 "
		 + "                  where price2.id <> price.id and p.id = p2.id)")
	public List<Product> getProductOnePrice();
	
	@Query("select distinct(p) "
		+  "from Order as o join o.products as op "
					+ 	   "join op.product as p "
		+ " where o.dateOfOrder=:day ")
	public List<Product> getSoldProductsOn(@Param("day") Date day);
	
	
	public Product findFirstByOrderByWeightDesc();
}
