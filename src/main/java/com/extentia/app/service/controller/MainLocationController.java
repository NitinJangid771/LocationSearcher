package com.extentia.app.service.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.extentia.app.service.model.Filter;
import com.extentia.app.service.model.Place;
import com.extentia.app.service.service_provider.LocationService;

@Produces(MediaType.APPLICATION_JSON)
@RestController
@EnableAutoConfiguration
public class MainLocationController {
	
	@Autowired
	@Qualifier("googleServiceProvider")
	private LocationService searchServiceProvider;

	@Autowired
	@Qualifier("fourSquareServiceProvider")
	private LocationService filteredSearchServiceProvider;

	@RequestMapping(method = RequestMethod.GET, value="/search")
	@ResponseBody
	public List<Place> search(@RequestParam("name") String name) {
		List<Place> places = null;
		try {
			places = searchServiceProvider.search(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return places;
	}

	@RequestMapping(method = RequestMethod.GET, value="/filteredsearch")
	@ResponseBody
	public List<Place> filteredSearch(@RequestParam("lat") double lat, @RequestParam("lng") double lng, @RequestParam("categories") List<String> categories) {
		if  (categories.size() == 0) {
			throw new IllegalArgumentException("Empty filter categories not allowed");
		}
		
		List<Place> places = new ArrayList<>();
		try {
			Filter filter = new Filter(categories, lat, lng);
			places = filteredSearchServiceProvider.filteredSearch(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return places;
	}
}
