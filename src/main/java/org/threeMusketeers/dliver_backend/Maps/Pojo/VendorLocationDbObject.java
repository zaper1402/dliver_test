package org.threeMusketeers.dliver_backend.Maps.Pojo;

public class VendorLocationDbObject {
    public String id;
    public String vendorId;
    public String address;
    public String city;
    public String state;
    public String country;
    public Integer pincode;
    public Double latitude;
    public Double longitude;

    // Constructor
    public VendorLocationDbObject(String id, String vendorId, String address, String city, String state, String country, Integer pincode, Double latitude, Double longitude) {
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
