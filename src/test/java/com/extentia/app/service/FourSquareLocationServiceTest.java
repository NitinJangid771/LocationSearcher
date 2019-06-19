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
import com.extentia.app.service.service_provider.foursquare.FourSquareLocationServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FourSquareLocationServiceTest {
	
	@Spy
	FourSquareLocationServiceImpl fourSquareLocationService = FourSquareLocationServiceImpl.getInstance();
	
	@Test
	public void createSerachURLTest() {
		String searchName = "dummyPlace";
		String expectedURL = "https://api.foursquare.com/v2/venues/search?client_id=SKBX52BKBEBW5BMLPFV0GB2VLZKWWJB1HMJU4B4R544ZBNSW&client_secret=RSIXSZFYNMSRNLL5WQGONRY3CKBAKCOL23ZJMTHLJSJVC1BB&intent=global&v=20190606&limit=10&query=dummyPlace";
		String searchURL = fourSquareLocationService.createSerachURL(searchName);
		assertEquals("FourSquare's create serach URL test failed", expectedURL, searchURL);
	}
	
	@Test
	public void createFilteredSerachURLTest() {
		String expectedURL = "https://api.foursquare.com/v2/venues/explore?client_id=SKBX52BKBEBW5BMLPFV0GB2VLZKWWJB1HMJU4B4R544ZBNSW&client_secret=RSIXSZFYNMSRNLL5WQGONRY3CKBAKCOL23ZJMTHLJSJVC1BB&intent=browse&v=20190606&limit=10&ll=20.5937,78.9629&query=dummyType";
		String filteredsearchURL = fourSquareLocationService.createFilteredSerachURL(TestUtil.getTestFilter());
		assertEquals("FourSquare's create filtered serach URL test failed", expectedURL, filteredsearchURL);
	}
	
	@Test
	public void searchTest() throws Exception {
		String searchName = "dummyPlace";
		List<Place> expectedPlaces = TestUtil.getTestPlaces(1);
		when(fourSquareLocationService.getAndParsePlaces(anyString(), anyInt())).thenReturn(expectedPlaces);
		List<Place> actualPlaces = fourSquareLocationService.search(searchName);

		assertTrue("FourSquare's search failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
	}
	
	@Test
	public void filteredSerachTest() throws Exception {
		List<Place> expectedPlaces = TestUtil.getTestPlaces(10);
		when(fourSquareLocationService.getAndParsePlaces(anyString(), anyInt())).thenReturn(expectedPlaces);
		List<Place> actualPlaces = fourSquareLocationService.filteredSearch(TestUtil.getTestFilter());

		assertTrue("FourSquare's filtered search failed", TestUtil.isSamePlaces(expectedPlaces, actualPlaces));
	}
	
}
