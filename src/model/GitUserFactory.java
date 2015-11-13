package model;
import java.io.IOException;

import org.json.JSONObject;

public class GitUserFactory {
	GitApi gitApi;
	
	public GitUserFactory(GitApi gitApi){
		this.gitApi=gitApi;
	}
	
	/*
	 * Given a github username, construct a GitUser object
	 */
	public GitUser getUser(String username){
		GitUser user=new GitUser(username);
		String response=gitApi.getUserInformation(user.getUserUrl());
		JSONObject jsonResponse=new JSONObject(response);
		
		//Updating UserInformation using the json response
		user.setFollowers((int)jsonResponse.get("followers"));
		user.setFollowing((int)jsonResponse.get("followers"));
		
		return user;
	}
	
}
