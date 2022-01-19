package main;

import java.util.ArrayList;
import static util.Utilities.*;

public class Message
{
    private Date timestamp;
    private String text;
    private ArrayList<User> likes; /*We decided to make likes a User type vector so that we would be able
        to check if user has already liked a message, if we want the number of likes we use getLikes*/
    private ArrayList<Message> replies; /*Replies have the same logic of a message(a text, a timestamp and a User)
        so instead of creating a new class for the replies we made the a vector of messages*/
    private User user;
    
    public Message(String m, User u){
        replies = new ArrayList<Message>();
        likes = new ArrayList<User>();
        timestamp = new Date();
	    text=m;
	    timestamp.updateTime();
	    user = u;
    }

    public int getLikes(){
	return likes.size();
    }

    public void addLike(User u){
	int i = findPositionVector(likes, u);
	if(i==-1){
		likes.add(u);
	}
	else{
		System.out.println("You have already liked this message\n");
		systemPause();
	}
	
    }

    public void removeLike(User u){
	int i = findPositionVector(likes, u);
	if(i!=-1){
		likes.remove(i-1);
	}
	else{
		System.out.println("User hasn't liked this message!\n");
		systemPause();
	}
    }

    public Date getTimestamp(){
	return timestamp;
    }

    public String getText(){
	return text;
    }

    public ArrayList<Message> getReplies(){
	return replies;
    }

    public void addReply(Message m){
	replies.add(m);
    }

    public User getUser(){
	return user;
    }

    public ArrayList<User> getLikeArray(){
	return likes;
    }

    public void setTimestamp(String t){
	timestamp.setTime(t);
    }
}
