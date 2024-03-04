package com.bms.functionality.navigate;

import com.bms.bank.Address;
import com.bms.bank.GeoLocation;

import java.sql.*;

public class NavigateDataLogic {
    boolean insertAddressRecord(Connection connection,Address address){
        boolean isAddressRecordInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(NavigateSQLQuery.INSERT_ADDRESS_QUERY, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, address.getAddressLineOne());
            ps.setString(2, address.getAddressLineTwo());
            ps.setString(3, address.getAddressLineThree());
            ps.setString(4, address.getLandMark());
            ps.setString(5, address.getCity());
            ps.setString(6, address.getState());
            ps.setString(7, address.getCountry());
            ps.setString(8, address.getPinCode());
            int rs = ps.executeUpdate();
            if(rs>0){
                ResultSet rSet=ps.getGeneratedKeys();
                rSet.next();
                address.setAddressId(rSet.getInt(1));
                isAddressRecordInserted=true;
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }


        return isAddressRecordInserted;
    }
    boolean insertGeoLocationRecord(Connection connection, GeoLocation geoLocation){
        boolean isGeoLocationRecordInserted=false;

        try(PreparedStatement ps = connection.prepareStatement(NavigateSQLQuery.INSERT_GEOLOCATION_QUERY, Statement.RETURN_GENERATED_KEYS)){
            ps.setDouble(1, geoLocation.getLatitude());
            ps.setDouble(2, geoLocation.getLongitude());
            int rs = ps.executeUpdate();
            if(rs>0){
                ResultSet rSet = ps.getGeneratedKeys();
                rSet.next();
                geoLocation.setGeoLocationId(rSet.getInt(1));
                isGeoLocationRecordInserted=true;
            }
        }catch(NullPointerException | SQLException e){
            System.out.println(e.getMessage());
        }


        return isGeoLocationRecordInserted;
    }
}
