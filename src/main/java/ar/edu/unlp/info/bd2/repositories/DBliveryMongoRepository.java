package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;



import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.bson.types.ObjectId;
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

}
