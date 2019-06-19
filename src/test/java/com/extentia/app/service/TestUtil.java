package com.extentia.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.extentia.app.service.model.Filter;
import com.extentia.app.service.model.FourSquarePlace;
import com.extentia.app.service.model.GooglePlace;
import com.extentia.app.service.model.Place;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestUtil {
	public static final double dummyLat = 20.5937;
	public static final double dummyLng = 78.9629;
	public static final String dummyType = "dummyType";
	public static final List<String> dummyCategories = new ArrayList<>();
	
	static {
		dummyCategories.add(dummyType);
	}
	
	public static List<Place> getTestPlaces(int placesCount) {
		List<Place> places = new ArrayList<>();		
		for (int i = 0; i < placesCount; i++) {
			places.add(getDummyPlace(i));
		}
		return places;
	}
	
	public static boolean isSamePlaces(List<Place> list1, List<Place> list2) {
		if (list1.size() == list2.size() && list1.containsAll(list2)) {
			//List with same size and containing all elements means both list are order-insensitive equal.
			return true;
		}
		return false;
	}
	
	/**
	 * Method added to reduce code to create dummy Place for testing purpose.
	 * The Dummy place returned is only for test purpose, It does not resembles/represent any of the real places.
	 * 
	 * */
	public static Place getDummyPlace(int sequnceNum) {
		String name = "dummy" + sequnceNum;
		String id = "dummyID_XYZ" + sequnceNum;
		double lat = dummyLat + sequnceNum;
		double lng = dummyLng + sequnceNum;
		Set<String> types = new HashSet<>();
		types.add(dummyType + sequnceNum);
		
		return getDummyPlace(name, id, lat, lng, types);
	}
	
	public static Filter getTestFilter() {
		return new Filter(dummyCategories , dummyLat, dummyLng);
	}
	
	public static JsonNode getGooglePlaceJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		
		//Create Name and ID
		objectNode.put(GooglePlace.KEY_NAME, "DummyPlace");
		objectNode.put(GooglePlace.KEY_ID, "bhsdjfbgdjshf");
		
		//Create Lat Lng Node
		ObjectNode geometryNode = objectMapper.createObjectNode();
		ObjectNode locationNode = objectMapper.createObjectNode();
		locationNode.put(GooglePlace.KEY_LAT, dummyLat);
		locationNode.put(GooglePlace.KEY_LNG, dummyLng);
		geometryNode.set(GooglePlace.KEY_LOCATION, locationNode);
		objectNode.set(GooglePlace.KEY_GEOMETRY, geometryNode);
		
		//Create Categories
		ArrayNode categoriesArray = objectMapper.valueToTree(dummyCategories);
		objectNode.putArray(GooglePlace.KEY_TYPES).addAll(categoriesArray);
		
		return objectNode;
	}

	/**
	 * Expected structure of input PlaceNode
	 * 
	 *	{
            "location" : {
               "lat" : 18.5204303,
               "lng" : 73.8567437
            },
	        "name" : "Pune",
	        "id" : "4a9a941cf964a520e43120e3",
	        "categories" : [ 
	        	{
	        		"id":"4bf58dd8d48988d1ed931735",
					"name":"Airport"
				} 
	        ]
	    }
	 * */
	public static JsonNode getFourSquarePlaceJson() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode objectNode = objectMapper.createObjectNode();
		
		//Create Name and ID
		objectNode.put(FourSquarePlace.KEY_NAME, "DummyPlace");
		objectNode.put(FourSquarePlace.KEY_ID, "bhsdjfbgdjshf");
		
		//Create Lat Lng Node
		ObjectNode locationNode = objectMapper.createObjectNode();
		locationNode.put(FourSquarePlace.KEY_LAT, dummyLat);
		locationNode.put(FourSquarePlace.KEY_LNG, dummyLng);
		objectNode.set(FourSquarePlace.KEY_LOCATION, locationNode);
		
		//Create Categories
		ObjectNode categoriesNode = objectMapper.createObjectNode();
		categoriesNode.put(FourSquarePlace.KEY_CATEGORY_NAME, dummyType);
		categoriesNode.put(FourSquarePlace.KEY_ID, "4bf58dd8d48988d1ed931735");
		ArrayNode categoriesArray = objectMapper.createArrayNode();
		categoriesArray.add(categoriesNode);
		objectNode.putArray(FourSquarePlace.KEY_CATEGORIES).addAll(categoriesArray);
		
		return objectNode;
	}
	
	public static Place getDummyPlace(String name, String id, double lat, double lng, Set<String> types) {
		Place dummyPlace = new Place() {
			@Override
			public void parsePlace(JsonNode placeNode) throws Exception {
				//Dummy place, Nothing to do here.
			}
		};
		
		dummyPlace.setName(name);
		dummyPlace.setId(id);
		dummyPlace.setTypes(types);
		dummyPlace.setLatitude(lat);
		dummyPlace.setLongitude(lng);
		
		return dummyPlace;
	};
}
