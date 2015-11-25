package com.imraazrally.gitbot.controller;

import com.imraazrally.gitbot.model.GitApi;
import com.imraazrally.gitbot.model.GitUser;

public class Follow {
	
	public static void follow(String args[], GitApi gitApi, GitUser myAccount){
		GitUser baseParent=gitApi.getFactory().getUser(args[3]);
		int levels=Integer.parseInt(args[4]);
		int interval=Integer.parseInt(args[5]);
		//Recursively treverse followers, and their followers, and so on.
		followUsersAtDepthN(myAccount,baseParent,gitApi,levels, interval); 
	}
	
	private static void followUsersAtDepthN(GitUser myAccount, GitUser parent, GitApi gitApi, int level, int interval){
		if(level<1)return;
				
		//Follow each follower from current level, and GET their followers as well (recusively).
		for(String username: gitApi.getListOfFollowerUsernames(parent)){
			
			GitUser user=gitApi.getFactory().getUser(username);	
			//------------------Follow!------------------
			if(gitApi.isFollowing(myAccount, user))continue;
			
			gitApi.follow(user);
			
			try{
				Thread.sleep(interval*1000);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//-------------------------------------------
			followUsersAtDepthN(myAccount,user,gitApi,level-1,interval);
		}	
	}
	
	public static void unfollow(GitUser parent, GitApi gitApi){
		for (String username: gitApi.getListOfFollowingUsernames(parent)){
			GitUser user=gitApi.getFactory().getUser(username);
			//If the user is NOT following the parent, we unfollow.
			if(!gitApi.isFollowing(user,parent)) gitApi.unfollow(user);
		}
	}
}
