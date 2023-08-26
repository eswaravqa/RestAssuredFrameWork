package com.qa.gorest.client;

import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameworkException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {
	
//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "d9087f8fe361cc113c178208d30189fb59ee0cb58522e394292c88f798fc8d4a";

	private RequestSpecBuilder specBuilder;
	
	private Properties prop;
	private String baseURI;
	private boolean isAuthorizationHeaderAdded = false;
	

	public RestClient(Properties prop, String baseURI) {
		specBuilder = new RequestSpecBuilder();	
		this.prop = prop;
		this.baseURI = baseURI;
	}
	public void addAuthorizationHeader() {
		if(!isAuthorizationHeaderAdded) {
		specBuilder.addHeader("Authorization", "Bearer "+ prop.getProperty("tokenId"));
		isAuthorizationHeaderAdded = true;
		}
	}
	
	private void setRequestContentType(String contentType) {
		switch (contentType.toLowerCase()) {
		case "json":
			specBuilder.setContentType(ContentType.JSON);
			break;
		case "xml":
			specBuilder.setContentType(ContentType.XML);
			break;
		case "text":
			specBuilder.setContentType(ContentType.TEXT);
			break;
		case "multipart":
			specBuilder.setContentType(ContentType.MULTIPART);
			break;	

		default:
			System.out.println("Please pass the right content type");
			throw new APIFrameworkException("INVALID CONTENT TYPE");
		}
	}
	
	private RequestSpecification createRequestSpec(boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {addAuthorizationHeader();}
		return specBuilder.build();			
	}
	private RequestSpecification createRequestSpec(Map<String, String> headersMap,boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {addAuthorizationHeader();}
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		return specBuilder.build();			
	}
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Map<String, Object> queryParams, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {addAuthorizationHeader();}
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(queryParams!=null) {
			specBuilder.addQueryParams(queryParams);
		}
		return specBuilder.build();			
	}
	private RequestSpecification createRequestSpec(Object requestBody, String contentType, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {addAuthorizationHeader();}		
		setRequestContentType(contentType);
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();			
	}
	private RequestSpecification createRequestSpec(Map<String, String> headersMap, Object requestBody, String contentType, boolean includeAuth) {
		specBuilder.setBaseUri(baseURI);
		if (includeAuth) {addAuthorizationHeader();}
		setRequestContentType(contentType);
		if(headersMap!=null) {
			specBuilder.addHeaders(headersMap);
		}
		if(requestBody!=null) {
			specBuilder.setBody(requestBody);
		}
		return specBuilder.build();			
	}
	
	//Http methods utils 	
	public Response get(String serviceUrl, boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all()
						.when().get(serviceUrl);
		}
		
		return RestAssured.given(createRequestSpec(includeAuth)).when().get(serviceUrl);
		
	}
	
	public Response get(String serviceUrl, Map<String, String> headersMap, boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, includeAuth)).log().all()
						.when().get(serviceUrl);
		}
		
		return RestAssured.given(createRequestSpec(headersMap, includeAuth)).when().get(serviceUrl);
		
	}
	
	public Response get(String serviceUrl, Map<String, Object> queryParams, boolean includeAuth, Map<String, String> headersMap, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).log().all()
						.when().get(serviceUrl);
		}
		
		return RestAssured.given(createRequestSpec(headersMap, queryParams, includeAuth)).when().get(serviceUrl);
		
	}
	public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
					.when()
						.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().post(serviceUrl);
	}
	
	public Response post(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, Map<String, String> headersMap, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, requestBody, contentType, includeAuth)).log().all()
					.when()
						.post(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, requestBody, contentType, includeAuth)).when().post(serviceUrl);
	}
	
	public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
					.when()
						.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().put(serviceUrl);
	}
	
	public Response put(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, Map<String, String> headersMap, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, requestBody, contentType, includeAuth)).log().all()
					.when()
						.put(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, requestBody, contentType, includeAuth)).when().put(serviceUrl);
	}
	public Response patch(String serviceUrl, String contentType, boolean includeAuth, Object requestBody, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).log().all()
					.when()
						.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(requestBody, contentType, includeAuth)).when().patch(serviceUrl);
	}
	
	public Response patch(String serviceUrl, String contentType, Object requestBody, boolean includeAuth, Map<String, String> headersMap, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(headersMap, requestBody, contentType, includeAuth)).log().all()
					.when()
						.patch(serviceUrl);
		}
		return RestAssured.given(createRequestSpec(headersMap, requestBody, contentType, includeAuth)).when().patch(serviceUrl);
	}
	public Response delete(String serviceUrl, boolean includeAuth, boolean log) {
		if(log) {
			return RestAssured.given(createRequestSpec(includeAuth)).log().all()
						.when().delete(serviceUrl);
		}
		
		return RestAssured.given(createRequestSpec(includeAuth)).when().delete(serviceUrl);		
	}
	
	public String getAccessToken(String serviceURL, String grantType, String clientId, String clientSecret) {
		RestAssured.baseURI = "https://test.api.amadeus.com";	
		//1. Get the access token by executing POST call 
		System.out.println("*********Get Access Token Method Starts**********");
		String accessToken = 	given().log().all()				
				.contentType(ContentType.URLENC)
				.formParam("grant_type", grantType)
				.formParam("client_id", clientId)
				.formParam("client_secret", clientSecret)
			.when()
				.post(serviceURL)
			.then()
				.assertThat()
				.statusCode(200)
				.extract().path("access_token");		
		System.out.println("Access Token : "+accessToken);
		return accessToken;
	}

	
	
	
	

}
