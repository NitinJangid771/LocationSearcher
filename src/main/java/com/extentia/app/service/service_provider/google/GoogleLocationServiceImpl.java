package com.extentia.app.service.service_provider.google;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.extentia.app.service.model.Filter;
import com.extentia.app.service.service_provider.ResponseParser;
import com.extentia.app.service.service_provider.URLTypeLocationServices;

/**
 * Singleton class for communicating with Google Place API.
 * 
 * */
public class GoogleLocationServiceImpl extends URLTypeLocationServices {
	@Value("${google.key}")
	private String KEY;
	private String KEY_URL_FORMAT = "&key=";
	private static final String BASE_FILTERED_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	private static final String BASE_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";
	private static final String INPUT = "&input=";
	private static final String INPUTTYPE = "inputtype=textquery";
	private static final String FIELDS = "&fields=name,place_id,types,geometry/location";
	private static final String TYPES = "&type=";
	private static final String RADIUS = "radius=1000";
	private static final String LOCATION = "&location=";
	
	@Autowired
	private GoogleReponseParser responseParser;
	
	private GoogleLocationServiceImpl() {
	}
	
	public static GoogleLocationServiceImpl getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private static class SingletonHelper {
		private static final GoogleLocationServiceImpl INSTANCE = new GoogleLocationServiceImpl();
	}

	/**
	 * With any change in this method please do update GoogleLocationServiceTest#createSerachURLTest also.
	 * */
	@Override
	public String createSerachURL(String name) {
		StringBuilder strURL = new StringBuilder();
		strURL.append(BASE_SEARCH_URL);
		strURL.append(INPUTTYPE);
		strURL.append(KEY_URL_FORMAT).append(KEY);
		strURL.append(FIELDS);
		strURL.append(INPUT).append(name);
		return strURL.toString();
	}

	/**
	 * With any change in this method please do update GoogleLocationServiceTest#createFilteredSerachURLTest also.
	 * */
	@Override
	public String createFilteredSerachURL(Filter filter) {
		StringBuilder strURL = new StringBuilder();
		strURL.append(BASE_FILTERED_SEARCH_URL);
		strURL.append(RADIUS).append(filter.getRadius());
		strURL.append(LOCATION).append(filter.getURLLatLng());
		strURL.append(TYPES).append(filter.getURLCategories());
		strURL.append(KEY_URL_FORMAT).append(KEY);
		return strURL.toString();
	}

	@Override
	protected ResponseParser getResponseParser() {
		return responseParser;
	}

	@Override
	protected void handleNonOKResponse() {
		System.out.println("Google search request returned reponse code other than OK.");
	}
	
	@Override
	protected void loadCategories() throws Exception {
		//TODO: Research on Google Place API to load all categories.
		//Possibly we need to manually fill this map
		this.categories = new HashMap<>();
	}
}
