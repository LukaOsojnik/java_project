package hr.javafx.realestate.javafxmanagementsystem.model;

import hr.javafx.realestate.javafxmanagementsystem.enum1.County;

/**
 * Služi za kreiranje klase Address
 */

public class Address extends Entity{
    private String streetName;
    private String streetNumber;
    private String city;
    private County county;

    public Address(Long id, String streetName, String streetNumber, String city, County county) {
        super(id);
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.county = county;
    }

    public Address(String streetName, String streetNumber, String city, County county) {
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.city = city;
        this.county = county;
    }



    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }
    public String toString(){
        return streetName + " " + streetNumber + ", " + city;
    }
}
