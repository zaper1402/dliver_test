package org.threeMusketeers.dliver_backend.Maps.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/maps")
public class MapsRequestController {
    private static final Logger logger = Logger.getLogger("MapsRequestController");
    String url = "jdbc:postgresql://localhost:5432/dlivery_backend";
    String postGresUser = "postgres";
    String postGresPassword = "ashirkul";
    final String TABLE_NAME = "locations";


    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyLocations(@RequestParam("locationId") Integer locationId,@RequestParam("locationId") Integer distance ) {
        try{
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, postGresUser, postGresPassword);
            Statement stmt = conn.createStatement();
            String sql = "SELECT *, ST_Y(location::geometry) AS latitude, ST_X(location::geometry) AS longitude FROM " + TABLE_NAME + " WHERE id="+ locationId ;
            ResultSet rs = stmt.executeQuery(sql);
            VendorLocationDbObject locationDbObject = null;
            while (rs.next()) {
                locationDbObject = new VendorLocationDbObject(
                        rs.getString("id"),
                        rs.getString("vendorId"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("country"),
                        rs.getInt("pincode"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude")
                );
                System.out.println( rs.getString("id"));
            }
            stmt.close();
            rs.close();
            if(locationDbObject != null){
                // execute search query
                String querySQL = "SELECT * FROM locations WHERE ST_DWithin(location, ST_MakePoint(?, ?)::geography, ?)";
                List<String> locationNames = new ArrayList<>();

               PreparedStatement pstmt = conn.prepareStatement(querySQL);
                pstmt.setDouble(1, locationDbObject.longitude); // Replace with the reference longitude
                pstmt.setDouble(2, locationDbObject.latitude);  // Replace with the reference latitude
                pstmt.setDouble(3, 500);  // Distance in meters
                ResultSet rs1 = pstmt.executeQuery();
                while (rs1.next()) {
                    locationNames.add(rs1.getString("address"));
                    System.out.println(rs1.getString("address"));
                }
                pstmt.close();
                rs1.close();
                conn.close();
                return ResponseEntity.ok(locationNames);
            }else {
                conn.close();
                return  ResponseEntity.notFound().build();

            }

        }catch (Exception e) {
//            logger.log(logger.getLevel(),e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }
}
