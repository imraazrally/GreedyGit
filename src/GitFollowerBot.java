import java.io.IOException;
import model.GitApi;
import model.GitUser;
import model.GitUserFactory;
import model.RequestDispatcher;

public class GitFollowerBot {
	public static void main(String [] args) throws IOException{
		//example for getting a list of followers given a username
		GitApi gitApi=new GitApi(new RequestDispatcher());
		GitUserFactory factory=new GitUserFactory(gitApi);
		
		GitUser user=factory.getUser("imraazrally");
		String[] listOfFollowers=gitApi.getListOfFollowerUsernames(user);
		
		for(String username: listOfFollowers) System.out.println(username);
		
	}
}
