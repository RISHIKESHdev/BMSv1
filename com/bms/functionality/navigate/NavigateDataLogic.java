package com.bms.functionality.navigate;

import com.bms.bank.Address;
import com.bms.bank.GeoLocation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NavigateDataLogic {
    boolean insertAddressRecord(Connection connection,Address address){
        boolean isAddressRecordInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(NavigateSQLQuery.INSERT_ADDRESS_QUERY)){
            ps.setString(1, address.getAddressLineOne());
            ps.setString(2, address.getAddressLineTwo());
            ps.setString(3, address.getAddressLineThree());
            ps.setString(4, address.getLandMark());
            ps.setString(5, address.getCity());
            ps.setString(6, address.getState());
            ps.setString(7, address.getCountry());
            ps.setString(8, Long.toString(address.getPinCode()));
            int rs = ps.executeUpdate();
            isAddressRecordInserted=(rs>0);
            if(isAddressRecordInserted && ps.getGeneratedKeys().next()){
                address.setAddressId(ps.getGeneratedKeys().getInt(1));
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }


        return isAddressRecordInserted;
    }
    boolean insertGeoLocationRecord(Connection connection, GeoLocation geoLocation){
        boolean isGeoLocationRecordInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(NavigateSQLQuery.INSERT_GEOLOCATION_QUERY)){
            ps.setDouble(1, geoLocation.getLatitude());
            ps.setDouble(2, geoLocation.getLongitude());
            int rs = ps.executeUpdate();
            isGeoLocationRecordInserted=(rs>0);
            if(isGeoLocationRecordInserted && ps.getGeneratedKeys().next()){
                geoLocation.setGeoLocationId(ps.getGeneratedKeys().getInt(1));
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }


        return isGeoLocationRecordInserted;
    }
}
