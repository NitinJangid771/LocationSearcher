# LocationSearcher
Loaction Sercher is an application which searches places and capable of filtering places based on certain criteria.

## Technology stack used inside
- RESTful Web Services (Java)
- Spring Boot
- Maven
- Junit with Mockito

## Basic idea behind this application
Create a single Location-Wrapper for all Location Service Providers, This creates abstraction between the Location API and the application. 
Using this the Backend + Frontend becomes free from the specific Location Providers formats.
Currently Location Wrapper has implementation for two Location Place API's
1. Google Places API
2. FourSquare Places API

### How to use this application for search
- Create URL for GET request on "/search" and which contains "name" parameter 
- name is the location/place for which search needs to be performed
- The response conatins the request loaction details.

### How to use this application for filtered search
- Create URL for GET request on "/filteredsearch" and which contains "lat", "lng" and "categories" as parameters 
- lat/lng are latitude and longitude for where the search should be performed
- categories is the filter criteria for which search needs to be performed
- The response conatins the filtered loactions.

## Architectural Details
Key important Classes/Intefaces involved are:
1. MainLocationController (RestController)
2. LocationService (Interface)
3. ResponseParser (Interface)
4. Place (Abstract Class)

#### MainLocationController (RestController)
Conatins two endpoints one for Search and other for Filtered Search.
MainLocationController internally uses LocationService to perform search operations.
Returs List Place as a Json response.

#### LocationService (I)
LocationService is an abstraction provided so as the application can be decoupled from underneath Loaction Service Provide API.
LocationService has an abstarct imlementation class URLTypeLocationServices.
URLTypeLocationServices further two implementaions GoogleLocationServiceImpl and FourSquareLocationServiceImpl.
LocationService implementaions internally use ResponseParser to parse response specific to every palce API.

#### ResponseParser (I)
ResponseParser is introduced to parse response which is fetched from Places API.
Response parser has two Implementations, GoogleReponseParser and FourSquareResponseParser
ResponseParser parses the response and fetches Place JsonNode and forwards this to Place (Abstract Class) to parse Place from this JsonNode.

#### Place (Abstract class)
Represents single Location/Place.
As every API returns places specific thier format therefore this class needs implementation specific to the Location API.
Place has to concreate implementations GooglePlace and FourSquarePlace.
The UI interacts with this Place Object this way an abstarction is created between UI and the Location Service API. 
