package com.qa.gorest.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIConstants;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.FakeStoreUser;

public class FakeStoreAPICreateProduct extends BaseTest{
	
	@BeforeMethod
	public void restClientSetUp() {
		restClient = new RestClient(prop, baseURI);		
	}
	
	@DataProvider
	public Object[][] getUserBodyTestData(){
		return new Object[][] {
			{"Mens Glasses", 4.5, "New Mens Glasses", "https://i.pravatar4.cc", "Mens accessories" }
			//{"Mens Tie", 34.5, "New Mens Tie", "https://i.pravatar2.cc", "Mens accessories" },
			//{"Mens Blazer", 104.5, "New Mens Blazer", "https://i.pravatar3.cc", "Mens accessories" },
			//{"Mens Shirt", 54.5, "New Mens Shirt", "https://i.pravatar4.cc", "Mens accessories" }
		};		
	}
	
	@Test(dataProvider = "getUserBodyTestData")
	public void addAProduct(String title, Double price, String description, String image, String category) {
		System.out.println("******Add a Product to the Fake Store - Method starts******* ");
			
		FakeStoreUser fakeStoreUser = new FakeStoreUser(title, price, description, image, category );
		
		Integer productId = restClient.post(APIConstants.FAKESTORE_ENDPOINT, "json", fakeStoreUser, false, true)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode()).extract().path("id");
		
		//Place holder to perform get validations 
		//RestClient restClient = new RestClient(prop, baseURI);
		
		System.out.println("******Get Product Details to validate- Get call starts*******");
		restClient.get(APIConstants.FAKESTORE_ENDPOINT+"/"+productId, false, true)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode());
			
		
		
	}
}
