package com.extentia.app.service;

import static com.extentia.app.service.TestUtil.dummyCategories;
import static com.extentia.app.service.TestUtil.dummyLat;
import static com.extentia.app.service.TestUtil.dummyLng;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.extentia.app.service.controller.MainLocationController;
import com.extentia.app.service.model.Filter;
import com.extentia.app.service.model.Place;
import com.extentia.app.service.service_provider.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebServiceTest {
	
	@InjectMocks
    public MainLocationController locationWS;

	@Mock
	LocationService searchServiceProvider;
	@Mock
	LocationService filteredSearchServiceProvider;

	@Test
	public void testSearch_CorrectInput() throws Exception {
		String searchInput = "dummyPlace";
		List<Place> expectedPlaces = TestUtil.getTestPlaces(1);
		when(searchServiceProvider.search(searchInput)).thenReturn(expectedPlaces);
		List<Place> actualPlaces = locationWS.search(searchInput);
		
		assertTrue("Search with correct input failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
//		assertThat("Search with correct input not working", actualPlaces, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedPlaces));
	}
	
	@Test
	public void testSearch_IncorrectInput() throws Exception {
		String searchInput = null;
		List<Place> expectedPlaces = TestUtil.getTestPlaces(0);
		when(searchServiceProvider.search(searchInput)).thenReturn(expectedPlaces);
		List<Place> actualPlaces = locationWS.search(searchInput);
		
		assertTrue("Search with incorrect input failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
	}
	
	@Test
	public void testFilteredSearch_CorrectInput() throws Exception {
		List<Place> expectedPlaces = TestUtil.getTestPlaces(10);
		when(filteredSearchServiceProvider.filteredSearch(any(Filter.class))).thenReturn(expectedPlaces);
		when(filteredSearchServiceProvider.getAllCategories()).thenReturn(new HashMap<String, String>());
		List<Place> actualPlaces = locationWS.filteredSearch(dummyLat, dummyLng, dummyCategories);
				
		assertTrue("Filtered Search with correct input failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testFilteredSearch_IncorrectInput() throws Exception {
		locationWS.filteredSearch(dummyLat, dummyLng, new ArrayList<String>());
		
		fail("Filtered Search with incorrect input failed");
	}
}
