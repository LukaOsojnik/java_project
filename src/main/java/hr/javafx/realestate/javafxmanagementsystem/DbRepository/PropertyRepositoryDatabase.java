package hr.javafx.realestate.javafxmanagementsystem.DbRepository;

import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyPurpose;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyStatus;
import hr.javafx.realestate.javafxmanagementsystem.enum1.PropertyType;
import hr.javafx.realestate.javafxmanagementsystem.exception.EmptyRepositoryResultException;
import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.Address;
import hr.javafx.realestate.javafxmanagementsystem.model.Property;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class PropertyRepositoryDatabase<T extends Property>  extends AbstractRepositoryDatabase<T>{
    @Override
    public T findById(Long id) throws EmptyRepositoryResultException {
        try(Connection conn = openConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM property WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Property with id " + id + " not found");
            }
        } catch(IOException | SQLException e){
            logger.error("Pogreška s pronalaskom id-a kod nekretnine.");
            throw new RepositoryAccessException(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        List<T> propertyList = new ArrayList<>();

        try(Connection conn = openConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PROPERTY");
            while(rs.next()) {
                Property property = extractFromResultSet(rs);
                propertyList.add((T) property);
            }

            return propertyList;

        } catch (SQLException | IOException e) {
            logger.error("Pogreška u čitanju imovine iz baze.");
            throw new RepositoryAccessException(e);
        }
    }
    public T extractFromResultSet(ResultSet rs) throws SQLException {

        AddressRepositoryDatabase<Address> ard = new AddressRepositoryDatabase<>();
        Long id = rs.getLong("ID");
        Long idAddress = rs.getLong("ADDRESS_ID");
        String mtrSq = rs.getString("METERS_SQUARED");
        String price = rs.getString("PRICE");
        String propertyStatus = rs.getString("PROPERTY_STATUS");
        String propertyType = rs.getString("PROPERTY_TYPE");
        String propertyPurpose = rs.getString("PROPERTY_PURPOSE");

        Address address = ard.findById(idAddress);

        Property property = new Property(id, new BigDecimal(mtrSq), new BigDecimal(price), address, PropertyStatus.valueOf(propertyStatus),
                PropertyType.valueOf(propertyType), PropertyPurpose.valueOf(propertyPurpose));

        return (T)property;
    }


    @Override
    public void save(T entity) {
        try(Connection conn = openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO PROPERTY(ADDRESS_ID, " +
                    "METERS_SQUARED, PRICE, PROPERTY_STATUS, PROPERTY_TYPE, PROPERTY_PURPOSE) VALUES (?,?,?,?,?,?)");
            stmt.setLong(1, entity.getAddress().getId());
            stmt.setBigDecimal(2, entity.getMetersSquared());
            stmt.setBigDecimal(3, entity.getPrice());
            stmt.setString(4, entity.getPropertyStatus().name());
            stmt.setString(5, entity.getPropertyType().name());
            stmt.setString(6, entity.getPropertyPurpose().name());

            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            logger.error("Pogreška kod spremanja imovine.");
            throw new RepositoryAccessException(e);
        }
    }

    public void changeStatus(T property) throws SQLException, IOException {
        try(Connection conn = openConnection()){
            PreparedStatement pstmt = conn.prepareStatement("UPDATE PROPERTY SET PROPERTY_STATUS = ? WHERE ID = ?");
            if(property.getPropertyStatus() == PropertyStatus.AVAILABLE){
                pstmt.setString(1, PropertyStatus.NOT_AVAILABLE.name());
            } else{
                pstmt.setString(1, PropertyStatus.AVAILABLE.name());
            }
            pstmt.setLong(2, property.getId());
            pstmt.executeUpdate();

        }
    }
}
