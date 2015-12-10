package com.imraazrally.gitbot.controller;

import java.util.ArrayList;
import java.util.List;

import com.imraazrally.gitbot.model.GitApi;
import com.imraazrally.gitbot.model.GitUser;

public class Follow {
	
	public static void followUsersAtDepthN(GitUser myAccount, GitUser parent, GitApi gitApi, int level, int interval){
		if(level<1)return;
		List<GitUser> followersInCurrentLevel=new ArrayList<GitUser>();
				
		//Following All followers in current level. (Bredth-first)
		for(String username: gitApi.getListOfFollowerUsernames(parent)){
			
			GitUser user=gitApi.getFactory().getUser(username);	
			//------------------Follow!------------------
			if(gitApi.isFollowing(myAccount, user))continue;
			
			gitApi.follow(user);
			
			try{
				Thread.sleep(interval*1000);
			}catch(Exception e){
				System.out.println("Wait...");
			}
			
			followersInCurrentLevel.add(user);
		}	
		
		//moving to next level.
		for (GitUser user: followersInCurrentLevel)
			followUsersAtDepthN(myAccount,user,gitApi,level-1,interval);
	}
	
	public static void unfollow(GitUser parent, GitApi gitApi){
		for (String username: gitApi.getListOfFollowingUsernames(parent)){
			GitUser user=gitApi.getFactory().getUser(username);
			//If the user is NOT following the parent, we unfollow.
			if(!gitApi.isFollowing(user,parent)) gitApi.unfollow(user);
		}
	}
}
