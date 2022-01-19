package main;

public class FriendRequest
{
    private User userto;    //The user that recieved the friend request
    private User userfrom;  //The User that send the friend request
    private Date timestamp; //Friend requests have a timestamp to see when they were sent
    private String state;
    
    FriendRequest(User to, User from){
        timestamp = new Date();
        userto=to;
        userfrom=from;
        state="pending"; //All created friend request have a pending state, then a user decides if he wants to accept
        timestamp.updateTime();
    }

    public User getUserTo(){       
        return userto;
    }

    public User getUserFrom(){
        return userfrom;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public void setState(String s){
        if(s=="accept"||s=="decline"){  /*If a friend request gets accepted or declined 
        there is no need for it to exist*/
            state=s;
            userto.removeFriendRequest(this);  
        }
        else{
            state="pending";         
        }
    }

    public String toString(){
        String s;
        if(state == "accept"){s="accepted";}
        else if(state == "decline"){s="declined";}
        else {s="pending";}
        return "Name: "+userfrom.getName()+"\tstate: "+ s + "\t" + timestamp.toString();   
    }

    public void setTimestamp(String t){
        timestamp.setTime(t);
    }

}
