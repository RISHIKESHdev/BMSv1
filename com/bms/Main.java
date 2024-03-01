package com.bms;

import com.bms.people.User;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static SimpleDateFormat dbDateTimeFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static LocalDateTime currentDateTime = LocalDateTime.now();
    public static String loggedInUserEmailId=null;
    public static User loggedInUserInfo;
    public static Scanner globalIn;
    public static void main(String[] args) {
        int optionIndex=0;
        int userOption=-1;
        Scanner in = new Scanner(System.in);
        globalIn=in;
        System.out.println("--------------------------------");
        System.out.println("Banking Application");
        System.out.println("--------------------------------");

        applicationWhile: while(userOption<optionIndex){
            optionIndex=0;
            if(loggedInUserEmailId != null){
                System.out.println("\n"+ (++optionIndex) + ". View Available Trips.");
                System.out.println(++optionIndex + ". Reserve Bus ticket.");
                System.out.println(++optionIndex + ". Update User Information");
                System.out.println(++optionIndex + ". Update User Password");
                System.out.println(++optionIndex + ". Cancel Reserved ticket.");
                System.out.println(++optionIndex + ". Logout User");

            }else{
                System.out.println(++optionIndex + ". Register New User.");
                System.out.println(++optionIndex + ". Login User.");
            }
            System.out.println(++optionIndex + ". View Reservation Status.");
            System.out.println(++optionIndex + ". Exit Application");
            System.out.print("Please select a option from above list: ");

            userOption = in.nextInt();

            if(loggedInUserEmailId != null){
                switch(userOption){
                    case 1: {

                        break;
                    }

                }
            }else{
                switch(userOption){
                    case 1: break;
                    case 2: {
                        if(loggedInUserInfo !=null) break applicationWhile;
                    }
                }
            }
            userOption=-1;
        }
    }
}
