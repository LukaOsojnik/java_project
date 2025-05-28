package hr.javafx.realestate.javafxmanagementsystem.model;

import hr.javafx.realestate.javafxmanagementsystem.enum1.County;

/**
 * Služi za kreiranje klase Address
 * Implementira Builder pattern za lakše i fleksibilnije kreiranje instanci
 */
public class Address extends Entity {
    private final String streetName;
    private final String streetNumber;
    private final String city;
    private final County county;

    // Private constructor - instances can only be created through the Builder
    private Address(Builder builder) {
        super(builder.id);
        this.streetName = builder.streetName;
        this.streetNumber = builder.streetNumber;
        this.city = builder.city;
        this.county = builder.county;
    }


    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getCity() {
        return city;
    }

    public County getCounty() {
        return county;
    }

    @Override
    public String toString() {
        return streetName + " " + streetNumber + ", " + city;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String streetName;
        private String streetNumber;
        private String city;
        private County county;

        // Private constructor
        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder streetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder streetNumber(String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder county(County county) {
            this.county = county;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}