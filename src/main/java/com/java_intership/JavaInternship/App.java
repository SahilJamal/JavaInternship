package com.java_intership.JavaInternship;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class App 
{
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	
	private static final String API_KEY = "142cf43e95f6f0839c9e984cf674dd96"; 

	public void getdata(String city) {
        
        double temp = getWheactherData(city, API_KEY);
        
        if(temp > 20) {
			
        	logger.info("Deployment is done because temp is greater than 20");
        	
        	//Deployment logic
        }else {
        	logger.info("Deployment is not done because temp is less than 20");
        }
        
    }
    
    private static double getWheactherData(String city , String Api_code) {
    	
    	String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+Api_code+"&units=metric";
    	
    	HttpClient httpClient = HttpClient.newHttpClient();
    	
    	HttpRequest responce = HttpRequest.newBuilder()
    			.uri(URI.create(apiUrl))
    			.build();
    	
    	double temperature = 0.0;
    	
    	try {
            
            HttpResponse<String> httpResponse = httpClient.send(responce, HttpResponse.BodyHandlers.ofString());
            
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(httpResponse.body());

            JsonNode temperatureNode = jsonNode.path("main").path("temp");
            temperature = temperatureNode.asDouble();

        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	return temperature;
    }
}

