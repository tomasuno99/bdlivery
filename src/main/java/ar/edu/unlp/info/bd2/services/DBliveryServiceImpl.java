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
		p.getActualPrice().finalizePrice();
		Price priceVar = new Price(price, startDate);
		p.getPrices().add(priceVar);
		return repository.storeProduct(p);
	}
	
	@Transactional
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = new Order(dateOfOrder, address, coordX, coordY, client);
		return this.repository.storeOrder(o);
	}
	
	/**
	 * agrega un producto al pedido
	 * @param id del pedido al cual se le agrega el producto
	 * @param quantity cantidad de producto a agregar
	 * @param product producto a agregar
	 * @return el pedido con el nuevo producto
	 * @throws DBliveryException en caso de no existir el pedido
	 */
	@Transactional
	public Order addProduct(Long order, Long quantity, Product product) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		OrderProduct op = new OrderProduct(quantity, product);
		o.getProducts().add(op);
		return this.repository.storeOrder(o);
	}
	
	@Transactional
	public boolean canCancel(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		return o.getActualStatus().equals("Pending");
	}
	
	@Transactional
	public boolean canFinish(Long id) throws DBliveryException {
		Order o = this.repository.getOrderById(id);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		return o.getActualStatus().equals("Sended");
	}
	
	@Transactional
	public OrderStatus getActualStatus(Long order) {
		Order o = this.repository.getOrderById(order);
		return o.getActualStatusObject();
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
	
	
	/**
	 * Registra el envío del pedido, registrando al repartidor y cambiando su estado a Send.
	 * @param order pedido a ser enviado
	 * @param deliveryUser Usuario que entrega el pedido
	 * @return el pedido modificado
	 * @throws DBliveryException en caso de no existir el pedido, que el pedido no se encuentre en estado Pending o sí no contiene productos.
	 */
	@Transactional
	public Order deliverOrder(Long order, User deliveryUser) throws DBliveryException {
		if (! this.canDeliver(order)) throw new DBliveryException("order error");
		//ineficiente)?
		Order o = this.repository.getOrderById(order);
		OrderStatus sended = new Sended();
		this.getActualStatus(order).setActual(false);
		o.setStatus(sended);
		o.setDeliveryUser(deliveryUser);
		return this.repository.storeOrder(o);
	}

	@Override
	public Order cancelOrder(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		if (! o.getActualStatus().equals("Pending")) throw new DBliveryException("The order is not in pending");
		o.getActualStatusObject().setActual(false);
		OrderStatus cancelled = new Cancelled();
		o.getStatus().add(cancelled);
		return repository.storeOrder(o);
	}

	@Override
	public Order finishOrder(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o == null) throw new DBliveryException("the order with that id does not exist");
		if (! o.getActualStatus().equals("Sended")) throw new DBliveryException("The order is not sended");
		o.getActualStatusObject().setActual(false);
		OrderStatus delivered = new Delivered();
		o.getStatus().add(delivered);
		return repository.storeOrder(o);
	}




	
	/**
	 * verifica si un pedido puede ser enviado para lo cual debe tener productos y estar en estado pending
	 * @param order pedido a ser enviado
	 * @return true en caso que pueda ser enviado, false en caso contrario
	 * @throws DBliveryException si el pedido no esta en estado pending.
	 */
	@Override
	public boolean canDeliver(Long order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o != null) {
			if (o.getActualStatus().equals("Pending")){
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



	@Override
	public List<Product> getProductByName(String name) {
		return this.repository.getProductByName(name);
	}
	
	@Transactional
	public List<Order> getAllOrdersMadeByUser(String username){
		return this.repository.getAllOrdersMadeByUser(username);
	}

	@Override
	public List<User> getUsersSpendingMoreThan(Float amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getTop10MoreExpensiveProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getTop6UsersMoreOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getCancelledOrdersInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getPendingOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getSentOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getSentMoreOneHour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getDeliveredOrdersSameDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> get5LessDeliveryUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getBestSellingProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsOnePrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductIncreaseMoreThan100() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supplier getSupplierLessExpensiveProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Supplier> getSuppliersDoNotSellOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getSoldProductsOn(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrdersCompleteMorethanOneDay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> getProductsWithPriceAt(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsNotSold() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> getOrderWithMoreQuantityOfProducts(Date day) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliverOrder(Long order, User deliveryUser, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(Long order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(Long order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}
}
