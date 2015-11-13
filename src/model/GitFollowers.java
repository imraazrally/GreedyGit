package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GitFollowers{
	private List<GitUser> followers;
	
	public GitFollowers(){
		this.followers=new ArrayList<GitUser>();
	}
	
	public void addFollower(GitUser user){
		followers.add(user);
	}

	public List<GitUser> getFollowers(){
		return followers;
	}
	
}
