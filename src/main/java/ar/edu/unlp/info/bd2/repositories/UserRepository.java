package ar.edu.unlp.info.bd2.repositories;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.User;


@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	
	
	public User findByUsername(String username);
	
	public User findByEmail(String email);

}
