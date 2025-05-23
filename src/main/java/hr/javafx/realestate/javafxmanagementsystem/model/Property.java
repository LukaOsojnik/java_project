package hr.javafx.realestate.javafxmanagementsystem.model;

import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyPurpose;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyStatus;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Služi za kreiranje klase Property
 */

public class Property extends Entity implements Serializable {

    private Address address;
    private BigDecimal metersSquared;
    private BigDecimal price;
    private PropertyType propertyType;
    private PropertyPurpose propertyPurpose;
    private PropertyStatus propertyStatus;

    public Property(BigDecimal metersSquared, BigDecimal price, Address address, PropertyType propertyType, PropertyPurpose propertyPurpose) {
        this.propertyType = propertyType;
        this.address = address;
        this.metersSquared = metersSquared;
        this.price = price;
        this.propertyPurpose = propertyPurpose;
        this.propertyStatus = PropertyStatus.AVAILABLE;
    }

    public Property(Long id, BigDecimal metersSquared, BigDecimal price, Address address, PropertyType propertyType, PropertyPurpose propertyPurpose) {
        super(id);
        this.propertyType = propertyType;
        this.address = address;
        this.metersSquared = metersSquared;
        this.price = price;
        this.propertyPurpose = propertyPurpose;
        this.propertyStatus = PropertyStatus.AVAILABLE;
    }
    public Property(Long id, BigDecimal metersSquared, BigDecimal price, Address address, PropertyStatus propertyStatus, PropertyType propertyType, PropertyPurpose propertyPurpose) {
        super(id);
        this.propertyType = propertyType;
        this.address = address;
        this.metersSquared = metersSquared;
        this.price = price;
        this.propertyPurpose = propertyPurpose;
        this.propertyStatus = propertyStatus;
    }


    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getMetersSquared() {
        return metersSquared;
    }

    public void setMetersSquared(BigDecimal metersSquared) {
        this.metersSquared = metersSquared;
    }

    public PropertyPurpose getPropertyPurpose() {
        return propertyPurpose;
    }

    public void setPropertyPurpose(PropertyPurpose propertyPurpose) {
        this.propertyPurpose = propertyPurpose;
    }

    public PropertyStatus getPropertyStatus() {
        return propertyStatus;
    }

    public void setPropertyStatus() {
        propertyStatus = PropertyStatus.NOT_AVAILABLE;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPropertyStatus(PropertyStatus propertyStatus) {
        this.propertyStatus = propertyStatus;
    }
}
