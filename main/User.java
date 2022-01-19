package main;

import java.util.ArrayList;
import static util.Utilities.*;
    
public class User
{
        private String name;
        private String email;
        private ArrayList<FriendRequest> friendrequests;
        private Wall wall;
        
        public User(String n, String e){
            setName(n);
            setEmail(e);
            wall = new Wall(this);
            friendrequests= new ArrayList<FriendRequest>();
        }
    
        public String getName(){return name;}
        public String getEmail(){return email;}
    
        public void setName(String n){name=n;}
        public void setEmail(String e){email=e;}
    
        public void respondRequest(FriendRequest fr, String state){
            Network i = Network.getNetwork();   /*We create a Network object which returns the instance 
                object of network, this is done to access the network functions and we don't create a network instance 
                because only 1 newtwork object must be created */
            fr.setState(state); 
            if(state.equals("accept")){
                i.makeFriends(this, fr.getUserFrom());  //A network function to make 2 users friends
            }
        }
    
        public void removeFriend(User u){
            Network i = Network.getNetwork();
            i.unFriend(this, u);
            i.unFriend(u, this);
        }
    
        public Wall getWall(){
            return wall;
        }
    
        public void addMessage(Message m){
            getWall().addMessage(m);
    
        }

        public void like(Message m){
            m.addLike(this);
        }

        public ArrayList<User> getFriends(){
            Network i = Network.getNetwork();
            return i.getFriends(this);   
        }

        public ArrayList<FriendRequest> getFriendRequests(){
            return friendrequests;
        }

        public void sendRequest(User u){
            FriendRequest fr = new FriendRequest(this, u);
            friendrequests.add(fr);
        }

        public void removeFriendRequest(FriendRequest fr){
            int i = findPositionVector(friendrequests, fr);
            friendrequests.remove(i-1);
        }

}
