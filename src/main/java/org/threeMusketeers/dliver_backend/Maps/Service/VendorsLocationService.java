package org.threeMusketeers.dliver_backend.Maps.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.threeMusketeers.dliver_backend.Maps.Dao.VendorLocationDao;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorsLocationService {
    String url = "jdbc:postgresql://localhost:5432/dlivery_backend";
    String postGresUser = "postgres";
    String postGresPassword = "ashirkul";
    final String TABLE_NAME = "locations";

    @Autowired
    VendorLocationDao vendorLocationDao;
    public ResponseEntity<?> getAllVendorsByDistance(Integer customerId, Integer distance) {
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, postGresUser, postGresPassword);
            //todo replace by customer llogic
            VendorLocationDbObject customerLocationDbObject = getCustomerById(conn,customerId);

            if(customerLocationDbObject != null){
                List<VendorLocationDbObject> locationNames = getAllVendorByDist(conn,customerLocationDbObject, Double.valueOf(distance));
                conn.close();
                return ResponseEntity.ok(locationNames);
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    private VendorLocationDbObject getCustomerById(Connection conn, Integer customerId) throws SQLException {
        String sql = "SELECT *, ST_Y(location::geometry) AS latitude, ST_X(location::geometry) AS longitude FROM " + TABLE_NAME + " WHERE id=?";
        VendorLocationDbObject vendorLocationDbObject = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Assuming id is a varchar in your DB, convert customerId to String if not already
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vendorLocationDbObject = new VendorLocationDbObject(
                            rs.getInt("id"),
                            rs.getInt("vendorId"),
                            rs.getString("address"),
                            rs.getString("city"),
                            rs.getString("state"),
                            rs.getString("country"),
                            rs.getInt("pincode"),
                            rs.getDouble("latitude"),
                            rs.getDouble("longitude")
                    );
                    System.out.println(rs.getString("id"));
                }
            }
            return vendorLocationDbObject;
        }
    }

    private List<VendorLocationDbObject> getAllVendorByDist(Connection conn, VendorLocationDbObject customerLocationDbObject, Double dist) throws SQLException {
        String querySQL = "SELECT * FROM locations WHERE ST_DWithin(location, ST_MakePoint(?, ?)::geography, ?)";
        List<VendorLocationDbObject> locationNames = new ArrayList<>();

        PreparedStatement pstmt = conn.prepareStatement(querySQL);
        pstmt.setDouble(1, customerLocationDbObject.getLongitude()); // Replace with the reference longitude
        pstmt.setDouble(2, customerLocationDbObject.getLatitude());  // Replace with the reference latitude
        pstmt.setDouble(3, dist);  // Distance in meters
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            locationNames.add(new VendorLocationDbObject(
                    rs.getInt("id"),
                    rs.getInt("vendorId"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("state"),
                    rs.getString("country"),
                    rs.getInt("pincode"),
                    null,
                    null
            ));
            System.out.println(rs.getString("address"));
        }
        pstmt.close();
        rs.close();
        return locationNames;
    }
}
