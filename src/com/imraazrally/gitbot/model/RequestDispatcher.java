package com.imraazrally.gitbot.model;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;

public class RequestDispatcher {
	private String accessToken;
	
	public RequestDispatcher(String accessToken){
		this.accessToken=new StringBuilder("&access_token=").append(accessToken).toString();
	}
	
	
	public String fetch(String url, String method){
		StringBuilder outputBuffer=new StringBuilder();
		try{
			HttpURLConnection request=(HttpURLConnection)new URL(url+accessToken).openConnection();
			int ch;

			//Sending the request
			request.setRequestMethod(method);
			request.setInstanceFollowRedirects(true);
			request.setDoInput(true);
			
			DataInputStream response=new DataInputStream(request.getInputStream());
			
			//Error Codes
			int responseCode=request.getResponseCode();
			
			switch(responseCode){
				case 200:
					
					while((ch=response.read())!=-1) outputBuffer.append((char)ch);
					response.close();
					break;
					
				case 403:
					
					Thread.sleep(15*60*1000);
					break;
					
				default:
					outputBuffer.append(String.valueOf(responseCode));
					break;
			}
			
			
		}catch(Exception e){
			outputBuffer.append("404");
		}
		
		return outputBuffer.toString();
	}
}
