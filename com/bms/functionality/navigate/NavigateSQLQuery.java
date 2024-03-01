package com.bms.functionality.navigate;

public class NavigateSQLQuery {
    static final String INSERT_ADDRESS_QUERY="INSERT INTO Address(address_Line_One,address_Line_Two,address_Line_Three,landmark,city,state,country,pinCode) value(?,?,?,?,?,?,?,?)";
    static final String INSERT_GEOLOCATION_QUERY="INSERT INTO Geo_Location(latitude,longitude) value(?,?)";
}
