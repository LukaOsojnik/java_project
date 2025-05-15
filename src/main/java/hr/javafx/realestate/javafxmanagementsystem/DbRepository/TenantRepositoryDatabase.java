package hr.javafx.realestate.javafxmanagementsystem.DbRepository;

import hr.javafx.realestate.javafxmanagementsystem.exception.EmptyRepositoryResultException;
import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.Tenant;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class TenantRepositoryDatabase<T extends Tenant> extends AbstractRepositoryDatabase<T> {


    @Override
    public T findById(Long id) {
        try(Connection conn = openConnection()){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tenant WHERE id = ?");
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Tenant with id " + id + " not found");
            }
        } catch(IOException | SQLException e){
            logger.error("Pogreška s pronalaskom id-a kod najmoprimca.");
            throw new RepositoryAccessException(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() {
        List<T> listTenant = new ArrayList<>();
        try(Connection conn = openConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tenant");
            while(rs.next()){
                listTenant.add(extractFromResultSet(rs));
            }
            return listTenant;

        }catch(IOException | SQLException e){
            logger.error("Pogreška kod čitanja najmoprimca iz baze.");
            throw new RepositoryAccessException(e.getMessage());
        }
    }
    public T extractFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("first_name");
        String surname = rs.getString("last_name");
        String number = rs.getString("contact_number");
        String email = rs.getString("email");

        return (T) new Tenant(id, name, surname, number, email);
    }

    @Override
    public void save(T entity) {
        try(Connection conn = openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO TENANT(FIRST_NAME, " +
                    "LAST_NAME, CONTACT_NUMBER, EMAIL) VALUES (?,?,?,?)");
            stmt.setString(1, entity.getFirstName());
            stmt.setString(2, entity.getLastName());
            stmt.setString(3, entity.getContactNumber());
            stmt.setString(4, entity.getEmail());
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            logger.error("Pogreška kod spremanja najmoprimca.");
            throw new RepositoryAccessException(e);
        }
    }

    public T returnLast(){
        try(Connection conn = openConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tenant ORDER BY id DESC LIMIT 1");
            if(rs.next()){
                return extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Tenant database is empty");
            }
        } catch (SQLException | IOException e) {
            throw new RepositoryAccessException(e);
        }
    }

    public void updateTenant(Long id, String email, String phoneNumber) {
        try(Connection connection = openConnection()){
            PreparedStatement updateTenant =
                    connection.prepareStatement(
                            "UPDATE TENANT SET CONTACT_NUMBER = ?, EMAIL = ? WHERE ID = ?");
            updateTenant.setString(1, phoneNumber);
            updateTenant.setString(2, email);
            updateTenant.setLong(3, id);
            updateTenant.executeUpdate();
        } catch(SQLException | IOException e) {
            logger.error("Pogreška kod uređivanja ugovora.");
            throw new RepositoryAccessException(e);
        }
    }
}
