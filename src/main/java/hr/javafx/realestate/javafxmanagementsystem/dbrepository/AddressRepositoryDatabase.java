package hr.javafx.realestate.javafxmanagementsystem.dbrepository;

import hr.javafx.realestate.javafxmanagementsystem.enum1.County;
import hr.javafx.realestate.javafxmanagementsystem.exception.EmptyRepositoryResultException;
import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.Address;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class AddressRepositoryDatabase<T extends Address> extends AbstractRepositoryDatabase<T> {

    private static final String ERROR_MESSAGE = "Pogreška pri spajaju na bazu.";

    @Override
    public T findById(Long id) {
        try(Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT address.id, address.street_name," +
                    " address.street_number, address.city, address.county FROM address WHERE id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return  extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Address with id " + id + " not found");
            }
        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public List<T> findAll() {
        try(Connection conn = openConnection();
            Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT address.id, address.street_name," +
                    " address.street_number, address.city, address.county  FROM ADDRESS");
            List<T> list = new ArrayList<>();
            while(rs.next()){
                Address address = extractFromResultSet(rs);
                list.add((T) address);
            }

            return list;

        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }

    public T extractFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("ID");
        String streetName = rs.getString("street_name");
        String streetNumber = rs.getString("street_number");
        String city = rs.getString("city");
        String county = rs.getString("county");

        Address address = Address.builder()
                .id(id)
                .streetName(streetName)
                .streetNumber(streetNumber)
                .city(city)
                .county(County.valueOf(county))
                .build();

        return (T) address;
    }

    @Override
    public void save(T entity) {
        try (Connection connection = openConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO address(STREET_NAME, STREET_NUMBER," +
                    "CITY, COUNTY) VALUES(?, ?, ?, ?)")) {
            stmt.setString(1, entity.getStreetName());
            stmt.setString(2, entity.getStreetNumber());
            stmt.setString(3, entity.getCity());
            stmt.setString(4, entity.getCounty().name());
            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }

    public T returnLast(){
        try(Connection conn = openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT address.id, address.street_name," +
                    " address.street_number, address.city, address.county  FROM address ORDER BY id DESC LIMIT 1")) {
            if(rs.next()){
                return extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Nema niti jedne adrese u bazi.");
            }
        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }
}
