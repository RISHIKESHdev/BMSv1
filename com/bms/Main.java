package com.bms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    public static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter dbDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat dbSimpleDateTimeFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static LocalDateTime currentDateTime = LocalDateTime.now();
    public static Scanner globalIn;
    public static BufferedReader globalBuffer;

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        globalIn=in;
        globalBuffer=reader;
        Application app = new Application();
        app.appFeatures();
        reader.close();
        in.close();
    }
}
