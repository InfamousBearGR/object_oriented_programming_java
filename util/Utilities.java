package util;

import main.Network;
import main.User;

import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class Utilities
{
    public static void systemPause(){
        System.out.println("Press ENTER to continue...");
        new Scanner(System.in).nextLine();   
    }
    
    public static void systemClear(){
        for(int i =0; i<50; i++) System.out.println();
    }
    
    public static String getline(BufferedReader br){
        String line = null;
        try{
            line = br.readLine();
        } catch(IOException ioe){
            System.out.println("couldnt read line");
        }
        return line;
    }
    
    public static User findUserFromName(String n){
	Network i = Network.getNetwork();
	
	for(int j = 0; j < i.getUsers().size(); j++){
		if(n.equals( i.getUsers().get(j).getName())){
			return i.getUsers().get(j);
		}
	}
	return null;
    }
    
    public static <T> int findPositionVector(ArrayList<T> array, T temp){
	int i;
	if(array !=null) {
        if (array.size() != 0) {
            for (i = 0; i < array.size(); i++) {
                if (array.get(i) == temp) {
                    return i;
                }
            }
        }

    }
    i = -1;
	return i;
    }
}
