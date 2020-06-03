package ar.edu.unlp.info.bd2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;

import ar.edu.unlp.info.bd2.model.Order;
import ar.edu.unlp.info.bd2.model.OrderStatus;
import ar.edu.unlp.info.bd2.model.Price;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Supplier;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.repositories.DBliveryException;
import ar.edu.unlp.info.bd2.repositories.DBliveryMongoRepository;

public class DBliveryServiceImpl implements DBliveryService {
	
	DBliveryMongoRepository repository;
	
	
	
	
	public DBliveryServiceImpl(DBliveryMongoRepository repository) {
		this.repository = repository;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier) {
		Product p = new Product(name, price, weight);
		//p.updatePrice(price, new Date());
		repository.insertWithAssociation("products", p.getClass(), p, supplier, "product_supplier");
		return p;
	}

	@Override
	public Product createProduct(String name, Float price, Float weight, Supplier supplier, Date date) {
		return null;
	}

	@Override
	public Supplier createSupplier(String name, String cuil, String address, Float coordX, Float coordY) {
		Supplier s= new Supplier(name, cuil, address, coordX, coordY);
		repository.insert("suppliers", s.getClass(), s);
		return s;
	}

	@Override
    public User createUser(String email, String password, String username, String name, Date dateOfBirth) {
//        if (!repository.uniqueUsername(username))
        User user = new User (email, password, username, name, dateOfBirth);
        repository.insert("users", user.getClass(), user);
        return user;
	}
	@Override
    public Product updateProductPrice(ObjectId id, Float price, Date startDate) throws DBliveryException {
        Product p = repository.getProductById(id);
        if(p.getObjectId() != null) {
            repository.replaceProduct(p.updatePrice(price, startDate));
            return p;
        }else {throw new DBliveryException("The product don't exist");}
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
		return Optional.of(repository.getOrderById(id));
	}

	@Override
	public Order createOrder(Date dateOfOrder, String address, Float coordX, Float coordY, User client) {
		Order o = new Order(dateOfOrder, address, coordX, coordY);
		repository.insertWithAssociation("orders",o.getClass(), o, client, "order_client");
		return o;
	}

//	@Override
//	public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
//		return repository.addProduct(order, quantity, product);
//	}

	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order deliverOrder(ObjectId order, User deliveryUser, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(ObjectId order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order cancelOrder(ObjectId order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(ObjectId order) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order finishOrder(ObjectId order, Date date) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canCancel(ObjectId order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canFinish(ObjectId id) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canDeliver(ObjectId order) throws DBliveryException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OrderStatus getActualStatus(ObjectId order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return repository.getProductsByName(name);
	}

	@Override
	public Order addProduct(ObjectId order, Long quantity, Product product) throws DBliveryException {
		// TODO Auto-generated method stub
		return null;
	}


}
