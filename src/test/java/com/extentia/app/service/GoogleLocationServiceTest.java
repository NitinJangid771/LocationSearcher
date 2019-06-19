package com.extentia.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.extentia.app.service.model.Place;
import com.extentia.app.service.service_provider.google.GoogleLocationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleLocationServiceTest {
	
	@Spy
	GoogleLocationServiceImpl googleLocationService = GoogleLocationServiceImpl.getInstance();
	
	@Test
	public void createSerachURLTest() {
		String searchName = "dummyPlace";
		String expectedURL = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?inputtype=textquery&key=AIzaSyAbjVICZBXrrWr6wRhHES0J4lzecwUqvnY&fields=name,place_id,types,geometry/location&input=dummyPlace";
		String searchURL = googleLocationService.createSerachURL(searchName);
		assertEquals("Google's create serach URL test failed", expectedURL, searchURL);
	}
	
	@Test
	public void createFilteredSerachURLTest() {
		String expectedURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=100030&location=20.5937,78.9629&type=dummyType&key=AIzaSyAbjVICZBXrrWr6wRhHES0J4lzecwUqvnY";
		String filteredsearchURL = googleLocationService.createFilteredSerachURL(TestUtil.getTestFilter());
		assertEquals("Google's create filtered serach URL test failed", expectedURL, filteredsearchURL);
	}
	
	@Test
	public void searchTest() throws Exception {
		String searchName = "dummyPlace";
		List<Place> expectedPlaces = TestUtil.getTestPlaces(1);
		when(googleLocationService.getAndParsePlaces(anyString(), anyInt())).thenReturn(expectedPlaces);
		List<Place> actualPlaces = googleLocationService.search(searchName);

		assertTrue("Google's search failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
	}
	
	@Test
	public void filteredSerachTest() throws Exception {
		List<Place> expectedPlaces = TestUtil.getTestPlaces(10);
		when(googleLocationService.getAndParsePlaces(anyString(), anyInt())).thenReturn(expectedPlaces);
		List<Place> actualPlaces = googleLocationService.filteredSearch(TestUtil.getTestFilter());

		assertTrue("Google's filtered search failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
	}
	
}
