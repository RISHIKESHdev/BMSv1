package com.bms.functionality.navigate;

import com.bms.Main;
import com.bms.bank.Address;
import com.bms.bank.GeoLocation;
import com.bms.functionality.CommonConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class NavigateLogic implements NavigateInterface{
    private final Scanner in = Main.globalIn;
    private final BufferedReader reader=Main.globalBuffer;
    public int getIdOnInsertAddressRecord(Connection connection){
        int addressId=0;

        Address address=getAddressInfo(true);
        NavigateDataLogic dataLogic = new NavigateDataLogic();

        if(address!=null && dataLogic.insertAddressRecord(connection,address)){
            addressId=address.getAddressId();
            System.out.println("Address Record Inserted.");
        }

        return addressId;
    }
    public int getIdOnInsertGeoLocationRecord(Connection connection){
        int geoLocationId=0;

        GeoLocation geoLocation=getGeoLocationInfo();
        NavigateDataLogic dataLogic = new NavigateDataLogic();

        if(dataLogic.insertGeoLocationRecord(connection,geoLocation)){
            geoLocationId=geoLocation.getGeoLocationId();
            System.out.println("Geo Location Record Inserted.");
        }

        return geoLocationId;
    }
    private GeoLocation getGeoLocationInfo(){
        GeoLocation geoLocation;
        double latitude,longitude;

        System.out.print("Latitude: ");latitude=in.nextDouble();
        System.out.print("Longitude: ");longitude=in.nextDouble();

        geoLocation=new GeoLocation(latitude,longitude);

        return geoLocation;
    }
    public Address getAddressInfo(boolean validateInput){
        Address address = null;

        String addressLineOne,addressLineTwo,addressLineThree,landMark,city,state,country,pinCode;

        try{
            System.out.print("Address Line One: ");addressLineOne=reader.readLine();
            System.out.print("Address Line Two: ");addressLineTwo=reader.readLine();
            System.out.print("Address Line Three: ");addressLineThree=reader.readLine();
            System.out.print("Land Mark: ");landMark=reader.readLine();
            System.out.print("City: ");city=reader.readLine();
            System.out.print("State: ");state=reader.readLine();
            System.out.print("Country: ");country=reader.readLine();
            System.out.print("PinCode: ");pinCode=reader.readLine();

            address = new Address(addressLineOne,addressLineTwo,addressLineThree,landMark,city,state,country,pinCode);
        }catch(IOException e){
            System.out.println(e.getMessage());
        }

        if(validateInput && address!=null && !isValidAddress(address))address=null;

        return address;
    }
    private boolean isValidAddress(Address address){
        boolean isValidAddress=true;
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, address.getLandMark())){
            System.out.println("Land Mark Must Contain At least One Words.");
            isValidAddress=false;
        }
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, address.getCity())){
            System.out.println("City Must Contain At least One Words.");
            isValidAddress=false;
        }
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, address.getState())){
            System.out.println("State Must Contain At least One Words.");
            isValidAddress=false;
        }
        if(!Pattern.matches(CommonConstant.AT_LEAST_ONE_STRING_REGEX, address.getCountry())){
            System.out.println("Country Must Contain At least One Words.");
            isValidAddress=false;
        }
        if(!Pattern.matches(CommonConstant.INDIAN_PIN_CODE_REGEX, address.getPinCode())){
            System.out.println("Invalid PIN Code");
            isValidAddress=false;
        }

        return isValidAddress;
    }
}
