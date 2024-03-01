package com.bms.functionality.navigate;

import com.bms.Main;
import com.bms.bank.Address;
import com.bms.bank.GeoLocation;
import com.bms.functionality.CommonConstant;

import java.sql.Connection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class NavigateLogic implements NavigateInterface{
    private static final Scanner in = Main.globalIn;
    public int getIdOnInsertAddressRecord(Connection connection){
        int addressId=0;

        Address address=getAddressInfo();
        NavigateDataLogic dataLogic = new NavigateDataLogic();

        if(dataLogic.insertAddressRecord(connection,address)){
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
    private Address getAddressInfo(){
        Address address;

        String addressLineOne,addressLineTwo,addressLineThree,landMark,city,state,country;
        long pinCode;

        System.out.print("Address Line One: ");addressLineOne=in.next();
        System.out.print("Address Line Two: ");addressLineTwo=in.next();
        System.out.print("Address Line Three: ");addressLineThree=in.next();
        System.out.print("Land Mark: ");landMark=in.next();
        System.out.print("City: ");city=in.next();
        System.out.print("State: ");state=in.next();
        System.out.print("Country: ");country=in.next();
        System.out.print("PinCode: ");pinCode=in.nextLong();

        address = new Address(addressLineOne,addressLineTwo,addressLineThree,landMark,city,state,country,pinCode);

        if(!isValidAddress(address))address=null;

        return address;
    }
    private boolean isValidAddress(Address address){
        boolean isValidAddress=true;

        if(!Pattern.matches(CommonConstant.ADDRESS_LINE_REGEX, address.getAddressLineOne())){
            System.out.println("Address Line One Must Contain At least Two Words.");
            isValidAddress=false;
        }
        if(!Pattern.matches(CommonConstant.ADDRESS_LINE_REGEX, address.getAddressLineTwo())){
            System.out.println("Address Line Two Must Contain At least Two Words.");
            isValidAddress=false;
        }
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
        if(!Pattern.matches(CommonConstant.INDIAN_PIN_CODE_REGEX, Long.toString(address.getPinCode()))){
            System.out.println("Country Must Contain At least One Words.");
            isValidAddress=false;
        }

        return isValidAddress;
    }
}
