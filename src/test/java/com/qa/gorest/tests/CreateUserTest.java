package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.GoRestUser;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTest extends BaseTest {
	

	@BeforeMethod
	public void resetRestClient() {
		restClient = new RestClient(prop, baseURI);		
	}
	
	@DataProvider
	public Object[][] getUserTestData(){
		return new Object[][] {
			{"Sita", "female", "active"},
			{"ParvathiDevi", "female", "active"},
			{"Lakshmi", "female", "active"}			
		};		
	}
	
	@Test(dataProvider = "getUserTestData")
	//1.Post
	public void createNewUserTest(String name, String gender, String status) {
		System.out.println("*****Create New User Test Method Starts*****");	
		GoRestUser user = new GoRestUser(name, StringUtils.getRandomEmailId(), gender, status);		
		int UserId =	restClient.post(APIConstants.GOREST_ENDPOINT, "json", user, true, true)
						.then().log().all()
							.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
								.extract().path("id");
	
	System.out.println("User id: "+ UserId);
	
	//Imp:Note
	//Even if we comment the following rest client declaration still it will work.
	//It is due to poor design of Go Rest API
	//But the best practice is always declare  new Rest Client for get call. 
	//Otherwise the pervious parameters, headers will carry to new Rest Client as well and fail the test case
	//
	RestClient restClient = new RestClient(prop, baseURI);	
	
	//2.GET		
		restClient.get(APIConstants.GOREST_ENDPOINT+"/"+UserId, true, true)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode())
					.assertThat()
						.body("id", equalTo(UserId));
		System.out.println("end of the test");
	}
	

	
		
	}

