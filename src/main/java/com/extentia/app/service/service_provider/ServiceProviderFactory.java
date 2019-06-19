package com.extentia.app.service.service_provider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.extentia.app.service.service_provider.foursquare.FourSquareLocationServiceImpl;
import com.extentia.app.service.service_provider.google.GoogleLocationServiceImpl;

@Configuration
public class ServiceProviderFactory {
	
	@Bean("googleServiceProvider")
	public static LocationService getGooglePlacesImpl() {
		return GoogleLocationServiceImpl.getInstance();
	}
	
	@Bean("fourSquareServiceProvider")
	public static LocationService getFourSquarePlacesImpl() {
		return FourSquareLocationServiceImpl.getInstance();
	}
	
}
