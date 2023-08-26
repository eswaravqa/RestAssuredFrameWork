package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //This annotation is responsible for defining User object in builder pattern in POST call. 
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FakeStoreUser {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("price")
	private Double price;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("image")
	private String image;
	
	@JsonProperty("category")
	private String category;
	

}
