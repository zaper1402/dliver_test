package org.threeMusketeers.dliver_backend.Maps.Pojo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "locations")
@Data
public class VendorLocationDbObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer vendorId;
    private String address;
    private String city;
    private String state;
    private String country;
    private Integer pincode;
    @Transient
    private Double latitude;
    @Transient
    private Double longitude;

    // Constructor
    public VendorLocationDbObject(Integer id, Integer vendorId, String address, String city, String state, String country, Integer pincode, Double latitude, Double longitude) {
        this.id = id;
        this.vendorId = vendorId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
