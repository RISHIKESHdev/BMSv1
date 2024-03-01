package com.bms.functionality.navigate;

import java.sql.Connection;

public interface NavigateInterface {
    int getIdOnInsertAddressRecord(Connection connection);
    int getIdOnInsertGeoLocationRecord(Connection connection);
}
