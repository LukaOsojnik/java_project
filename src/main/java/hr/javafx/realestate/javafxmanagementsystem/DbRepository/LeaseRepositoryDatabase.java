package hr.javafx.realestate.javafxmanagementsystem.DbRepository;

import hr.javafx.realestate.javafxmanagementsystem.exception.EmptyRepositoryResultException;
import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;
import hr.javafx.realestate.javafxmanagementsystem.model.Property;
import hr.javafx.realestate.javafxmanagementsystem.model.Tenant;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class LeaseRepositoryDatabase<T extends LeaseAgreement> extends AbstractRepositoryDatabase<T> {
    @Override
    public T findById(Long id) {
        try(Connection conn = openConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM lease_agreement WHERE id = ?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return  extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Lease agreement with id " + id + " not found");
            }
        } catch (SQLException | IOException e) {
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public List<T> findAll() {
        List<T> leaseAgreementList = new ArrayList<>();
        try(Connection conn = openConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM lease_agreement");
            while(rs.next()) {
                leaseAgreementList.add(extractFromResultSet(rs));
            }
            return leaseAgreementList;

        }catch(SQLException | IOException e) {
            logger.error("Pogreška kod čitanja ugovora iz baze podataka.");
            throw new RepositoryAccessException(e);
        }
    }
    public T extractFromResultSet(ResultSet rs) throws SQLException {
        TenantRepositoryDatabase<Tenant> trd = new TenantRepositoryDatabase<>();
        PropertyRepositoryDatabase<Property> prd = new PropertyRepositoryDatabase<>();

        Long id = rs.getLong("ID");
        Long idTenant = rs.getLong("TENANT_ID");
        Long idProperty = rs.getLong("PROPERTY_ID");
        BigDecimal rentPrice = rs.getBigDecimal("RENT_PRICE");
        LocalDate signingDate = LocalDate.parse(rs.getString("SIGNING_DATE"));

        Tenant tenant = trd.findById(idTenant);
        Property property = prd.findById(idProperty);

        return (T) new LeaseAgreement(id, tenant, property, rentPrice, signingDate);
    }

    @Override
    public void save(T entity) {
        InvoiceRepositoryDatabase<Invoice> ird = new InvoiceRepositoryDatabase<>();

        try(Connection conn = openConnection()){
            PreparedStatement pst = conn.prepareStatement("INSERT INTO LEASE_AGREEMENT(TENANT_ID, " +
                    "PROPERTY_ID, RENT_PRICE, SIGNING_DATE) VALUES (?,?,?,?)");
            pst.setLong(1, entity.getTenant().getId());
            pst.setLong(2, entity.getProperty().getId());
            pst.setBigDecimal(3, entity.getRentPrice());
            pst.setString(4, entity.getSigningDate().toString());
            pst.executeUpdate();

            LeaseAgreement lease = returnLast();
            Invoice invoice = new Invoice(lease, lease.getSigningDate());
            ird.save(invoice);
        } catch(IOException | SQLException e) {
            logger.error("Pogreška kod spremanja ugovora u bazu podataka.");
            throw new RepositoryAccessException(e);
        }
    }

    public T returnLast() {
        try(Connection conn = openConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM lease_agreement ORDER BY id DESC LIMIT 1");
            if(rs.next()){
                return extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Address not found");
            }
        } catch (SQLException | IOException e) {
            throw new RepositoryAccessException(e);
        }

    }

    public void updateLeaseAgreement(Long leaseId, String rentPrice) {
        try(Connection connection = openConnection()){
            PreparedStatement updateMeal =
                    connection.prepareStatement(
                            "UPDATE LEASE_AGREEMENT SET RENT_PRICE = ? WHERE ID = ?");
            updateMeal.setString(1, rentPrice);
            updateMeal.setLong(2, leaseId);
            updateMeal.executeUpdate();
        } catch(SQLException | IOException e) {
            logger.error("Pogreška kod uređivanja ugovora.");
            throw new RepositoryAccessException(e);
        }
    }
}
