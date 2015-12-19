package com.imraazrally.gitbot.model;
import java.io.IOException;

import org.json.JSONObject;

public class GitUser {
	private String username;
	private int followers;
	private int following;
	private String email;
	
	public GitUser(String username){
		this.username=username;
	}
	
	public GitUser(String username, int followers, int following){
		this.username=username;
		this.followers=followers;
		this.following=following;
	}

	public String getUsername() {
		return username;
	}

	public int getFollowers() {
		return followers;
	}

	public int getFollowing() {
		return following;
	}
	
	public String getFollowerUrl(){
		return String.format("https://api.github.com/users/%s/followers?bot=GreedyGit", this.username);
	}
	
	public String getFollowingUrl(){
		return String.format("https://api.github.com/users/%s/following?bot=GreedyGit", this.username);
	}
	
	public String getUserUrl(){
		return String.format("https://api.github.com/users/%s?bot=GreedyGit", this.username);
	}
	
	public void setFollowers(int followers){
		this.followers=followers;
	}
	
	public void setFollowing(int following){
		this.following=following;
	}
	
	public String toString(){
		return getUserUrl();
	}
	
}
