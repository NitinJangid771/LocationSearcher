package com.extentia.app.service.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

@Component
public class GooglePlace extends Place {
	public static final String KEY_ID = "place_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_TYPES = "types";
	public static final String KEY_GEOMETRY = "geometry";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_LAT = "lat";
	public static final String KEY_LNG = "lng";

	/**
	 * Expected structure of input PlaceNode
	 * 
	 *	{
	         "geometry" : {
	            "location" : {
	               "lat" : 18.5204303,
	               "lng" : 73.8567437
	            }
	         },
	         "name" : "Pune",
	         "place_id" : "ChIJARFGZy6_wjsRQ-Oenb9DjYI",
	         "types" : [ "locality", "political" ]
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
        JsonNode typesNode = placeNode.path(KEY_TYPES);
        Iterator<JsonNode> typesItr = typesNode.elements();
        while (typesItr.hasNext()) {
       	 	JsonNode typeNode = typesItr.next();
       	 	types.add(typeNode.asText());
        }
        setTypes(types);
        
        //set latitude and longitude
        JsonNode locationNode = placeNode.path(KEY_GEOMETRY).path(KEY_LOCATION);
        double lat = locationNode.path(KEY_LAT).asDouble();
        double lng = locationNode.path(KEY_LNG).asDouble();
        setLatitude(lat);
        setLongitude(lng);
	}

}
