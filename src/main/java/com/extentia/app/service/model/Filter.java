package com.extentia.app.service.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Filter {
	private Set<String> categories = new HashSet<>();
	private double latitude, longitude;
	private String searchName;
	private int radius;
	
	public Filter(Collection<String> categories, double latitude, double longitude) {
		this.categories = new HashSet<String>(categories);
		this.latitude = latitude;
		this.longitude = longitude;
		this.searchName = null;
		this.radius = 30;
	}

	public Set<String> getCategories() {
		return categories;
	}
	
	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getSearchName() {
		return searchName;
	}
	
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public String getURLLatLng() {
		return latitude + "," + longitude;
	}
	
	public String getURLCategories() {
		StringBuilder urlCategories = new StringBuilder();
		categories.stream().forEach((category) -> {
			urlCategories.append(category).append(',');
		});
		return urlCategories.substring(0, urlCategories.length() - 1);
	}
}
