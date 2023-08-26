package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;

public class ReqResAPITest extends BaseTest {

	@BeforeMethod
	public void resetRestClient() {
		restClient = new RestClient(prop, baseURI);
	}
	
	@DataProvider
	public Object[][] getUserIdTestData(){
		return new Object[][] {	{1}, {2}, {6}, {7}, {8}, {9} };		
	}
	@DataProvider
	public Object[][] getPageTestData(){
		return new Object[][] { {1}, {2}, {3},	{4} };		
	}

	@Test(dataProvider = "getUserIdTestData")
	public void getAllReqResWithUserIdTest(Integer userId) {
		System.out.println("*****Get All Request Response Test Method Starts*****");	
		restClient.get(APIConstants.REQRES_ENDPOINT+"/"+userId, false, true)
			.then().log().all()
				.assertThat()
					.statusCode(APIHttpStatus.OK_200.getCode());
	}
	
	@Test(dataProvider = "getPageTestData")
	public void getAllReqResPagewiseTest(Integer page) {
		System.out.println("*****Get All Request Response Test Method Starts*****");	
		
		
		Map<String, Object> queryParams = new HashMap<String, Object>();
		queryParams.put("page", page);
		
		restClient.get(APIConstants.REQRES_ENDPOINT, queryParams, true, null, true)
			.then().log().all()
				.assertThat()
					.statusCode(APIHttpStatus.OK_200.getCode());
	}

}
