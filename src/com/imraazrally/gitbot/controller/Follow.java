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
				System.out.printf("...");
			}
			
			followersInCurrentLevel.add(user);
		}	
		
		//Having followed all the users at current level, we move onto the next level down the tree
		for (GitUser user: followersInCurrentLevel)
			followUsersAtDepthN(myAccount,user,gitApi,level-1,interval);
	}
	
	public static void unfollow(GitUser user, GitApi gitApi){
		
		int MAX=30;
		
		/*
		 *		A GET request to retrieve all the users followed by 'user'
		 *	    will only result in at most 30 users in the request message at one time.
		 *		So we have to group the total count of users into groups of 30.
		 *		
		 *		In otherwords: we need to unfollow in groups of 30.
		 * 
		 */
		
		
		// Total number of users that we are following
		int followingCount=user.getFollowing();
		
		//Because the MAX limit is 30, we subdevide the process into smaller chunks
		int iterations=followingCount/MAX;
	
		for(int i=0; i<iterations; i++){	
			for (String username: gitApi.getListOfFollowingUsernames(user,MAX)){
				GitUser following=gitApi.getFactory().getUser(username);
				//If the user is NOT following the parent, we unfollow.
				if(!gitApi.isFollowing(following,user)) gitApi.unfollow(following);
			}
		}
			
	}
}
