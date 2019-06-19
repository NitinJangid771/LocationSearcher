package com.extentia.app.service.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents individual location/place returned from under lying "Places API"
 * 
 * */
abstract public class Place {
	protected String name;
	protected Set<String> types;
	protected String id;
	protected double latitude = -1, longitude = -1;
	
	public Place() {
	}
	
	/**
	 * Sub-class is expected to provide implementation to extract all required info from input JsonNode and then set all properties.
	 * 
	 * */
	abstract public void parsePlace(JsonNode placeNode) throws Exception;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getTypes() {
		return types;
	}

	public void setTypes(Set<String> types) {
		this.types = types;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double lat) {
		this.latitude = lat;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double lng) {
		this.longitude = lng;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Place) {
			Place that = (Place) obj;
			if (this.toString().equals(that.toString())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder strBulder = new StringBuilder();
		strBulder.append("Name=" + this.name + " ");
		strBulder.append("Id=" + this.id + " ");
		strBulder.append("Latitude=" + this.latitude + " ");
		strBulder.append("Longitude=" + this.longitude + " ");
		
		List<String> typeList = new ArrayList<>(this.types);
		//Order of types in list should not affect Object#equals method.
		Collections.sort(typeList);
		StringBuilder typesAsString = new StringBuilder();
		for (String s : typeList) {
			typesAsString.append(s).append(",");
		}
		strBulder.append("Types=" + typesAsString.toString());
		
		return strBulder.toString();
	}
}
