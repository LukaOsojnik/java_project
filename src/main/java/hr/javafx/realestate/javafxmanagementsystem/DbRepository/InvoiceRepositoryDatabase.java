package hr.javafx.realestate.javafxmanagementsystem.DbRepository;

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

public class InvoiceRepositoryDatabase<T extends Invoice> extends AbstractRepositoryDatabase<T>{

    @Override
    public T findById(Long id) {
        return null;
    }

    @Override
    public List<T> findAll() throws SQLException, IOException {

        List<T> invoiceList = new ArrayList<>();
        try(Connection conn = openConnection()){
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Invoice");
            while(rs.next()){
                invoiceList.add(extractFromResultSet(rs));
            }
        }
        return invoiceList;
    }
    public T extractFromResultSet(ResultSet rs) throws SQLException {
        LeaseRepositoryDatabase<LeaseAgreement> lrd = new LeaseRepositoryDatabase<>();
        Long id = rs.getLong("id");
        Long leaseID = rs.getLong("LEASE_ID");
        LocalDate dueDate = LocalDate.parse(rs.getString("DUE_DATE"));
        Boolean isPaid = rs.getBoolean("IS_PAID");
        BigDecimal price = rs.getBigDecimal("RENT_PRICE");

        return (T) new Invoice(id, lrd.findById(leaseID), dueDate, isPaid, price);
    }

    @Override
    public void save(T entity) {

        try(Connection conn = openConnection()){
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO invoice(lease_id," +
                    "due_date, is_paid, rent_price)" +
                    "VAlUES(?, ?, ?, ?)");
            pstmt.setLong(1, entity.getLease().getId());
            pstmt.setString(2, (entity.getLease().getSigningDate().plusMonths(1).toString()));
            pstmt.setString(3, "FALSE");
            pstmt.setBigDecimal(4, entity.getLease().getRentPrice());
            pstmt.executeUpdate();
        } catch(IOException | SQLException e) {
            logger.error("Pogreška kod spremanja računa u bazu podataka.");
            throw new RepositoryAccessException(e);
        }
    }

    public void updateStatus(Long id) throws SQLException, IOException {
        try(Connection conn = openConnection()){
            PreparedStatement pstmt = conn.prepareStatement("UPDATE invoice SET status = ? WHERE id = ?");
            pstmt.setString(1, "TRUE");
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        }
    }
}
