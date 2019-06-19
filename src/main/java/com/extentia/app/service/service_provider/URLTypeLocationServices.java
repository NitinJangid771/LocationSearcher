package com.extentia.app.service.service_provider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extentia.app.service.model.Filter;
import com.extentia.app.service.model.Place;

/**
 * Base class for all URL type location services.
 *
 */
public abstract class URLTypeLocationServices implements LocationService {
	
	public static final int SEARCH_CODE = 1;
	public static final int FILTERED_SEARCH_CODE = 2;
	public static final String EMPTY_STRING = "";
	protected Map<String, String> categories;
	abstract protected void loadCategories() throws Exception;
	abstract public String createSerachURL(String name);
	abstract public String createFilteredSerachURL(Filter filter);
	abstract protected ResponseParser getResponseParser();
	abstract protected void handleNonOKResponse();
	
	@Override
	public List<Place> search(String name) throws Exception {
		String strURL = createSerachURL(name);
		return getAndParsePlaces(strURL, SEARCH_CODE);
	}

	@Override
	public List<Place> filteredSearch(Filter filter) throws Exception {
		String strURL = createFilteredSerachURL(filter);
		return getAndParsePlaces(strURL, FILTERED_SEARCH_CODE);
	}
	
	public List<Place> getAndParsePlaces(String strURL, int parseCode) throws Exception {
		if (strURL == EMPTY_STRING) {
			return new ArrayList<Place>();
		}
		URL url = new URL(strURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		List<Place> places;
		
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			ResponseParser reponseParser = getResponseParser();
			switch (parseCode) {
				case SEARCH_CODE:
					places = reponseParser.parseSearchResponse(response.toString());
					break;
				case FILTERED_SEARCH_CODE:
					places = reponseParser.parseFilteredSearchResponse(response.toString());
					break;
				default:
					throw new IllegalArgumentException("Parse code not defined");
			}
		}
		else {
			places = new ArrayList<>();
			handleNonOKResponse();
		}

		return places;
	}
	
	@Override
	public Map<String, String> getAllCategories() throws Exception {
		if (categories != null) {
			//FlyWeight to avoid HTTP hits.
			return categories;
		}
		
		loadCategories();
		return categories;
	}
}
