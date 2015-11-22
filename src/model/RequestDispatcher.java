package model;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class RequestDispatcher {
	private String accessToken;
	
	public RequestDispatcher(String accessToken){
		this.accessToken=new StringBuilder("?access_token=").append(accessToken).toString();
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
			
			//Storing the data in buffer
			DataInputStream response=new DataInputStream(request.getInputStream());
			while((ch=response.read())!=-1) outputBuffer.append((char)ch);
			response.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return outputBuffer.toString();
	}
}
