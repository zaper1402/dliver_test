package org.threeMusketeers.dliver_backend.Maps.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;
import org.threeMusketeers.dliver_backend.Maps.Service.VendorsLocationService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/maps")
public class MapsRequestController {
    private static final Logger logger = Logger.getLogger("MapsRequestController");
    @Autowired
    VendorsLocationService vendorsLocationService;



    @GetMapping("/nearby")
    public ResponseEntity<?> getNearbyLocations(@RequestParam("locationId") Integer locationId,@RequestParam("distance") Integer distance ) {
        return vendorsLocationService.getAllVendorsByDistance(locationId,distance);
    }
}
