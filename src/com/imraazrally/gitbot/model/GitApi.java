package com.imraazrally.gitbot.model;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

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
		return getUsernames(user.getFollowers(),user.getFollowerUrl());
	}
	
	public String[] getListOfFollowingUsernames(GitUser user, int size){
		return getUsernames(size,user.getFollowingUrl());
	}
		
	
	/*
	 *  Checks to see whether user is following the target 
	 */
	
	public boolean isFollowing(GitUser user, GitUser target){
		
		String response=dispatcher.fetch(
											//https://api.github.com/:user/following/:target
											new StringBuilder("https://api.github.com/users/")
															 .append(user.getUsername())
															 .append("/following/")
															 .append(target.getUsername())
															 .toString()
											//Request Method			
											,"GET"
										);
		
		
		if(response.equals("204"))return true;
		return false;
		
	}
	
	
	public String follow(GitUser user){
		String url=new StringBuilder("https://api.github.com/user/following/").append(user.getUsername()).toString();
		return dispatcher.fetch(url, "PUT");
	}
	
	public String unfollow(GitUser user){
		String url=new StringBuilder("https://api.github.com/user/following/").append(user.getUsername()).toString();
		return dispatcher.fetch(url, "DELETE");
	}
	
	private String[] getUsernames(int size, String url){
		//an array of usernames (size=number of followers)
		String [] users=new String [size];
		//Getting raw json response and converting to array of json objects
		String response= this.dispatcher.fetch(url, "GET");
		JSONArray jsonResponse=new JSONArray(response);
		
		//Retrieving usernames
		for (int i=0; i<size; i++){
			try{
				users[i]=(String)jsonResponse.getJSONObject(i).get("login");
				System.out.println("OK..\n");
			}catch(Exception e){
				System.out.println("Skipped...\n");
			}
		}
		return users;
	}
		
}
