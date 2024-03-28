package org.threeMusketeers.dliver_backend.Maps.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.threeMusketeers.dliver_backend.Maps.Dao.CustomerLocationDao;
import org.threeMusketeers.dliver_backend.Maps.Dao.VendorLocationDao;
import org.threeMusketeers.dliver_backend.Maps.Pojo.CustomerLocationDbObject;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerLocationService {
    String url = "jdbc:postgresql://localhost:5432/dlivery_backend";
    String postGresUser = "postgres";
    String postGresPassword = "ashirkul";
    final String TABLE_NAME = "customer_locations";

    @Autowired
    CustomerLocationDao customerLocationDao;

    public CustomerLocationDbObject getCustomerById(Integer customerId) throws SQLException {
        Connection conn = DriverManager.getConnection(url, postGresUser, postGresPassword);
        String sql = "SELECT *, ST_Y(location::geometry) AS latitude, ST_X(location::geometry) AS longitude FROM " + TABLE_NAME + " WHERE id=?";
        CustomerLocationDbObject customerLocationDbObject = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    customerLocationDbObject = new CustomerLocationDbObject(
                            rs.getInt("id"),
                            rs.getInt("customerId"),
                            rs.getString("address"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("country"),
                            rs.getInt("pincode"),
                            rs.getString("type"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude")
                    );
                    System.out.println("customerId : "+rs.getString("customerid"));
                }
            }
            conn.close();
            return customerLocationDbObject;
        }
    }
}
