package ar.edu.unlp.info.bd2.services;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

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
import ar.edu.unlp.info.bd2.mongo.PersistentObject;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;

public class DBliveryServiceImpl implements DBliveryService, DBliveryStatisticsService {

	DBliveryMongoRepository repository;

	public DBliveryServiceImpl(DBliveryMongoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name, price, weight);
		// p.updatePrice(price, new Date());
		repository.insertWithAssociation("products", p.getClass(), p, supplier, "product_supplier");
		return p;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		Product p = new Product(name, price, weight, date);
		repository.insertWithAssociation("products", p.getClass(), p, supplier, "product_supplier");
		return p;
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s = new Supplier(name, cuil, address, coordX, coordY);
		repository.insert("suppliers", s.getClass(), s);
		return s;
	}

	@Override
	public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
//        if (!repository.uniqueUsername(username))
		User user = new User(email, password, username, name, dateOfBirth);
		repository.insert("users", user.getClass(), user);		
		return user;
	}

	@Override
	public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException {
		Product p = repository.getProductById(id);
		if (p.getObjectId() != null) {
			repository.replaceProduct(p.updatePrice(price, startDate));
			return p;
		} else {
			throw new DBliveryException("The product don't exist");
		}
	}

	@Override
	public Optional<User> getUserById(ObjectId id) {
		return Optional.of(repository.getUserById(id));
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		return Optional.of(repository.getUserByEmail(email));
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return Optional.of(repository.getUserByUsername(username));
	}

	@Override
	public Optional<Order> getOrderById(ObjectId id) {
		Order o = repository.getOrderById(id);
		o.setClient(repository.getClientFromOrder(id));
		return Optional.of(o);
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = new Order(dateOfOrder, address, coordX, coordY, client);
		repository.insertWithAssociation("orders", o.getClass(), o, client, "order_client");
//		repository.saveAssociation(client, o, associationName);
		return o;
	}

	/**
	 * agrega un producto al pedido
	 * 
	 * @param order    pedido al cual se le agrega el producto
	 * @param quantity cantidad de producto a agregar
	 * @param product  producto a agregar
	 * @return el pedido con el nuevo producto
	 * @throws DBliveryException en caso de no existir el pedido
	 */
	@Override
	public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null) {
			OrderProduct op = new OrderProduct(quantity, product);
			o.getProducts().add(op);
			this.repository.updateOrder(o);
			return o;
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}

	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null && this.canDeliver(order)) {
			OrderStatus os = new Delivered();
			o.changeStatus(os);
			o.setDeliveryUser(deliveryUser);
			this.repository.updateOrder(o);
			return o;
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}
	@Override
	public Order finishOrder(ObjectId order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null && this.canFinish(order)) {
			OrderStatus os = new Delivered();
			o.changeStatus(os);
			this.repository.updateOrder(o);
			return o;
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}
	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null && this.canDeliver(order)) {
			OrderStatus os = new Sended(date);
			o.changeStatus(os);
			o.setDeliveryUser(deliveryUser);
			this.repository.updateOrder(o);
			return o;
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}
	/**
	 * Cancela un pedido
	 * @param order id del pedido a cancelar
	 * @return el pedido modificado
	 * @throws DBliveryException en caso de no existir el pedido o si el pedido no esta en estado pending
	 */
	@Override
	public Order cancelOrder(ObjectId order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (this.canCancel(order)) {
			OrderStatus os = new Cancelled();
			o.changeStatus(os);
			this.repository.updateOrder(o);
			return o;
		}
		else {throw new DBliveryException("The order isn't in Pending");}
	}

	@Override
	public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (this.canCancel(order)) {
			OrderStatus os = new Cancelled(date);
			o.changeStatus(os);
			this.repository.updateOrder(o);
			return o;
		}
		else {throw new DBliveryException("The order isn't in Pending");}
	}



	@Override
	public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null && this.canFinish(order)) {
			OrderStatus os = new Delivered(date);
			o.changeStatus(os);
			this.repository.updateOrder(o);
			return o;
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}

	@Override
	public boolean canCancel(ObjectId order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null) {
			if (o.getActualStatus().equals("Pending")) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}

	@Override
	public boolean canFinish(ObjectId id) throws DBliveryException {
		Order o = this.repository.getOrderById(id);
		if (o.getObjectId() != null) {
			if (o.getActualStatus().equals("Sended")) {
				return true;
			} else {
				return false;
			}
		} else {
			throw new DBliveryException("The order don't exist");
		}
	}

	@Override
	public boolean canDeliver(ObjectId order) throws DBliveryException {
		Order o = this.repository.getOrderById(order);
		if (o.getObjectId() != null) {
			if (!o.getProducts().isEmpty()) {
				if (o.getActualStatus().equals("Pending")) {
					return true;
				} else {
					throw new DBliveryException("The order is not in pending state");
				}
			} else {
				return false;
			}

		} else {
			throw new DBliveryException("The order don't exist");
		}
	}

	@Override
	public OrderStatus getActualStatus(ObjectId order) {
		Order o = this.repository.getOrderById(order);
		return o.getActualStatusObject();
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return repository.getProductsByName(name);
	}

	@Override
	public List<Order> getAllOrdersMadeByUser(String username) throws DBliveryException {
		User u = this.repository.getUserByUsername(username);
		if (u.getObjectId() != null) {
			return repository.getOrdersByUser(u.getObjectId());
		}
		else {
			throw new DBliveryException("The user don't exist");
		}
		
	}

	@Override
	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		return this.repository.getTopNSuppliersInSentOrders(n);
	}

	@Override
	public List<Order> getPendingOrders() {
		return this.repository.getPendingOrders();
	}

	@Override
	public List<Order> getSentOrders() {
		return this.repository.getSentOrders();
	}

	@Override
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		return this.repository.getDeliveredOrdersInPeriod(startDate, endDate);
	}

	@Override
	public List<Order> getDeliveredOrdersForUser(String username) {
		return this.repository.getDeliveredOrdersForUser(username);
//		List<Order> list = new ArrayList<>(); 
//		
//		try {
//			for (Order o: this.getAllOrdersMadeByUser(username)){
//				if (o.getActualStatus().equals("Delivered")) {
//					list.add(o);
//				}
//			}
//		} catch (DBliveryException e) {
//			return list; // retorna la lista vacia si ocurre la excepcion
//		}
//		return list;
	}

	@Override
	public Product getBestSellingProduct() {
//		return this.repository.getBestSellingProduct();
		return this.repository.getBestSellingProduct();
	}

	@Override
	public List<Product> getProductsOnePrice() {
		return this.repository.getProductsOnePrice();
	}

	@Override
	public List<Product> getSoldProductsOn(Date day) {
		return this.repository.getSoldProductsOn(day);
	}

	@Override
	public Product getMaxWeigth() {
		return this.repository.getMaxWeigth();
	}

	@Override
	public List<Order> getOrderNearPlazaMoreno() {
		return this.repository.getOrderNearPlazaMoreno();
	}

}
