package model;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class GitApi {
	/*
	 * This class is responsible for the request and response messages
	 */
	
	private RequestDispatcher dispatcher;
	private GitUserFactory factory;
	
	public GitApi(RequestDispatcher dispatcher){
		this.dispatcher=dispatcher;
		factory=new GitUserFactory(this);
	}
	
	public GitUserFactory getFactory(){
		return factory;
	}
	/*
	 * Given api.github.com/users/:username -> retrieve the JSON response
	 */
	
	public String getUserInformation(String userUrl){
		return this.dispatcher.fetch(userUrl,"GET");
	}
	
	/*
	 * Given a User, retrieve a list of follower usernames
	 */
	
	public String[] getListOfFollowerUsernames(GitUser user){
		//an array of usernames (size=number of followers)
		String [] followers=new String [user.getFollowers()];
		//Getting raw json response and converting to array of json objects
		String response= this.dispatcher.fetch(user.getFollowerUrl(), "GET");
		JSONArray jsonResponse=new JSONArray(response);
		
		//Retrieving usernames
		for (int i=0; i<user.getFollowers(); i++){
			try{
				followers[i]=(String)jsonResponse.getJSONObject(i).get("login");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return followers;
	}
	
	
	public String follow(GitUser user){
		String url=new StringBuilder("https://api.github.com/user/following/").append(user.getUsername()).toString();
		return dispatcher.fetch(url, "PUT");
	}
	

}
