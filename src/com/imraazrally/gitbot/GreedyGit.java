package com.imraazrally.gitbot;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.imraazrally.gitbot.controller.Follow;
import com.imraazrally.gitbot.model.DbService;
import com.imraazrally.gitbot.model.GitApi;
import com.imraazrally.gitbot.model.GitUser;
import com.imraazrally.gitbot.model.GitUserFactory;
import com.imraazrally.gitbot.model.RequestDispatcher;

public class GreedyGit {
	
	public static void main(String [] args) throws IOException{
	
		/*  @Author Imraaz Rally
		 *  @Website www.imraazrally.com
		 * 
		 *  @Note:
		 *		1. Please do not run this program for too long and drain the Server resources.
		 * 		2. Please do not use this program with the intention of spamming.
		 * 		3. Clean Code, Nice profile, and regular activity with the moderate use of this 
		 * 		   program will help you to aquire followers
		 * 
		 *  @Description:
		 *    	This program is a GitBot to follow/unfollow users with the intention of gaining real followers.
		 *    	Given a "TARGET" username, this bot will follow all of the "followers" of the TARGET user. 
		 *      Then, proceed to follow their followers, and so on recursively. 
		 *    	The expectation is, some of those followers will follow you back based on the psychological principal of RECIPROCITY. 
		 */

		
		//Initializing the command line 
		CommandLine CMD=initCli(args);
		if(CMD==null)return;
		
		//Accesstoken is used as the authentication system to access GitHub Api 
		final String accessToken=CMD.getOptionValue("token");
		final String username=CMD.getOptionValue("user");
		
		//Api call functions to github are processed via GitApi class
		GitApi gitApi=new GitApi(
							//Passing the Access Token to Request Dispatcher;
							new RequestDispatcher(accessToken)							
					  );
		
		//The user of the account that will be aquiring the follwers
		GitUser myAccount=gitApi.getFactory().getUser(username);
		
		
		if(CMD.hasOption("follow")){
			
			// In order to follow recursively, we need the -target -delay -depth paremeters from command line
			if(!CMD.hasOption("target") || !CMD.hasOption("delay") || !CMD.hasOption("depth")){
				System.out.println("missing -target or -delay or -depth");
				return;
			}
			
			GitUser target=gitApi.getFactory().getUser(CMD.getOptionValue("target"));
			int delay=Integer.parseInt(CMD.getOptionValue("delay"));
			int depth=Integer.parseInt(CMD.getOptionValue("depth"));
			
		
			//Call to recursive follow 
			Follow.followUsersAtDepthN(myAccount,target,gitApi,depth, delay);
		}
		
		
		if(CMD.hasOption("unfollow")){
			Follow.unfollow(myAccount,gitApi);
		}
		
		System.out.println("\n Thank you for using GitFollower! My name is Imraaz Rally! www.imraazrally.com");
		
	}
	
	
	public static CommandLine initCli(String [] args){
		/*
		 *  Setting up the command line interface and options
		 *  
		 *  @Option -follow			= to follow users
		 *  @Option -unfollow		= to unfollow users
		 *  @Option -token			= your github access_token. 
		 *  @Option -user 			= your gitHub username  
		 *  @Option -delay			= wait in seconds before following next user
		 *  @Option -depth			= recursive depth of the followers
		 *  @Option -help			= help documentation
		 *  @Option -target		= username of the target user
		 */
		
		CommandLineParser cli=new DefaultParser();
		Options options=new Options();
		
		options.addOption("follow", false, "set the program mode to follow users");
		options.addOption("unfollow",false, "set the program mode to unfollow users");
		options.addOption("token",true,"github access token");
		options.addOption("user",true,"github username");
		options.addOption("delay",true,"wait in seconds before following next user");
		options.addOption("depth",true,"how many level down the chain you want to recursively follow");
		options.addOption("help",true,"help");
		options.addOption("target",true,"target user");

		
		try {
			
			CommandLine cmd=cli.parse(options, args);
			
			//Input Validation
			
			if(cmd==null){ 
				System.out.println("Please User --help for details");
				return null;
			}
			
			if(cmd.hasOption("follow") && cmd.hasOption("unfollow")){
				System.out.println("invalid -follow and -unfollow. Use only one");
				return null;
			}
			
			if(!cmd.hasOption("follow")&&!cmd.hasOption("unfollow")){
				System.out.println("missing -follow or -unfollow");
				return null;
			}
			
			if(!cmd.hasOption("token") || !cmd.hasOption("user")){
				System.out.println("missing -token and -user");
				return null;
			}
			
			return cmd;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}










