package com.qa.gorest.tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.GoRestUser;
import com.qa.gorest.utils.StringUtils;

public class APISchemaValidationsTest extends BaseTest {
	
	@BeforeMethod
	public void resetRestClient() {
		restClient = new RestClient(prop, baseURI);			
	}
	
	@Test
	//1.Post
	public void createAPIUserSchemaTest() {
		System.out.println("*****Create APIUser Schema Test Method Starts*****");	
		//RestClient restClient = new RestClient(prop, baseURI);	
		GoRestUser user = new GoRestUser("Good", StringUtils.getRandomEmailId(), "male", "Active");	
		
		restClient.post(APIConstants.GOREST_ENDPOINT, "json", user, true, true)
						.then().log().all()
							.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
								.body(matchesJsonSchemaInClasspath("createuserschema.json"));
	

	}

}
