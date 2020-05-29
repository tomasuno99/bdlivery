package ar.edu.unlp.info.bd2.repositories;

import static com.mongodb.client.model.Aggregates.*;



import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;

import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.mongo.*;
import com.mongodb.client.*;
import java.util.*;
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

	public User insert(User user) {
		Document doc = new Document("_id", user.getObjectId());
		doc.append("email", user.getEmail());
		doc.append("password", user.getPassword());
		doc.append("username", user.getUsername());
		doc.append("name", user.getName());
		doc.append("date_of_birth", user.getDateOfBirth());
		this.getDb().getCollection("users").insertOne(doc);
		return user;
	}


}
