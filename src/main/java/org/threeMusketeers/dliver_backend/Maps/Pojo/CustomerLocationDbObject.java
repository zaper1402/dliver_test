package org.threeMusketeers.dliver_backend.Maps.Pojo;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "locations")
@Data
public class CustomerLocationDbObject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private Integer customerId;
    private String address;
    private String city;
    private String state;
    private String country;
    private Integer pincode;
    private String type;
    @Transient
    private Double latitude;
    @Transient
    private Double longitude;

    // Constructor
    public CustomerLocationDbObject(Integer id, Integer customerId, String address, String city, String state, String country, Integer pincode,String type, Double latitude, Double longitude) {
        this.id = id;
        this.customerId = customerId;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
