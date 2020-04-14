package ar.edu.unlp.info.bd2.services;

import java.util.Date;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import ar.edu.unlp.info.bd2.model.Cancelled;
import ar.edu.unlp.info.bd2.model.Delivered;
import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderProduct;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Price;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Sended;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryRepository;

public class DBliveryServiceImpl implements DBliveryService {
	
	private DBliveryRepository repository;
	
	public DBliveryServiceImpl(DBliveryRepository repository) {
			this.repository = repository;
	}

	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name,price,weight,supplier);
		return this.repository.storeProduct(p) ;
	}
	
	@Transactional
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product p = new Product(name,price,weight,supplier, date);
		return this.repository.storeProduct(p) ;
	}
	
	@Transactional
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier(name,cuil,address,coordX,coordY);
		return repository.storeSupplier(s);
	}
	
//	@Transactional
//	public Product getProductById(long idProd) {
//		return repository.getProductById(idProd);
//	}

	@Transactional
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
		User u=new User(email, password, username, name, dateOfBirth);
		return repository.storeUser(u);
	}

	@Transactional
	public Product updateProductPrice(Long id, Float price, Date startDate) throws DBliveryException {
		Product p = repository.getProductById(id);
		if (p==null) {
			throw new DBliveryException("the product with that id does not exist");
		}
		p.updatePrice(price, startDate);
		return repository.updateProduct(p);
	}
	
	@Transactional
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = new Order(dateOfOrder, address, coordX, coordY, client);
		return this.repository.storeOrder(o);
	}
	
	@Transactional
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		OrderProduct op = new OrderProduct(quantity, product);
		o.getProducts().add(op);
		return this.repository.updateOrder(o);
	}
	
	@Transactional
	public boolean canCancel(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		return this.getActualStatus(order).getStatus().equals("Pending");
	}
	
	@Transactional
	public boolean canFinish(Long id) throws DBliveryException {
		Order o = this.repository.getOrderById(id);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		return this.getActualStatus(id).getStatus().equals("Sended");
	}
	
	@Transactional
	public OrderStatus getActualStatus(Long order) {
		return repository.getActualStatus(order);
	}
	
	@Override
	public Optional<User> getUserById(Long id) {
		return Optional.of(this.repository.getUserById(id));
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return Optional.of(this.repository.getUserByEmail(email));
	}
	
	@Override
	public Optional<User> getUserByUsername(String username) {
		return Optional.of(this.repository.getUserByUsername(username));
	}

	@Override
	public Optional<Product> getProductById(Long id) {
		return Optional.of(this.repository.getProductById(id));
	}

	@Override
	public Optional<Order> getOrderById(Long id) {
		return Optional.of(this.repository.getOrderById(id));
	}
	
	@Transactional
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		if (! this.canDeliver(order)) throw new DBliveryException("order error");
		//ineficiente)?
		Order o = this.repository.getOrderById(order);
		OrderStatus sended = new Sended();
		this.getActualStatus(order).setActual(false);
		o.setStatus(sended);
		o.setDeliveryUser(deliveryUser);
		return this.repository.updateOrder(o);
	}
	
	@Transactional
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		if (! this.canDeliver(order)) throw new DBliveryException("order error");
		Order o = this.repository.getOrderById(order);
		OrderStatus sended = new Sended();
		sended.setDate(date);
		this.getActualStatus(order).setActual(false);
		o.setStatus(sended);
		o.setDeliveryUser(deliveryUser);
		return this.repository.updateOrder(o);
	}

	@Transactional
	public Order cancelOrder(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		if (! this.getActualStatus(order).getStatus().equals("Pending")) throw new DBliveryException("The order is not in pending");
		this.getActualStatus(order).setActual(false);
		OrderStatus cancelled = new Cancelled();
		o.getStatus().add(cancelled);
		return repository.updateOrder(o);
	}
	
	@Transactional
	public Order cancelOrder(Long order, Date date) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		if (! this.getActualStatus(order).getStatus().equals("Pending")) throw new DBliveryException("The order is not in pending");
		this.getActualStatus(order).setActual(false);
		OrderStatus cancelled = new Cancelled();
		cancelled.setDate(date);
		o.getStatus().add(cancelled);
		return repository.updateOrder(o);
	}

	@Transactional
	public Order finishOrder(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		if (! this.getActualStatus(order).getStatus().equals("Sended")) throw new DBliveryException("The order is not sended");
		this.getActualStatus(order).setActual(false);
		OrderStatus delivered = new Delivered();
		o.getStatus().add(delivered);
		return repository.updateOrder(o);
	}
	
	@Transactional
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		if (! this.getActualStatus(order).getStatus().equals("Sended")) throw new DBliveryException("The order is not sended");
		this.getActualStatus(order).setActual(false);
		OrderStatus delivered = new Delivered();
		delivered.setDate(date);
		o.getStatus().add(delivered);
		return repository.updateOrder(o);
	}


	@Transactional
	public boolean canDeliver(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o != null) {
			if (this.getActualStatus(order).getStatus().equals("Pending")){
				if (o.getProducts().size() > 0) {
					return true;
				}
			}
			else {
				throw new DBliveryException("the order is not Pending");
			}
		}
		return false;
	}



	@Transactional
	public List<Product> getProductByName(String name) {
		return this.repository.getProductByName(name);
	}
	
	@Transactional
	public List<Order> getAllOrdersMadeByUser(String username){
		return this.repository.getAllOrdersMadeByUser(username);
	}

	@Transactional
	public List<User> getUsersSpendingMoreThan(Float amount) {
		return this.repository.getUsersSpendingMoreThan(amount);
	}


	@Transactional
	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		return this.repository.getTopNSuppliersInSentOrders(n);
	}

	@Transactional
	public List<Product> getTop10MoreExpensiveProducts() {
		return this.repository.getTop10MoreExpensiveProducts();
	}

	@Transactional
	public List<User> getTop6UsersMoreOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Order> getCancelledOrdersInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Order> getPendingOrders() {
		return this.repository.getPendingOrders();
	}

	@Transactional
	public List<Order> getSentOrders() {
		return this.repository.getSentOrders();
	}

	@Transactional
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		return this.repository.getDeliveredOrdersInPeriod(startDate, endDate);
	}

	@Transactional
	public List<Order> getDeliveredOrdersForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Order> getSentMoreOneHour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Order> getDeliveredOrdersSameDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<User> get5LessDeliveryUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Product getBestSellingProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Product> getProductsOnePrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Product> getProductIncreaseMoreThan100() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public Supplier getSupplierLessExpensiveProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Supplier> getSuppliersDoNotSellOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Product> getSoldProductsOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Order> getOrdersCompleteMorethanOneDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Object[]> getProductsWithPriceAt(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Product> getProductsNotSold() {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public List<Order> getOrderWithMoreQuantityOfProducts(Date day) {
		// TODO Auto-generated method stub
		return null;
	}
}
