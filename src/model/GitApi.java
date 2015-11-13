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
	
	public GitApi(RequestDispatcher dispatcher){
		this.dispatcher=dispatcher;
	}
	
	/*
	 * Given api.github.com/users/:username -> retrieve the JSON response
	 */
	
	public String getUserInformation(String userUrl){
		return this.dispatcher.get(userUrl);
	}
	
	/*
	 * Given a User, retrieve a list of follower usernames
	 */
	
	public String[] getListOfFollowerUsernames(GitUser user){
		//an array of usernames (size=number of followers)
		String [] followers=new String [user.getFollowers()];
		//Getting raw json response and converting to array of json objects
		String response= this.dispatcher.get(user.getFollowerUrl());
		JSONArray jsonResponse=new JSONArray(response);
		
		//Retrieving usernames
		for (int i=0; i<user.getFollowers(); i++) followers[i]=(String)jsonResponse.getJSONObject(i).get("login");
		return followers;
	}
	
}
