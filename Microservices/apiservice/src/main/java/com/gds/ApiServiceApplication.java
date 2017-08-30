package com.gds;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@SpringBootApplication
@RestController
public class ApiServiceApplication {

	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
	@Value("${cache.service.url}")
	private String cacheServiceURL;
	
	@Value("${dataaccess.service.url}")
	private String dataaccessServiceURL;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiServiceApplication.class, args);
	}
	
	@RequestMapping("/api")
	public @ResponseBody String hello(@RequestParam String identifier) {
		String responseString = null;
		Map<String,String> jsonResponse = new HashMap<String,String>();
		try{
			jsonResponse = getDataFromOtherService(cacheServiceURL, identifier);
			if("Data Unavailable".equals(jsonResponse.get("Data"))){
				jsonResponse = getDataFromOtherService(dataaccessServiceURL, identifier);
			}
		}catch(Exception e){
			jsonResponse.put("Error", "Error Processing Request");
		}finally {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			responseString = gson.toJson(jsonResponse);
		}
		return responseString;
	}
	
	
	private Map<String,String> getDataFromOtherService(String url,String identifier){
		RestTemplate restTemplate = restTemplateBuilder.build();
		ResponseEntity<String> reponse = null;
		
		Map<String,String> jsonResponse = new HashMap<String,String>();
		try{
			reponse = restTemplate.exchange(url+identifier, HttpMethod.GET,null,String.class);
			jsonResponse = new Gson().fromJson(reponse.getBody(), Map.class);
		}catch(Exception e){
			jsonResponse.put("Error", "Error Processing Request");
		}
		
		return jsonResponse;
	}
	
	/*@RequestMapping("/api")
	public String hello() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		String baseUrl = "http://localhost:2222/cache?cacheKey=cacheKey";
		
		System.out.println(cacheServiceURL);
		ResponseEntity<String> reponse = 
				restTemplate.exchange(baseUrl, HttpMethod.GET,null,String.class);
		
		return reponse.getBody();
	}*/
}
