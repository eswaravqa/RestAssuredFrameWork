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
import com.qa.gorest.utils.ExcelUtil;
import com.qa.gorest.utils.StringUtils;

public class CreateUserTestWithExcelData extends BaseTest {
	
	@BeforeMethod
	public void resetRestClient() {
		restClient = new RestClient(prop, baseURI);		
	}
	
	@DataProvider
	public Object[][] getTestDatFromExcel() {
		return ExcelUtil.getTestData(APIConstants.GOREST_API_USER_TESTDATA_SHEET);
	}
	
	@Test(dataProvider = "getTestDatFromExcel")
	//1.Post
	public void createNewUserTest(String name, String gender, String status) {
		System.out.println("*****Create New User Test (with test data from excel) Method Starts*****");	
		GoRestUser user = new GoRestUser(name, StringUtils.getRandomEmailId(), gender, status);		
		int UserId =	restClient.post(APIConstants.GOREST_ENDPOINT, "json", user, true, true)
						.then().log().all()
							.assertThat().statusCode(APIHttpStatus.CREATED_201.getCode())
								.extract().path("id");
	
	System.out.println("User id: "+ UserId);
	

	RestClient restClient = new RestClient(prop, baseURI);	
	
	//2.GET		
		restClient.get(APIConstants.GOREST_ENDPOINT+"/"+UserId, true, true)
		.then().log().all()
			.assertThat()
				.statusCode(APIHttpStatus.OK_200.getCode())
					.assertThat()
						.body("id", equalTo(UserId));
	}
	

}
