package main;

import java.util.ArrayList;
import static util.Utilities.*;
import java.util.HashMap;

public class Network
{
    private ArrayList<User> users;
    private static Network instance = null;
    private HashMap<User, ArrayList<User>> friends;
    
    
    private Network(){
        users = new ArrayList<User>();
        friends = new HashMap<User, ArrayList<User>>();
    }

    public ArrayList<User> getUsers(){
        return users;
    }

    public void addUser(User u){
        int i = findPositionVector(users, u);
        if(i==-1){
            users.add(u);
            friends.put(u, new ArrayList<User>());
        }
        else{
    
            System.out.println("The user already exists!\n");
        }
    }

    public void deleteUser(User u){
        int i = findPositionVector(users, u);
        if(i!=-1){
            users.remove(i-1);
        }
        else{
    
            System.out.println("The user doesn't exist!\n");
        }
    }

    public void makeFriends(User u1, User u2){
        int i = findPositionVector(u1.getFriends(), u2);
        if(i==-1){
            addFriend(u1, u2);
            addFriend(u2, u1);
        }
    }

    public ArrayList<User> getFriends(User u){
        return friends.get(u);
    }   

    public boolean checkFriends(User u1, User u2){
        if(findPositionVector(u2.getFriends(), u1) != -1){
            return true;
        }
    
        return false;
    }

    public ArrayList<User> getMutualFriends(User u1, User u2){
        ArrayList<User> mutuals = new ArrayList<User>();
        int i, j;
    
        for(i=0; i < u1.getFriends().size(); i++){
            for(j=0; j < u2.getFriends().size(); j++){
                if(u1.getFriends().get(i)==u2.getFriends().get(j)){
                    mutuals.add(u1.getFriends().get(i));
                }
            }   
        }
        return mutuals;
    }

    public static Network getNetwork(){
        if(instance==null){ /*If no instance has been created the initiation of the programm, 
                    then a new a Network object is created */
        
        instance = new Network();
        }
        return instance; /*After the creation or call of getNetwork in any classes
            the instance is returned for uses in other classes*/
    }
    
    //Function that adds a user to the map of a user's friends
    public void addFriend(User u1, User u2){
        friends.get(u1).add(u2);
    }

    public void unFriend(User u, User removed){
        int i = findPositionVector(friends.get(u), removed);
        friends.get(u).remove(i);
    }
        
}
