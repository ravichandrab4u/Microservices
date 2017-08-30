package com.gds;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
@RestController
public class CacheServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacheServiceApplication.class, args);
	}
	
	/*@RequestMapping("/cache")
	public String data() {
		try {
			Thread.currentThread().sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Data From Cache Service .... ";
	}*/
	
	@RequestMapping("/cache")
	public @ResponseBody String data(@RequestParam String identifier) {
		String responseString = null;
		Map<String,String> jsonResponse = new HashMap<String,String>();
		try{
			if("123456".equals(identifier)){
				jsonResponse.put("Data", "Data From Cache Service .... ");
			}else{
				jsonResponse.put("Data", "Data Unavailable");
			}
		}catch(Exception e){
			jsonResponse.put("Error", "Error Processing Request");
		}finally {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			responseString = gson.toJson(jsonResponse);
		}
		return responseString;
	}
	
}
