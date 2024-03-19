package org.threeMusketeers.dliver_backend.Maps.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.threeMusketeers.dliver_backend.Maps.Dao.VendorLocationDao;
import org.threeMusketeers.dliver_backend.Maps.Pojo.CustomerLocationDbObject;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorsLocationService {
    String url = "jdbc:postgresql://localhost:5432/dlivery_backend";
    String postGresUser = "postgres";
    String postGresPassword = "ashirkul";
    final String TABLE_NAME = "vendor_locations";

    @Autowired
    VendorLocationDao vendorLocationDao;

    @Autowired
    CustomerLocationService customerLocationService;
    public ResponseEntity<?> getAllVendorsByDistance(Integer customerId, Integer distance) {
        try{
            Class.forName("org.postgresql.Driver");
            CustomerLocationDbObject customerLocationDbObject = customerLocationService.getCustomerById(customerId);

            if(customerLocationDbObject != null){
                List<VendorLocationDbObject> locationNames = getAllVendorByDist(customerLocationDbObject, Double.valueOf(distance));
                return ResponseEntity.ok(locationNames);
            }
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }
    private List<VendorLocationDbObject> getAllVendorByDist(CustomerLocationDbObject customerLocationDbObject, Double dist) throws SQLException {
        Connection conn = DriverManager.getConnection(url, postGresUser, postGresPassword);
        String querySQL = "SELECT * FROM " + TABLE_NAME +" WHERE ST_DWithin(location, ST_MakePoint(?, ?)::geography, ?)";
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
        conn.close();
        return locationNames;
    }
}
