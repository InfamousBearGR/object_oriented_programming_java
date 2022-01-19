package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import static util.Utilities.*;

public class Menu
{
/* To load the data of our file we used a weird but working method, pretty much we wrote all the strings 
in the file in the exit() function in a way where we can easily locate everything, e.g. all the users name 
are written normally, after a user's name the email is written with a tab, then all the friends with a tab
until the keyword fr is found, after a fr all the friend requests are written with the format of the toString 
in the FriendRequest class, when the wall keyword is found the friend requests end and the wall messages begin 
again with a tab. After the wall the users' names that have liked that message are written with 2 tabs.
Then whenthe keyword replies indicates the replies that have the same format as wall but again with 2 tabs
as the likes, then the next wall message is written and under it the likes and replies in the same way.
After that this begins again for every user, for reference here is an example:

Alexandros Karagiannhs
    alexkarag@gmail.com
    Michael Avramopoulos
    fr
    Name: Eystratios Pantazaras state: pending  Fri May 31 22:02:14 2019
    wall
    Alexandros Karagiannhs  Hello   Tue May 28 11:54:15 2019
        replies
    Alexandros Karagiannhs  What's up?  Tue May 28 11:54:15 2019
        Michael Avramopoulos
        replies
        Michael Avramopoulos    Good, you?  Tue May 28 11:54:15 2020
        
so the user is the 1st string, his friends are the next string until the fr keyword and so on.
Also in orded to separte the strings on the messages, replies and friend requests we used a string 
constructor that takes as parameters the beginning of the line, or a tab in as a starting point
in the frined requests and the messages or 2 tabs in the replies and the end of the line, or the number 
of tabs we want the string to end */

static User u;

public static void loadFile(){
    String line;
    
    /*1st if is for getting users only, this is done so that we can create User objects 
    to save them in a ArrayList and the save their information such as messages on their wall, their email etc*/
    try{
        BufferedReader cppfile1 = new BufferedReader(new FileReader("save.txt"));
        Network i = Network.getNetwork();
    while((line = getline(cppfile1))!=null){
        if(line.charAt(0)!='\t'){
            i.addUser(new User(line," "));
        }
    }
    cppfile1.close();
    }catch(IOException ioe){
        System.out.println("cant open file\n");
        systemPause();
    }
    
    
    
    //2nd if for everything else
    try{
        BufferedReader cppfile = new BufferedReader(new FileReader("save.txt"));
            Network i = Network.getNetwork();
            User user;
            line = getline(cppfile);
            for(int j=0; j<i.getUsers().size(); j++){
                user = findUserFromName(line.substring(0, line.length()));
                if((line = getline(cppfile))== null) break;
                user.setEmail(line.substring(1, line.length()));
                if((line = getline(cppfile))== null) break;
                while(!line.equals("\tfr")){
                    User user1 = findUserFromName(line.substring(1, line.length()));
                
                    i.makeFriends(user, user1);
                    if((line = getline(cppfile))== null) break;
                }
                int k=0;
                if((line = getline(cppfile))== null) break;
                while(!line .equals("\twall")){
                    User user1 = findUserFromName(line.substring(7, line.indexOf("\t",1)));
                
                    String timestamp = (line.substring(line.indexOf("\t",line.indexOf("\t",1) + 1) + 1 ,line.length()))+"\n";
                    user.sendRequest(user1);
                    user.getFriendRequests().get(k).setTimestamp(timestamp);
                    k++;
                    if((line = getline(cppfile))== null) break;
                }
            
                if((line = getline(cppfile))== null) break;
                while(line.substring(0, 1).equals("\t") && !line.substring(1, 2).equals("\t") ){
                    User user1 = findUserFromName(line.substring(+ 1, line.indexOf("\t",1)));
                    
                    String text = line.substring((line.indexOf("\t",1)+1),line.indexOf("\t", line.indexOf("\t",1)+1));
                    String timestamp1 = line.substring(line.indexOf("\t",line.indexOf("\t",1)+1)+1, line.length())+"\n";
                    Message m = new Message(text, user1);
                    m.setTimestamp(timestamp1);
                    if((line = getline(cppfile))== null) break;
                    
                    while(!line.equals("\t\treplies")){
                        User user2 = findUserFromName(line.substring(2, line.length()));
                        m.addLike(user2);
                        if((line = getline(cppfile))== null) break;
                    }
                    if((line = getline(cppfile))== null) break;
                    
                    while(line.substring(1, 2) .equals( "\t")){
                        User user3 = findUserFromName(line.substring(2, line.indexOf("\t", 3)));
                        text = line.substring((line.indexOf("\t",3)+1), line.indexOf("\t",line.indexOf("\t",3)+1));
                        String timestamp2 = line.substring(line.indexOf("\t", line.indexOf("\t",3)+1) + 1, line.length())+"\n";
                        
                        Message reply = new Message(text, user3);
                        reply.setTimestamp(timestamp2);
                        m.addReply(reply);
                        if((line = getline(cppfile))== null) break;
                    
                    }
                    user1.getWall().addMessage(m);
                }
        
            }
            cppfile.close();
    }catch(IOException ioe){
        System.out.println("File could not load\n");
        systemPause();
    }
}

public static void printRegisterLogIn(){
    String choice;
    do{
        systemClear();
        System.out.println("Press 1 to Register or 2 to Log in\n");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextLine();
        if(choice.equals("1")){
            registerUser();
        }
        else if(choice.equals("2")){
            logIn();
        }
        else{
            System.out.println("Wrong number, please try again\n");
            systemPause();
        }
    }while(!choice.equals("1") && !choice.equals("2"));
    
}

public static void registerUser(){
    systemClear();
    String n, e;
    System.out.println("Please type Name and Surname\n");
    Scanner scanner = new Scanner(System.in);
    n = scanner.nextLine();
    System.out.println("Please type Email\n");
    e = scanner.nextLine();
    u = new User(n, e);
    Network i = Network.getNetwork();
    i.addUser(u);
    System.out.println("Welcome User "+n+"\n");
}

public static void logIn(){
    boolean f = false;
    String e;
    do{
    systemClear();
    System.out.println("Please type your Email or write 'BACK' to go back\n");
    Scanner scanner = new Scanner(System.in);
    e = scanner.nextLine();
    Network i = Network.getNetwork();
    if(e.equals("BACK"))break;
    for(int j=0; j < i.getUsers().size(); j++ ){       
        if(e.equals(i.getUsers().get(j).getEmail())){
            u = i.getUsers().get(j);
            f = true;
        }
        
    }
    if(f)
    {
        System.out.println("Welcome "+u.getName()+"!\n");
        systemPause();
    }
    else{
        System.out.println("Non-existing user, please try again\n");
        systemPause();
    }
    }while(!f);
    if(e.equals("BACK")){printRegisterLogIn();}
}

public static void printMenu(){
    int i;
    do{
        systemClear();
        String n;
        System.out.println("To see your Wall press 1\n"
            +"To see a User's wall press 2\n"
            +"To send a Friend Request press 3\n"
            +"To see your Friend Requests press 4\n"
            +"To see your Friends press 5\n"
            +"To log out press 6\n"
            +"To exit press 7\n");
        Scanner scanner = new Scanner(System.in);
        i = scanner.nextInt();
        scanner.nextLine();
        if(i == 1 ){
            seeWall(u);
        }
        else if(i == 2 ){
            do{
                System.out.println("Which friend's wall would you like to see?\n");
                n = scanner.nextLine();
                if(findUserFromName(n) == null){
                    System.out.println("User doesn't exist\n");
                }
            }while(findUserFromName(n) == null);
            seeWall(findUserFromName(n));
        }
        else if(i == 3 ){
            sendFriendRequest();
        }
        else if( i == 4 ){
            respondToRequests();
        }
        else if ( i == 5 ){
            seeMyFriends();
        }
        else if( i == 6 ){
            printRegisterLogIn();
        }
        else if( i == 7 ){
            exit();
        }
        else{
            System.out.println("Wrong number, please try again\n");
            systemPause();
        }
    }while(i!= 7);
}

public static void seeWall(User user){
    systemClear();
    int choice;
    int choice1;
    String text;
    for(int i = 0; i < user.getWall().getMessages().size(); i++ ){
        System.out.println((i+1)+". "+user.getWall().getMessages().get(i).getText()+"\t(Likes: "+user.getWall().getMessages().get(i).getLikes()+")\t-" + user.getWall().getMessages().get(i).getUser().getName()+ " " +user.getWall().getMessages().get(i).getTimestamp().toString());
        for(int k = 0; k < user.getWall().getMessages().get(i).getReplies().size(); k++){
            System.out.println("\t"+(k+1)+". "+user.getWall().getMessages().get(i).getReplies().get(k).getText()+ "\t-" + user.getWall().getMessages().get(i).getReplies().get(k).getUser().getName()+ " "+ user.getWall().getMessages().get(i).getReplies().get(k).getTimestamp().toString());
        }
        
    }
    System.out.println();
    System.out.println("Press 1 to post a message\n"
        +"Press 2 to reply to a message\n"
        +"Press 3 to like a message\n"
        +"Press 4 to go back\n");
    Scanner scanner = new Scanner(System.in);
    choice = scanner.nextInt(); 
    scanner.nextLine(); 
    if(choice == 1 ){
        if(user.getWall().hasRightToPost(u) || user==u){
            System.out.println("Type your message\n");
            text = scanner.nextLine();
            user.getWall().addMessage(new Message(text, u));
            seeWall(user);
        }
        else{
            System.out.println("You are not friends with the user "+user.getName()+"\n");
            systemPause();
            seeWall(user);
        }
    }
    else if(choice == 2 ){
        if(user.getWall().hasRightToPost(u) || user==u){
            System.out.println("Choose a message\n");
            choice1 = scanner.nextInt(); 
            scanner.nextLine(); 
            System.out.println("Type your reply\n");
            text = scanner.nextLine(); 
            user.getWall().getMessages().get(choice1-1).addReply(new Message(text, u));
            seeWall(user);  
        }
        else{
            System.out.println("You are not friends with the user "+user.getName()+"\n");
            systemPause();
            seeWall(user);
        }
    }
    else if(choice == 3){
        int i;
        if(user.getWall().hasRightToPost(u) || user==u){
            do{     
                System.out.println("Choose a message\n");
                choice1 = scanner.nextInt(); 
                scanner.nextLine();
                if(choice1>user.getWall().getMessages().size()){System.out.println("Non-existing message, please try again!\n");}
        }while(choice1>user.getWall().getMessages().size());
            System.out.println("Press 1 to like or 2 to remove like\n");
            i = scanner.nextInt(); 
            scanner.nextLine();
            if(i==1){
                user.getWall().getMessages().get(choice1-1).addLike(u);  
                seeWall(user);
            }
            else if(i==2){
                user.getWall().getMessages().get(choice1-1).removeLike(u);   
                seeWall(user);
            }
        }
        else{
            System.out.println("You are not friends with the user "+user.getName()+"\n");
            systemPause();
            seeWall(user);
        }
    }
}

public static void sendFriendRequest(){
    String n;
    int choice;
    do
    {
        systemClear();
        System.out.println("Press 1 to type the user's name\n"
            +"Press 2 to go back\n");
        Scanner scanner = new Scanner(System.in);
        choice = scanner.nextInt(); 
        scanner.nextLine();
        if(choice == 1){
            System.out.println("Type the user's name\n");
            n = scanner.nextLine();
            User uto = findUserFromName(n);
            if(uto!=null){
                uto.sendRequest(u);
                System.out.println("Friend Request sent!\n");
                systemPause();
            }
            else{
                System.out.println("User not found\n");
                systemPause();
            }
        }
        else if(choice!=1 && choice!=2)
        {
            System.out.println("Wrong number, please try again\n");
            systemPause();
        }
    }while(choice!=1 && choice!=2);
    
}

public static void respondToRequests(){
    ArrayList<FriendRequest> fr = u.getFriendRequests();
    int j, choice;
    choice=1;
    Scanner scanner = new Scanner(System.in);
    do{
        do{
            systemClear();
            System.out.println("These are your friend requests \n");
            for(int i = 0; i < fr.size(); i++)
            {
                System.out.println( (i+1) +". " + fr.get(i).toString() +"\n");
            }
            System.out.println("Type the number corresponding to the friend request you would like to accept or decline, or -1 to exit\n");
            j = scanner.nextInt(); 
            scanner.nextLine();
        }while(j!=-1 && j>fr.size());
        if(j!=-1)
        {
            systemClear();
            System.out.println("These are your friend request \n");
            for(int i = 0; i < fr.size(); i++)
            {
                System.out.println((i+1) +". " + fr.get(i).toString()+"\n");
            }
            System.out.println("Press 1 to Accept\n"
            +"Press 2 to Decline\n"
            +"Press 3 to go Back\n");
            choice = scanner.nextInt(); 
            if(choice == 1){
                u.respondRequest(fr.get(j-1), "accept");
            }   
            else if(choice == 2){
                u.respondRequest(fr.get(j-1), "decline");
            }
        }
    }while(choice!=1 && choice!=2 && choice!=3 && j!=-1);
}

public static void seeMyFriends(){
    systemClear();
    Scanner scanner = new Scanner(System.in);
    int j;
    ArrayList<User> ufr = u.getFriends();
    System.out.println("These are your friends\n");
    for(int i = 0; i < ufr.size(); i++){
        System.out.println((i+1) + ". " + ufr.get(i).getName() + "\n");
    }
    System.out.println();
    System.out.println("Press 0 to go back, or type a number to remove a friend\n");
    j = scanner.nextInt();
    scanner.nextLine();
    if(j!=0){   
        u.removeFriend(ufr.get(j-1));
        System.out.println("User "+ufr.get(j-1).getName()+" was removed from your friends\n");
        systemPause();
    }
    
}

public static void exit(){
    systemClear();
    try{
        PrintWriter cppfile = new PrintWriter("save.txt");
        Network i = Network.getNetwork();
        ArrayList<User> users = i.getUsers();
        for(int j=0; j<users.size(); j++){
            cppfile.println(users.get(j).getName());
            cppfile.println("\t"+users.get(j).getEmail());
            
            ArrayList<User> friends = i.getFriends(users.get(j));
            for(int k=0; k<friends.size(); k++){
                cppfile.println("\t"+friends.get(k).getName());
            }
            
            cppfile.println("\tfr");  
            ArrayList<FriendRequest> fr = users.get(j).getFriendRequests();
            for(int z=0; z<fr.size(); z++){
                cppfile.println("\t"+fr.get(z).toString().substring(0, fr.get(z).toString().length()-1));
            }
            
            cppfile.println("\twall");
            ArrayList<Message> messages = users.get(j).getWall().getMessages();
            for(int w=0; w<messages.size(); w++){
                cppfile.println("\t"+messages.get(w).getUser().getName()+"\t"+messages.get(w).getText()+"\t"+messages.get(w).getTimestamp().toString().substring(0, messages.get(w).getTimestamp().toString().length()-1));
                ArrayList<User> likes = messages.get(w).getLikeArray();
                ArrayList<Message> replies = messages.get(w).getReplies();
                for(int q=0; q<likes.size(); q++){
                    cppfile.println("\t\t"+likes.get(q).getName());
                }
                cppfile.println("\t\treplies");
                for(int t=0; t<replies.size(); t++){
                    cppfile.println("\t\t"+replies.get(t).getUser().getName()+"\t"+replies.get(t).getText()+"\t"+replies.get(t).getTimestamp().toString().substring(0, replies.get(t).getTimestamp().toString().length()-1));
                }
                
            }
        }
        cppfile.close();
    }
    catch(IOException ioe){
        System.out.println("File could not open\n");
        systemPause();
    }
    
}

}
