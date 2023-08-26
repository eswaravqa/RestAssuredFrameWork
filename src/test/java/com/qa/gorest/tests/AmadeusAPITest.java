package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AmadeusAPITest extends BaseTest {
	
	private String accessToken;
	
	@Parameters({"baseURI", "grantType", "clientId", "clientSecret"})
	@BeforeMethod 
	public void flightAPITSetup(String baseURI, String grantType, String clientId, String clientSecret) {
		restClient = new RestClient(prop, baseURI);
		accessToken = restClient.getAccessToken(APIConstants.AMADEUS_OATHTOKEN_ENDPOINT, grantType, clientId, clientSecret);		
	}
	
	@DataProvider
	public Object[][] getParamsTestData(){
		return new Object[][] {
			{"PAR", 200},
			{"LON", 100}			
		};		
	}
	@Test(dataProvider = "getParamsTestData")
	public void getFlightInfoTest(String origin, Integer maxPrice ) {
	
	RestClient	restClientFlightBooking = new RestClient(prop, baseURI);
	System.out.println("************Get Filight Booking Info method starts*****************");
	
	Map<String, Object> queryParams = new HashMap<String, Object>();
	queryParams.put("origin", origin);
	queryParams.put("maxPrice", maxPrice);
	
	Map<String, String> headersMap = new HashMap<String, String>();
	headersMap.put("Authorization", "Bearer "+accessToken);
	
	Response flightDataResponse = restClientFlightBooking.get(APIConstants.AMADEUS_FLIGHTBOOKING_ENDPOINT, queryParams, false, headersMap, true)
			.then().log().all()
				.assertThat()
					.statusCode(APIHttpStatus.OK_200.getCode())
						.and()
							.extract()
								.response();		
	
	JsonPath js = flightDataResponse.jsonPath();
	String type = js.get("data[0].type");
	System.out.println("Type in the response is :  "+type); //flight-destinations
	
	}
		
	
		

}
