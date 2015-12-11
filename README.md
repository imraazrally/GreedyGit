# GreedyGit 

@Author: Imraaz Rally<br>
@Technologies: Java, Apache Commons CLI<br>
![tree](http://www.eps-egypt.com/images/structure-icon.gif)
GreedyGit is a GitBot that utilizes the GitHub's api to do cool things. 
At this stage, the GreedyGit is only capable of **_helping you increase your follower count!!_** 

#How does GreedyGit acquire followers ?
While the **best way to acquire followers is to create something really cool or contribute to other open source projects**, GreedyGit is a neat little tool and fun to use. The principal behind GreedyGit is **reciprocity**. People you follow are likely to follow you back. 

#Current Capabilities

1. **Recursive Breadth-First Following:** Once you specify a 'target' user. GreedyGit will follow all the followers of the 'target' user. Not only can GreedyGit grab the target's followers, but she can also grab their followers, and followers of their followers, and so on....infinitely many. However, I wouldn't recommend spamming. Moderation is key.

2. **Unfollow the Ungreatful:** Having followed a reasonable amount of users...say 4k.... a few days later you will notice your follower count increasing. A few weeks later, the rate of increase will decline. *At this point you may want to **unfollow** those users who has not reciprocated your kindness!.* Of course, you can now **move on to following others** 


#Installation Instructions
1. **Download: **GreedyGit/release/GreedyGit.jar
2. **Run:** *java -cp GreedyGit.jar com.imraazrally.gitbot.GreedyGit [options...]*

###Usage: Recursive Breadth-First Following:
**Pattern:** <br>
*java -cp GreedyGit.jar com.imraazrally.gitbot.GreedyGit* -follow -token {access_token} -user {username} -target {the target username} -delay {delay between following in seconds} -depth {number of levels of depth to follow}
<br><br>
**Sample:**<br>
*java -cp GreedyGit.jar com.imraazrally.gitbot.GreedyGit* -follow -token f8f89e8b010ca7409370b4640 -user imraazrally -target mark -delay 4 -depth 3

<br>

### Usage: Unfollow the Ungreatful:
**Pattern:** <br>
*java -cp GreedyGit.jar com.imraazrally.gitbot.GreedyGit* -unfollow -token {access_token} -user {username} 
<br><br>
**Sample:**<br>
*java -cp GreedyGit.jar com.imraazrally.gitbot.GreedyGit* -unfollow -token f8f891287ef1fde40b -user imraazrally

![greedy](http://icons.iconarchive.com/icons/jonathan-rey/simpsons/256/Homer-Simpson-02-Donut-icon.png)
###Enjoy! - Imraaz
