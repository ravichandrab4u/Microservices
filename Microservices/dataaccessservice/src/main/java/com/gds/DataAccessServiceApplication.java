package com.gds;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringBootApplication
@RestController
public class DataAccessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataAccessServiceApplication.class, args);
	}
	
	/*@RequestMapping("/dataaccess")
	public String data() {
		try {
			Thread.currentThread().sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "Data From Data Access .... ";
	}*/
	
	@RequestMapping("/dataaccess")
	public @ResponseBody String data(@RequestParam String identifier) {
		String responseString = null;
		Map<String,String> jsonResponse = new HashMap<String,String>();
		try{
			if("123456789".equals(identifier)){
				jsonResponse.put("Data", "Data From Data Access Service .... ");
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
