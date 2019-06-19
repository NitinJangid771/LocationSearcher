package com.extentia.app.service.service_provider;

import java.util.List;

import com.extentia.app.service.model.Place;

/**
 * Interface to represent response parser for different underlying Place API's
 * 
 * */
public interface ResponseParser {
	
	List<Place> parseSearchResponse(String rawJson)  throws Exception;
	
	List<Place> parseFilteredSearchResponse(String rawJson)  throws Exception;

}
