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
		
		GitApi gitApi=
				new GitApi(
							//Passing the Access Token to Request Dispatcher; Change to args[0]
							new RequestDispatcher("YOUR ACCESS TOKEN GOES HERE.")
						  );
		
		//Parent User from which we will extract all the followers recursively
		GitUser Parent=gitApi.getFactory().getUser("imraazrally");
		
		//Recursively treverse followers, and their followers, and so on.
		followUsersAtDepthN(Parent,gitApi,2);
		
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
