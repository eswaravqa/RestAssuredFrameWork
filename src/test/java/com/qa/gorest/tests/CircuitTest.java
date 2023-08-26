package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

public class CircuitTest extends BaseTest {
	
	@BeforeMethod
	public void resetRestClient() {
		restClient = new RestClient(prop, baseURI);		
	}
	
	//Old code before writing Json Path Validator 
	/*
	@DataProvider
	public Object[][] getYearTestData(){
		return new Object[][] {
			{2017},
			{2018}						
		};		
	}
	
	@Test(dataProvider = "getYearTestData")
	public void getAllCircuitTest(Integer year) {	
		System.out.println("*****Get All Circuits information year:2017 & 2018 Test Method Starts*****");	
		restClient.get(CIRCUITS_ENDPOINT+"/"+year+"/circuits.json", false, true)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode());
	}*/
	
	//New code with Json Path validator 
	@Test
	public void getAllCircuitTest() {	
		System.out.println("*****Get All Circuits information year:2017 Test Method Starts*****");	
		Response circuitResponse = restClient.get(APIConstants.CIRCUITS_ENDPOINT+"/2017/circuits.json", false, true);
		int statusCode = circuitResponse.statusCode();
		Assert.assertEquals(statusCode, APIHttpStatus.OK_200.getCode());
		
		JsonPathValidator js =new JsonPathValidator();
			List<String> countryList = js.readList(circuitResponse, "$.MRData.CircuitTable.Circuits[?(@.circuitId == 'shanghai')].Location.country");
			System.out.println(countryList);
			Assert.assertTrue(countryList.contains("China"));
		
	}
}
