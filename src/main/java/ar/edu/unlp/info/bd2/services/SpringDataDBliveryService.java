package ar.edu.unlp.info.bd2.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.model.Cancelled;
import ar.edu.unlp.info.bd2.model.Delivered;
import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderProduct;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Sended;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.OrderRepository;
import ar.edu.unlp.info.bd2.repositories.ProductRepository;
import ar.edu.unlp.info.bd2.repositories.SupplierRepository;
import ar.edu.unlp.info.bd2.repositories.UserRepository;

@Service
@Transactional
public class SpringDataDBliveryService implements DBliveryService, DBliveryStatisticsService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	SupplierRepository supplierRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserRepository userRepository;
	
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
		Optional<Product> p = this.productRepository.findById(id);
		if (! p.isPresent()) {
			throw new DBliveryException("the product with that id does not exist");
		}
		Product product = p.get();
		product.updatePrice(price, startDate);
		return this.productRepository.save(product);
	}

	@Override
	public Optional<User> getUserById(Long id) {
		return this.userRepository.findById(id);
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return Optional.of(this.userRepository.findByEmail(email));
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return Optional.of(this.userRepository.findByUsername(username));
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		return this.orderRepository.findById(id);
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
		if (! this.canDeliver(order)) throw new DBliveryException("order error");
		Order o = this.orderRepository.findById(order).get();
		OrderStatus sended = new Sended();
		this.getActualStatus(order).setActual(false);
		o.setStatus(sended);
		o.setDeliveryUser(deliveryUser);
		return this.orderRepository.save(o);
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		if (! this.canDeliver(order)) throw new DBliveryException("order error");
		Order o = this.orderRepository.findById(order).get();
		OrderStatus sended = new Sended();
		sended.setDate(date);
		this.getActualStatus(order).setActual(false);
		o.setStatus(sended);
		o.setDeliveryUser(deliveryUser);
		return this.orderRepository.save(o);
	}

	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		if (! this.canCancel(order)) throw new DBliveryException("order error");
		Order o = this.orderRepository.findById(order).get();
		OrderStatus cancel = new Cancelled();
		this.getActualStatus(order).setActual(false);
		o.setStatus(cancel);
		return this.orderRepository.save(o);
	}

	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException {
		if (! this.canCancel(order)) throw new DBliveryException("order error");
		Order o = this.orderRepository.findById(order).get();
		OrderStatus cancel = new Cancelled();
		cancel.setDate(date);
		this.getActualStatus(order).setActual(false);
		o.setStatus(cancel);
		return this.orderRepository.save(o);
	}

	@Override
	public Order finishOrder(Long order) throws DBliveryException {
		if (! this.canFinish(order)) throw new DBliveryException("order error");
		Order o = this.orderRepository.findById(order).get();
		this.getActualStatus(order).setActual(false);
		OrderStatus delivered = new Delivered();
		o.getStatus().add(delivered);
		return orderRepository.save(o);
	}

	@Override
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		if (! this.canFinish(order)) throw new DBliveryException("order error");
		Order o = this.orderRepository.findById(order).get();
		this.getActualStatus(order).setActual(false);
		OrderStatus delivered = new Delivered();
		delivered.setDate(date);
		o.getStatus().add(delivered);
		return orderRepository.save(o);
	}

	@Override
	public boolean canCancel(Long order) throws DBliveryException {
		Optional<Order> o = this.orderRepository.findById(order);
		if (! o.isPresent()) throw new DBliveryException("the order with that id does not exist");
		return this.getActualStatus(order).getStatus().equals("Pending");
	}

	@Override
	public boolean canFinish(Long id) throws DBliveryException {
		Optional<Order> o_optional = this.orderRepository.findById(id);
		if (!o_optional.isPresent()) throw new DBliveryException("the order with that id does not exist");
		return this.getActualStatus(id).getStatus().equals("Sended");
	}
	
	@Override
	public boolean canDeliver(Long order) throws DBliveryException {
		Order o = this.orderRepository.findById(order).get();
		if (this.getActualStatus(order).getStatus().equals("Pending")) {
			if (o.getProducts().size() > 0 ) {
				return true;
			}
		} else {
			throw new DBliveryException("the order is not in pending status");
		}
		return false;
	}

	@Override
	public OrderStatus getActualStatus(Long order) {
		return this.orderRepository.getActualStatus(order);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return this.productRepository.findByNameContaining(name);
	}

	public Optional<Product> getProductById(Long id) {
		return this.productRepository.findById(id);
	}
	@Override
	public Product getMaxWeigth() {
		return this.productRepository.findFirstByOrderByWeightDesc();
	}
	@Override
	public List<Order> getAllOrdersMadeByUser(String username) {
		return this.orderRepository.getAllOrdersMadeByUser(username);
	}
	@Override
	public List<Order> getPendingOrders() {
		return this.orderRepository.getPendingOrders();
	}
	@Override
	public List<Order> getSentOrders() {
		return this.orderRepository.getSentOrders();
	}
	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		return this.orderRepository.getDeliveredOrdersInPeriod(startDate, endDate);
	}
	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		return this.orderRepository.getDeliveredOrdersForUser(username);
	}
	@Override
	public List<Product> getProductsOnePrice() {
		return this.productRepository.getProductOnePrice();
	}
	@Override
	public List<Product> getSoldProductsOn(Date day) {
		return this.productRepository.getSoldProductsOn(day);
	}

}
