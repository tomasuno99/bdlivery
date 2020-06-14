package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;



import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.joda.time.chrono.AssembledChronology.Fields;
import org.springframework.beans.factory.annotation.Autowired;

public class DBliveryMongoRepository {

    @Autowired private MongoClient client;


    public void saveAssociation(PersistentObject source, PersistentObject destination, String associationName) {
        Association association = new Association(source.getObjectId(), destination.getObjectId());
        this.getDb()
                .getCollection(associationName, Association.class)
                .insertOne(association);
    }

    public MongoDatabase getDb() {
        return this.client.getDatabase("bd2_grupo" + this.getGroupNumber() );
    }

    private Integer getGroupNumber() { return 15; }

    public <T extends PersistentObject> List<T> getAssociatedObjects(
            PersistentObject source, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("source", source.getObjectId())),
                                        lookup(destCollection, "destination", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
    
    public <T extends PersistentObject> List<T> getObjectsAssociatedWith(
            ObjectId objectId, Class<T> objectClass, String association, String destCollection) {
        AggregateIterable<T> iterable =
                this.getDb()
                        .getCollection(association, objectClass)
                        .aggregate(
                                Arrays.asList(
                                        match(eq("destination", objectId)),
                                        lookup(destCollection, "source", "_id", "_matches"),
                                        unwind("$_matches"),
                                        replaceRoot("$_matches")));
        Stream<T> stream =
                StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), false);
        return stream.collect(Collectors.toList());
    }
    

    public void insert(String collectionName, Class objClass, Object obj) {
        this.getDb().getCollection(collectionName, objClass).insertOne(obj);
    }
    
    public void insertWithAssociation(String collectionName, Class objClass, PersistentObject assocSource, PersistentObject assocDestination, String assocName) {
    	this.saveAssociation(assocSource, assocDestination, assocName);
    	this.insert(collectionName, objClass, assocSource);
    }
    
//    public Order addProduct(ObjectId order, Long quantity, Product product) {
//    	this.getDb().getCollection("orders", order.getClass()).findOneAndUpdate((eq("_id", order),  );
//    }
    
//    public Product updateProductPrice(ObjectId id, Price price) {
//        MongoCollection<Product> mongoDoc = this.getDb().getCollection("products", Product.class);
//        Product p = mongoDoc.find(eq("_id", id)).first();
//        Product prod = p;
//        prod.updatePrice(price.getPrice(), price.getStartDate());
//        mongoDoc.replaceOne(eq("_id", id), prod);
//        return p;
//    }

	public Product getProductById(ObjectId id) {
		MongoCollection<Product> collection = this.getDb().getCollection("products", Product.class);
        return collection.find(eq("_id", id)).first();
	}

	public void replaceProduct(Product product) {
		this.getDb().getCollection("products", Product.class).replaceOne(eq("_id", product.getObjectId()), product);
	}

	public Order getOrderById(ObjectId id) {
		return this.getDb().getCollection("orders", Order.class).find(eq("_id", id)).first();
	}

	public User getUserByUsername(String username) {
		return this.getDb().getCollection("users", User.class).find(eq("username", username)).first();
	}

	public User getUserByEmail(String email) {
		return this.getDb().getCollection("users", User.class).find(eq("email", email)).first();
	}

	public User getUserById(ObjectId id) {
		return this.getDb().getCollection("users", User.class).find(eq("_id", id)).first();
	}

	public List<Product> getProductsByName(String name) {
		List<Product> list = new ArrayList<>();
		Pattern pat = Pattern.compile(".*" + name + ".*");
		for (Product obj: this.getDb().getCollection("products", Product.class).find(regex("name", pat))) {
			list.add(obj);
		}
		return list;
	}

	public void updateOrder(Order o) {
		this.getDb().getCollection("orders", Order.class).replaceOne(eq("_id", o.getObjectId()), o);
	}

	public List<Order> getOrdersByUser(ObjectId user_id) {
		List<Order> list = new ArrayList<>();
		for (Order obj: this.getDb().getCollection("orders", Order.class).find(eq("client._id", user_id))) {
			list.add(obj);
		}
		return list;
	}

	public List<Order> getSentOrders() {
		List<Order> list = new ArrayList<>();
		MongoCollection db = this.getDb().getCollection("orders", Order.class);
		
        Bson match = match(Filters.elemMatch("statusHistory", Filters.and(eq("status", "Sended"),eq("actual", true))));
        db.aggregate(Arrays.asList(match)).into(list);
		return list;
	}
	
	public List<Order> getPendingOrders() {
		List<Order> list = new ArrayList<>();
		MongoCollection db = this.getDb().getCollection("orders", Order.class);
		
		Bson match = match(Filters.elemMatch("statusHistory", Filters.and(eq("status", "Pending"),eq("actual", true))));
        db.aggregate(Arrays.asList(match)).into(list);
		return list;
	}
	
	public List<Order> getDeliveredOrdersInPeriod(Date startDate, Date endDate) {
		List<Order> list = new ArrayList<>();
		MongoCollection db = this.getDb().getCollection("orders", Order.class);
		
		Bson compareDates = Filters.and(Filters.gte("dateOfOrder", startDate),Filters.lte("dateOfOrder", endDate));
		Bson delivered = Filters.elemMatch("statusHistory", Filters.and(eq("status", "Delivered"),eq("actual", true)));
		Bson match = match(Filters.and(compareDates, delivered));
        
		db.aggregate(Arrays.asList(match)).into(list);
		
		return list;
	}

	public List<Product> getProductsOnePrice() {
		List<Product> list = new ArrayList<>();
		MongoCollection<Product> db = this.getDb().getCollection("products", Product.class);
		
		Bson match = match(Filters.size("prices", 1));
        
		db.aggregate(Arrays.asList(match)).into(list);
		
		return list;
	}
	
	public Product getMaxWeigth() {
		List<Product> list = new ArrayList<>();
		MongoCollection<Product> db = this.getDb().getCollection("products", Product.class);
		
		Bson match = sort(Sorts.orderBy(Sorts.descending("weight")));
        
		return db.aggregate(Arrays.asList(match,limit(1))).first();
	}
	
	public List<Order> getOrderNearPlazaMoreno() {
		List<Order> list = new ArrayList<>();
		MongoCollection<Order> db = this.getDb().getCollection("orders", Order.class);
		Point point = new Point(new Position(-34.921236,-57.954571));
		
		Bson match = Filters.near("position", point, 400.0, 0.0);
        db.find(match).into(list);
        
        return list;
	}

//	public Product getBestSellingProduct() {
//		List<Order> list = new ArrayList<>();
//		MongoCollection<Order> db = this.getDb().getCollection("orders", Order.class);
//		Point point = new Point(new Position(-34.921236,-57.954571));
//		
//		Bson match = Filters.near("position", point, 400.0, 0.0);
//        db.find(match).into(list);
//        
//        return list;
//	}

	public List<Product> getSoldProductsOn(Date day) {
		List<Product> list = new ArrayList<>();
		MongoCollection<Order> db = this.getDb().getCollection("orders", Order.class);
		MongoCollection<Product> dbProduct = this.getDb().getCollection("products", Product.class);

		
		Bson match = match(eq("dateOfOrder", day));
		
		db.aggregate(Arrays.asList(match,unwind("$products"),replaceRoot("$products.product"),out("productsDay"))).toCollection();
		
		this.getDb().getCollection("productsDay",Product.class).aggregate(Arrays.asList()).into(list);
		
        return list;
	}

	public List<Supplier> getTopNSuppliersInSentOrders(int n) {
		List<Supplier> list = new ArrayList<>();
		MongoCollection<Order> db = this.getDb().getCollection("orders", Order.class);
		
		
		Bson match = Filters.elemMatch("statusHistory", Filters.and(eq("status", "Sent"),eq("actual", true)));
		Bson group = group("$products.product.supplier", Accumulators.sum("$products.product.quantity", 1));
		Bson sort = sort(Sorts.orderBy(Sorts.descending("supplier.totalProducts")));
		Bson addField = addFields(new Field<Document>("totalProducts", new Document("$size", "$products.quantity")));
		
		
		db.aggregate(Arrays.asList(match,unwind("$products"),group, addField, replaceRoot("$products.product.supplier"),out("auxCollection"))).toCollection();
		
		this.getDb().getCollection("auxCollection").aggregate(Arrays.asList(sort, replaceRoot("$products.product.supplier"),out("selectedSupplier"),limit(n))).toCollection();
		
		this.getDb().getCollection("selectedSuppliers", Supplier.class).aggregate(Arrays.asList()).into(list);
		
		return list;
	}
	
	
}
