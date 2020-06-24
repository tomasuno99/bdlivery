package ar.edu.unlp.info.bd2.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderProduct;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.OrderRepository;
import ar.edu.unlp.info.bd2.repositories.ProductRepository;
import ar.edu.unlp.info.bd2.repositories.SupplierRepository;
import ar.edu.unlp.info.bd2.repositories.UserRepository;

@Service
@Transactional
public class SpringDataDBliveryService implements DBliveryService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
<<<<<<< HEAD
	OrderRepository orderRepository;
=======
	UserRepository userRepository;
>>>>>>> 0fb576f556c2f9a608787af23b8ecc8dd553465d
	
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		return productRepository.save(new Product(name,price,weight,supplier));
	}
	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		return productRepository.save(new Product(name,price,weight,supplier, date));
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		return supplierRepository.save(new Supplier(name,cuil,address, coordX, coordY));
	}

	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		return userRepository.save(new User(email,password,username,name,dateOfBirth));
	}

	@Override
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		return this.orderRepository.save(new Order(dateOfOrder, address, coordX, coordY, client));
	}

	@Override
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Optional<Order> o_optional = this.orderRepository.findById(order);
		if (!o_optional.isPresent()) throw new DBliveryException("the order with that id does not exist");
		Order o = o_optional.get();
		OrderProduct op = new OrderProduct(quantity, product);
		o.getProducts().add(op);
		this.orderRepository.save(o);
		return o;
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCancel(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFinish(Long id) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDeliver(Long order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderStatus getActualStatus(Long order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Product> getProductById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
