package com.imraazrally.gitbot;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.imraazrally.gitbot.controller.Follow;
import com.imraazrally.gitbot.model.DbService;
import com.imraazrally.gitbot.model.GitApi;
import com.imraazrally.gitbot.model.GitUser;
import com.imraazrally.gitbot.model.GitUserFactory;
import com.imraazrally.gitbot.model.RequestDispatcher;

public class GitFollowerBot {
	
	public static void main(String [] args) throws IOException{
		
		try{
			/*  Author: Imraaz Rally - www.imraazrally.com
			 * 
			 * 
			 *  The program acheieves two things. 
			 *  1. Given a parent user, grab the followers recursively
			 *  2. Remove the weed. (unfollow users who has not followed back).
			 *
			 *
			 *
			 *  1. Suppose the user account is 'imraazrally' and accesstoken is '1234'
			 *  2. Suppose the desired parent user account is 'markzuck'
			 *  3. Suppose you want to extract users upto a depth of 10 levels down the tree
			 *  4. Suppose you want to add users only every 3 seconds.
			 *  
			 *  [Call to Follow] 
			 *  -java GitFollowerBot follow {access token}{account_username}{parent_username}{levels}{interval}
			 *  
			 *  {Example:}  java GitFollowerBot follow 1234 imraazrally markzuck 10 3
			 *  
			 *  
			 *  [Call to Unfollow]
			 *  -java GitFollowerBot unfollow {access token}{account_username} 
			 *  
			 *  
			 *  {Example:} java GitFollowerBot unfollow 1234 imraazrally
			 */
			
			String PROGRAM_MODE=args[0].toLowerCase();
			String ACCESS_TOKEN=args[1];
			String USERNAME=args[2];
			
			/*
			 *  Retreiving input from STDIN to determine whether to follow/unfollow
			 */
			
			if(!PROGRAM_MODE.equals("follow") && !PROGRAM_MODE.equals("unfollow"))return;
			if(ACCESS_TOKEN==null || USERNAME==null) return;
					
			/*
			 *	GitApi contains the core functionality 
			 *  The RequestDispatcher does the HTTP Communication 
			 */
			
			GitApi gitApi=new GitApi(
								//Passing the Access Token to Request Dispatcher;
								new RequestDispatcher(ACCESS_TOKEN)							
						   );

			
			/*
			 *  The Main Account [Yours]
			 */
			GitUser myAccount=gitApi.getFactory().getUser(USERNAME);
			
			
			
			if(PROGRAM_MODE.equals("follow")){
				GitUser baseParent=gitApi.getFactory().getUser(args[3]);
				int levels=Integer.parseInt(args[4]);
				int interval=Integer.parseInt(args[5]);
				//Recursively treverse followers, and their followers, and so on.
				Follow.followUsersAtDepthN(myAccount,baseParent,gitApi,levels, interval); 
			}
			
			
			if(PROGRAM_MODE.equals("unfollow")){
				Follow.unfollow(myAccount,gitApi);
			}
			
			
			
		}catch(Exception e){
			System.out.println("\n\nPlease Check Your Arguments");
			System.out.println("eg: follow   {access token}{account_username}{parent_username}{levels}{interval}");
			System.out.println("eg: unfollow {access token}{account_username} \n\n");
		}
		
		System.out.println("Thank you for using GitFollower! My name is Imraaz Rally! www.imraazrally.com");
	}
	
	
}
