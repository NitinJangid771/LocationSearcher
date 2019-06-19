package com.extentia.app.service.service_provider;

import java.util.List;
import java.util.Map;

import com.extentia.app.service.model.Filter;
import com.extentia.app.service.model.Place;

public interface LocationService {

	/**
	 * Returns List of Place exactly matching with the input name, else return empty List.
	 * 
	 * */
	List<Place> search(String name) throws Exception;
	
	/**
	 * Return List of places available after applying the filter.
	 * 
	 * */
	List<Place> filteredSearch(Filter filter) throws Exception;
	
	/**
	 * Return List of categories available.
	 * 
	 * */
	Map<String, String> getAllCategories() throws Exception;
}
