package com.extentia.app.service.service_provider.google;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.extentia.app.service.model.GooglePlace;
import com.extentia.app.service.model.Place;
import com.extentia.app.service.service_provider.ResponseParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reads the response from Google Place API and creates Place Object representing the google location.
 * Also handles error, empty response.
 * 
 * */
@Service
@Scope("singleton")
public class GoogleReponseParser implements ResponseParser {
	public static final String KEY_STATUS = "status";
	public static final String KEY_CANDIDATES = "candidates";
	public static final String KEY_RESULTS = "results";
	public static final String OK_STATUS = "OK";
	public static final String OVER_QUERY_LIMIT_STATUS = "OVER_QUERY_LIMIT";
	public static final String ZERO_RESULTS_STATUS = "ZERO_RESULTS";

	@Override
	public List<Place> parseSearchResponse(String rawJson) throws Exception {
		return parseResponse(rawJson, false);
	}

	@Override
	public List<Place> parseFilteredSearchResponse(String rawJson) throws Exception {
		return parseResponse(rawJson, true);
	}
	
	private List<Place> parseResponse(String rawJson, boolean isFilteredSearch) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(rawJson);
		List<Place> places = new ArrayList<>();
		
		String status = rootNode.path(KEY_STATUS).asText();
		switch (status) {
			case OK_STATUS:
				JsonNode candidatesNode = rootNode.path(isFilteredSearch ? KEY_RESULTS : KEY_CANDIDATES);
			     Iterator<JsonNode> candidates = candidatesNode.elements();
			     while (candidates.hasNext()) {
			         JsonNode placeNode = candidates.next();
			         Place place = new GooglePlace();
			         place.parsePlace(placeNode);
			         places.add(place);
			     }
			     break;
			case OVER_QUERY_LIMIT_STATUS:
				System.out.println("Response returned OVER_QUERY_LIMIT_STATUS");
				break;
			case ZERO_RESULTS_STATUS:
				//Is special handling required for zero results?
			default:
				//what to do for other status?
		}
		
		return places;
	}
	
}