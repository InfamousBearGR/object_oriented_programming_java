package main;

import java.util.ArrayList;
    
    public class Wall
    {
        private ArrayList<Message> messages;    /*A wall has written messages on it,
            so we use a Message type vector to save these messages*/
        private User user;
        
        
    public Wall(User u){
        messages =  new ArrayList<Message>();
        user = u;
    }
    
    public ArrayList<Message> getMessages(){
        return messages;
    }
    
    public void addMessage(Message m){
        messages.add(m);
    }
    
    public boolean hasRightToPost(User u){
        Network net;
        net = Network.getNetwork();
        if(net.checkFriends(user, u)){
            return true;
        }
        return false;
    }
}
