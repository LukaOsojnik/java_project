package hr.javafx.realestate.javafxmanagementsystem.dbrepository;

import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.Invoice;
import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;



public class InvoiceRepositoryDatabase<T extends Invoice> extends AbstractRepositoryDatabase<T>{

    private static Boolean DATABASE_ACCESS_IN_PROGRESS = false;

    @Override
    public synchronized T findById(Long id) {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        Invoice invoice;
        try(Connection conn = openConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT invoice.id, invoice.lease_id," +
                    " invoice.due_date, invoice.is_paid, invoice.rent_price FROM invoice WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                invoice = extractFromResultSet(rs);
                return (T) invoice;
            }
            throw new RepositoryAccessException("Invoice with id " + id + " not found");
        } catch (SQLException | IOException _) {
            throw new RepositoryAccessException();
        }
    }

    @Override
    public synchronized List<T> findAll() {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        List<T> invoiceList = new ArrayList<>();
        try(Connection conn = openConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select invoice.id, invoice.lease_id," +
                    " invoice.due_date, invoice.is_paid, invoice.rent_price from Invoice");
            while(rs.next()){
                invoiceList.add(extractFromResultSet(rs));
            }
        } catch(SQLException | IOException _) {
            throw new RepositoryAccessException();
        } finally{
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
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
    public synchronized void save(T entity) {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        try(Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO invoice(lease_id," +
                    "due_date, is_paid, rent_price)" +
                    "VAlUES(?, ?, ?, ?)")) {
            pstmt.setLong(1, entity.getLease().getId());
            pstmt.setString(2, (entity.getLease().getSigningDate().plusDays(1).toString()));
            pstmt.setString(3, "FALSE");
            pstmt.setBigDecimal(4, entity.getLease().getRentPrice());
            pstmt.executeUpdate();
        } catch(IOException | SQLException e) {
            logger.error("Pogreška kod spremanja računa u bazu podataka.");
            throw new RepositoryAccessException(e);
        } finally{
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }
    }
    public synchronized void saveExistingInvoice(T entity) {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        try(Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO invoice(lease_id," +
                    "due_date, is_paid, rent_price)" +
                    "VAlUES(?, ?, ?, ?)")) {
            pstmt.setLong(1, entity.getLease().getId());
            pstmt.setString(2, (entity.getDueDate().plusDays(1).toString()));
            pstmt.setString(3, "FALSE");
            pstmt.setBigDecimal(4, entity.getLease().getRentPrice());
            pstmt.executeUpdate();
        } catch(IOException | SQLException e) {
            logger.error("Pogreška kod spremanja računa u bazu podataka.");
            throw new RepositoryAccessException(e);
        } finally{
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }
    }


    public synchronized void updateStatus(T entity) {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        try(Connection conn = openConnection();
            PreparedStatement pstmt = conn.prepareStatement("UPDATE invoice SET is_paid = ? WHERE id = ?")) {
            pstmt.setString(1, "TRUE");
            pstmt.setLong(2, entity.getId());
            pstmt.executeUpdate();
        } catch(IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally{
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }
    }
    public synchronized List<Invoice> nextMonthInvoice() {

        while(DATABASE_ACCESS_IN_PROGRESS) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        DATABASE_ACCESS_IN_PROGRESS = true;

        List<Invoice> invoices = new ArrayList<>();
        try(Connection conn = openConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT id, lease_id, due_date, is_paid, rent_price " +
                    "FROM invoice i WHERE due_date = (SELECT MAX(due_date) " +
                    "    FROM invoice WHERE lease_id = i.lease_id) AND due_date <= CURRENT_DATE;");
            while(rs.next()){
                invoices.add(extractFromResultSet(rs));
            }
        } catch(IOException | SQLException e) {
            throw new RepositoryAccessException(e);
        } finally{
            DATABASE_ACCESS_IN_PROGRESS = false;
            notifyAll();
        }
        return invoices;
    }
}
