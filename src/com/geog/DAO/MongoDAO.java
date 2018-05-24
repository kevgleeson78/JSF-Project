package com.geog.DAO;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.bson.Document;
import com.geog.Model.HeadsOfState;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDAO {

	MongoClient mongoClient = new MongoClient();
	MongoDatabase database = mongoClient.getDatabase("headsOfState");
	MongoCollection<Document> headsOfState = database.getCollection("headsOfState");
	
	
	// FindIterable<Document> users = users2.find();
	public ArrayList<HeadsOfState> loadHeadOfState() throws Exception {
	
		ArrayList<HeadsOfState> userList = new ArrayList<HeadsOfState>();
		Gson gson = new Gson();

		FindIterable<Document> list = headsOfState.find().projection(fields(include("_id", "headOfState")));

		for (Document d : list) {
			HeadsOfState headsOfState = gson.fromJson(d.toJson(), HeadsOfState.class);
			userList.add(headsOfState);
		}

		for (HeadsOfState u : userList) {
			u.set_id(u.get_id());
			u.setHeadOfState(u.getHeadOfState());

		}
		

		return userList;

	}

	public ArrayList<HeadsOfState> addHeadOfState(HeadsOfState hos) throws SQLException {

		hos.set_id(hos.get_id());
		hos.setHeadOfState(hos.getHeadOfState());

		Document d = new Document();
		d.append("_id", hos.get_id());
		d.append("headOfState", hos.getHeadOfState());
		headsOfState.insertOne(d);
		return null;

	}

	public HeadsOfState deleteHeadOfState(String _id) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", _id);
		headsOfState.deleteOne(query);
		return null;
	}

}
