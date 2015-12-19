package com.imraazrally.gitbot.controller;

import java.util.ArrayList;
import java.util.List;

import com.imraazrally.gitbot.model.GitApi;
import com.imraazrally.gitbot.model.GitUser;

public class Follow {
	private static int MAX=30;
	
	/*
	 *		A GET request to retrieve all the users followed/following by 'user'
	 *	    will only result in at most 30 results in the request message at one time. This limitation is imposed by GitHub's API.
	 *		So we have to group the total count of results into groups of 30.
	 *		
	 *		In otherwords: we need to follow/unfollow in groups of 30. In terms of pages,
	 *		
	 *		Page1: 0-29 users
	 *		Page2: 30-59 users
	 * 		Page3: 60-89 users
	 * 		...
	 * 		...
	 */
	

	public static void followUsersAtDepthN(GitUser myAccount, GitUser parent, GitApi gitApi, int level, int interval){
		if(level<1)return;
		List<GitUser> followersInCurrentLevel=new ArrayList<GitUser>();
		
		//Because there is a limit of MAX users per page, we subdevide the process into smaller chunks of MAX
		int pageCount=parent.getFollowers()/MAX;
		
		for (int page=0; page<pageCount+1; page++){
			//Following All followers in current level. (Bredth-first)
			for(String username: gitApi.getListOfFollowerUsernames(parent,MAX,page)){
				
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
		}
		
		//Having followed all the users at current level, we move onto the next level down the tree
		for (GitUser user: followersInCurrentLevel)
			followUsersAtDepthN(myAccount,user,gitApi,level-1,interval);
	}
	
	public static void unfollow(GitUser user, GitApi gitApi){
		
		// Total number of users that we are following
		int followingCount=user.getFollowing();
		
		//Because the MAX limit is 30, we subdevide the process into smaller chunks
		int pageCount=followingCount/MAX;
	
		for(int page=0; page<pageCount+1; page++){	
			for (String username: gitApi.getListOfFollowingUsernames(user,MAX)){
				GitUser following=gitApi.getFactory().getUser(username);
				//If the user is NOT following the parent, we unfollow.
				if(!gitApi.isFollowing(following,user))gitApi.unfollow(following);
			}
		}
			
	}
}
