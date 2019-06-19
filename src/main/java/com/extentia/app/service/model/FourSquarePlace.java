package com.extentia.app.service.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class FourSquarePlace extends Place {
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	public static final String KEY_CATEGORIES = "categories";
	public static final String KEY_CATEGORY_NAME = "name";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_LAT = "lat";
	public static final String KEY_LNG = "lng";

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
	@Override
	public void parsePlace(JsonNode placeNode) throws Exception {
		//set name
        setName(placeNode.path(KEY_NAME).asText());
        
        //set place id
        setId(placeNode.path(KEY_ID).asText());
        
        //create and set types
        Set<String> types = new HashSet<>();
        JsonNode categoriesNode = placeNode.path(KEY_CATEGORIES);
        Iterator<JsonNode> categoriesItr = categoriesNode.elements();
        while (categoriesItr.hasNext()) {
        	JsonNode categoryNode = categoriesItr.next().path(KEY_CATEGORY_NAME);
        	types.add(categoryNode.asText());
        }
        setTypes(types);
        
        //set latitude and longitude
        JsonNode locationNode = placeNode.path(KEY_LOCATION);
        double lat = locationNode.path(KEY_LAT).asDouble();
        double lng = locationNode.path(KEY_LNG).asDouble();
        setLatitude(lat);
        setLongitude(lng);
	}
}
