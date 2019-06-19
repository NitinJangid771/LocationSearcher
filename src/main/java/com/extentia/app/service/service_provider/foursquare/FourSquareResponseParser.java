package com.extentia.app.service.service_provider.foursquare;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.extentia.app.service.model.FourSquarePlace;
import com.extentia.app.service.model.Place;
import com.extentia.app.service.service_provider.ResponseParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Scope("singleton")
public class FourSquareResponseParser implements ResponseParser {
	private static final String RESPONSE_META = "meta";
	private static final String RESPONSE_CODE = "code";
	private static final String RESPONSE = "response";
	private static final String CATEGORIES = "categories";
	private static final String GROUPS = "groups";
	private static final String VENUES = "venues";
	private static final String ITEMS = "items";
	private static final String VENUE = "venue";
	private static final String ID = "id";
	private static final String NAME = "name";
	
	public FourSquareResponseParser() {
	}

	@Override
	public List<Place> parseSearchResponse(String rawJson) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(rawJson);
		List<Place> places = new ArrayList<>();
		
		int responseCode = rootNode.path(RESPONSE_META).findPath(RESPONSE_CODE).asInt();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			JsonNode venues = rootNode.path(RESPONSE).path(VENUES);
			Iterator<JsonNode> venueElements = venues.elements();
			while (venueElements.hasNext()) {
		    	JsonNode placeNode = venueElements.next();
		    	Place place = new FourSquarePlace();
		        place.parsePlace(placeNode);
		        places.add(place);
	    	}
		}
		
		return places;
	}
	
	@Override
	public List<Place> parseFilteredSearchResponse(String rawJson) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(rawJson);
		List<Place> places = new ArrayList<>();
		
		int responseCode = rootNode.path(RESPONSE_META).findPath(RESPONSE_CODE).asInt();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			JsonNode groups = rootNode.path(RESPONSE).path(GROUPS);
			Iterator<JsonNode> groupElements = groups.elements();
		    while (groupElements.hasNext()) {
		    	JsonNode items = groupElements.next().path(ITEMS);
		    	Iterator<JsonNode> itemElements = items.elements();
		    	while (itemElements.hasNext()) {
			    	JsonNode placeNode = itemElements.next().path(VENUE);
			    	Place place = new FourSquarePlace();
			        place.parsePlace(placeNode);
			        places.add(place);
		    	}
		    }
		}
		
		return places;
	}
	
	/**
	 * Expected input Json Structure:
	 * 
	 * response : {
	 * 		categories : [
	 * 			{	
	 * 				id : "String",
	 * 				name : "String",
	 * 				categories : [] //This is recursive
	 * 			}
	 *  	]
	 * }
	 * 
	 * */
	public Map<String, String> parseCategories(String rawJson) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(rawJson);
		Map<String, String> categories = new HashMap<>();
		
		int responseCode = rootNode.path(RESPONSE_META).findPath(RESPONSE_CODE).asInt();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			JsonNode catJsonNode = rootNode.path(RESPONSE).path(CATEGORIES);
			parseElements(catJsonNode, categories);
		}
		
		return categories;
	}
	
	private void parseElements(JsonNode catJsonNode, Map<String, String> categories) {
		Iterator<JsonNode> elements = catJsonNode.elements();
	    while (elements.hasNext()) {
	    	JsonNode catElement = elements.next();
	    	String displayName = catElement.path(NAME).asText();
	    	String id = catElement.path(ID).asText();
	    	categories.put(displayName, id);
	    	
	    	JsonNode interativeJsonNode = catElement.path(CATEGORIES);
	    	parseElements(interativeJsonNode, categories);
	    }
	}
	
}
