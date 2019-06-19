package com.extentia.app.service.service_provider.foursquare;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.extentia.app.service.model.Filter;
import com.extentia.app.service.service_provider.ResponseParser;
import com.extentia.app.service.service_provider.URLTypeLocationServices;

/**
 * Singleton class for communicating with FourSquare Place API.
 * 
 * */
public class FourSquareLocationServiceImpl extends URLTypeLocationServices {
	@Value("${foursquare.clientsecret}")
	private String CLIENT_SECRET;
	@Value("${foursquare.clientid}")
	private String CLIENT_ID;
	private String CLIENT_SECRET_URL_FORMAT = "&client_secret=";
	private String CLIENT_ID_URL_FORMAT = "client_id=";
	private static final String ALL_CATEGORIES_URL = "https://api.foursquare.com/v2/venues/categories?v=20190214&client_secret=RSIXSZFYNMSRNLL5WQGONRY3CKBAKCOL23ZJMTHLJSJVC1BB&client_id=SKBX52BKBEBW5BMLPFV0GB2VLZKWWJB1HMJU4B4R544ZBNSW";
	private static final String BASE_FILTERED_SEARCH_URL = "https://api.foursquare.com/v2/venues/explore?";
	private static final String BASE_SEARCH_URL = "https://api.foursquare.com/v2/venues/search?";
	private static final String BROWSE_INTENT = "&intent=browse";
	private static final String GLOBAL_INTENT = "&intent=global";
	private static final String VERSION = "&v=20190606";
	private static final String LIMIT = "&limit=10";
	private static final String LAT_LNG = "&ll=";
	private static final String QUERY = "&query=";
	//private static final String CATEGORY_ID = "&categoryid=";
		
	@Autowired
	private FourSquareResponseParser responseParser;
	
	public FourSquareLocationServiceImpl() {
	}
	
	public static FourSquareLocationServiceImpl getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private static class SingletonHelper {
		private static final FourSquareLocationServiceImpl INSTANCE = new FourSquareLocationServiceImpl();
	}
		
	@Override
	public String createSerachURL(String name) {
		StringBuilder strURL = new StringBuilder();
		strURL.append(BASE_SEARCH_URL);
		strURL.append(CLIENT_ID_URL_FORMAT).append(CLIENT_ID);
		strURL.append(CLIENT_SECRET_URL_FORMAT).append(CLIENT_SECRET);
		strURL.append(GLOBAL_INTENT);
		strURL.append(VERSION);
		strURL.append(LIMIT);
		strURL.append(QUERY).append(name);
		return strURL.toString();
	}

	@Override
	public String createFilteredSerachURL(Filter filter) {
		StringBuilder strURL = new StringBuilder();
		strURL.append(BASE_FILTERED_SEARCH_URL);
		strURL.append(CLIENT_ID_URL_FORMAT).append(CLIENT_ID);
		strURL.append(CLIENT_SECRET_URL_FORMAT).append(CLIENT_SECRET);
		strURL.append(BROWSE_INTENT);
		strURL.append(VERSION);
		strURL.append(LIMIT);
		strURL.append(LAT_LNG).append(filter.getURLLatLng());
		strURL.append(QUERY).append(filter.getURLCategories());
		//strURL.append(CATEGORY_ID).append(filter.getURLCategories());
		return strURL.toString();
	}

	@Override
	protected ResponseParser getResponseParser() {
		return responseParser;
	}
	
	@Override
	protected void handleNonOKResponse() {
		System.out.println("FourSquare search request returned reponse code other than OK.");
	}
	
	@Override
	protected void loadCategories() throws Exception {
		URL url = new URL(ALL_CATEGORIES_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//Parse and load categories
			this.categories = responseParser.parseCategories(response.toString());
		}
		else {
			throw new RuntimeException("No Categories available.");
		}
	}
}
