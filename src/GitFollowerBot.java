import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.GitApi;
import model.GitFollowers;
import model.GitUser;
import model.GitUserFactory;
import model.RequestDispatcher;

public class GitFollowerBot {
	public static void main(String [] args) throws IOException{
		//Update to get info from standard input
		String ACCESS_TOKEN="ACCESS TOKEN";
		String PARENT_USERNAME="imraazrally";
		
		GitApi gitApi=new GitApi(
							//Passing the Access Token to Request Dispatcher;
							new RequestDispatcher(ACCESS_TOKEN)
						  );
		
		//Parent User from which we will extract all the followers recursively
		GitUser parent=gitApi.getFactory().getUser(PARENT_USERNAME);
		
		//Recursively treverse followers, and their followers, and so on.
		followUsersAtDepthN(parent,gitApi,10);
		
	}
	
	public static void followUsersAtDepthN(GitUser parentUser, GitApi gitApi, int level){
		if(level<1)return;
				
		//Follow each follower from current level, and GET their followers as well (recusively).
		for(String username: gitApi.getListOfFollowerUsernames(parentUser)){
			
			GitUser user=gitApi.getFactory().getUser(username);	
			//------------------Follow!------------------
			gitApi.follow(user);
			//-------------------------------------------
			followUsersAtDepthN(user,gitApi,level-1);
		}
			
	}
}
