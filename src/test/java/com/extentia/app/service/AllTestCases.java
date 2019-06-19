package com.extentia.app.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	WebServiceTest.class, 
	GoogleLocationServiceTest.class,
	FourSquareLocationServiceTest.class,
	PlaceTest.class
})
public class AllTestCases {

	
}
