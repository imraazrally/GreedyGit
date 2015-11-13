import java.io.IOException;
import model.GitApi;
import model.GitUser;
import model.GitUserFactory;
import model.RequestDispatcher;

public class GitFollowerBot {
	public static void main(String [] args) throws IOException{
		//test
		GitApi gitApi=new GitApi(new RequestDispatcher());
		GitUserFactory factory=new GitUserFactory(gitApi);
		
		GitUser imraaz=factory.getUser("imraazrally");
		
		for(String username:gitApi.getListOfFollowerUsernames(imraaz)){
			System.out.println(username);
		}
		
	}
}
