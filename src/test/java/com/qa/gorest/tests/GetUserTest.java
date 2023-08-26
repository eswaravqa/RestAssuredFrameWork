package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;


public class GetUserTest extends BaseTest {	
	
	@BeforeMethod
	public void resetRestClient() {
		restClient = new RestClient(prop, baseURI);		
	}
	
	@DataProvider
	public Object[][] getUserIdTestData() {
		return new Object[][] { {4770428}, {4770426}, {4770425}, {4770380} };
	}
	@DataProvider
	public Object[][] getUserParamsTestData(){
		return new Object[][] {
			{"Rama", "active"},
			{"Siva", "active"},
			{"Venkata", "active"},
			{"Naveen", "active"}	
		};		
	}
	
	@Test(enabled = true, priority = 1)
	public void getAllUsersTest() {	
		System.out.println("*****Get All Users Test Method Starts*****");		
		restClient.get(APIConstants.GOREST_ENDPOINT, true, true)
		.then().log().all()
		.assertThat().statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test(dataProvider = "getUserIdTestData", priority = 2)
	public void getUsersWithUserIdTest(Integer userId) {	
		System.out.println("*****Get Users with User Id Test Method Starts*****");		
		restClient.get(APIConstants.GOREST_ENDPOINT+"/"+userId, true, true)	
			.then().log().all()
				.assertThat().statusCode(APIHttpStatus.OK_200.getCode())
					.assertThat().body("id", equalTo(userId));
	}
	
	@Test(dataProvider="getUserParamsTestData", priority = 3)
	public void getUserWithQueryParamsTest(String name, String status) {	
		System.out.println("*****Get Users with Query Parameters Test Method Starts*****");		
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("name", name);
		queryParams.put("status", status);
		
		restClient.get(APIConstants.GOREST_ENDPOINT, queryParams, true, null, true)
				.then().log().all()
					.assertThat().statusCode(APIHttpStatus.OK_200.getCode());						
							
	}

}
