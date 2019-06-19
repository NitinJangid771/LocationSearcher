package com.extentia.app.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.extentia.app.service.model.FourSquarePlace;
import com.extentia.app.service.model.GooglePlace;
import com.fasterxml.jackson.databind.JsonNode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceTest {
	
	@Autowired
	FourSquarePlace fourSquarePlace;

	@Autowired
	GooglePlace googlePlace;

	@Test
	public void testGooglePlace() throws Exception {
		String expected = "Name=DummyPlace Id=bhsdjfbgdjshf Latitude=20.5937 Longitude=78.9629 Types=dummyType,";
		JsonNode input = TestUtil.getGooglePlaceJson();
		googlePlace.parsePlace(input);
		
		assertEquals("Google place parse failed", expected, googlePlace.toString());
	}
	
	@Test
	public void testFourSquarePlace() throws Exception {
		String expected = "Name=DummyPlace Id=bhsdjfbgdjshf Latitude=20.5937 Longitude=78.9629 Types=dummyType,";
		JsonNode input = TestUtil.getFourSquarePlaceJson();
		fourSquarePlace.parsePlace(input);
		
		assertEquals("Four Square place parse failed", expected, fourSquarePlace.toString());
	}
}
