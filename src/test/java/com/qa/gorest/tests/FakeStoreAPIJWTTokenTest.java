package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.FakeStoreUsernameAndPassword;

public class FakeStoreAPIJWTTokenTest extends BaseTest {
	
	@BeforeMethod
	public void restClientSetUp() {
		restClient = new RestClient(prop, baseURI);		
	}
	
	@DataProvider
	public Object[][] getUsernameAndPasswordTestData(){
		return new Object[][] {	{"mor_2314", "83r5^_"} };		
	}
	
	@Test(dataProvider = "getUsernameAndPasswordTestData")
	public void generateJWTToken(String username, String password) {
		
			
		FakeStoreUsernameAndPassword unNpw = new FakeStoreUsernameAndPassword(username, password);
		
		String jwtToken =	restClient.post(APIConstants.FAKESTORE_JWTTOKEN_ENDPOINT, "json", unNpw, false, true)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode())
					.extract().path("token");
		
		System.out.println("JWT Token: "+ jwtToken);		
		
	}
	


}
