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
		s
		try{
			String PROGRAM_MODE=args[0].toLowerCase();
			String ACCESS_TOKEN=args[1];
			String USERNAME=args[2];
			
			if((!PROGRAM_MODE.equals("follow") && !PROGRAM_MODE.equals("unfollow")) && !PROGRAM_MODE.equals("commit"))return;
			if(ACCESS_TOKEN==null || USERNAME==null) return;
					
			GitApi gitApi=new GitApi(
										//Passing the Access Token to Request Dispatcher;
										new RequestDispatcher(ACCESS_TOKEN)							
						   );

			//Parent User from which we will extract all the followers recursively
			GitUser myAccount=gitApi.getFactory().getUser(USERNAME);
			
			if(PROGRAM_MODE.equals("follow")) 	Follow.follow(args,gitApi,myAccount);
			if(PROGRAM_MODE.equals("unfollow")) Follow.unfollow(myAccount,gitApi);
			
		}catch(Exception e){
			System.out.println("\n\nPlease Check Your Arguments");
			System.out.println("eg: follow   {access token}{account_username}{parent_username}{levels}{interval}");
			System.out.println("eg: unfollow {access token}{account_username} \n\n");
		}
		
		System.out.println("Thank you for using GitFollower! My name is Imraaz Rally! www.imraazrally.com");
	}
	
	
}
