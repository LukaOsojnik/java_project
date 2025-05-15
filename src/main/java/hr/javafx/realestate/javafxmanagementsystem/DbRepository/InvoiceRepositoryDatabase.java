package hr.javafx.realestate.javafxmanagementsystem.DbRepository;

import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class InvoiceRepositoryDatabase<T extends LeaseAgreement> extends AbstractRepositoryDatabase<T>{

    @Override
    public T findById(Long id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return List.of();
    }

    @Override
    public void save(T entity) {
        LeaseRepositoryDatabase<LeaseAgreement> lrd = new LeaseRepositoryDatabase<>();

        try(Connection conn = openConnection()){
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO invoice(lease_id," +
                    "due_date, is_paid, rent_price)" +
                    "VAlUES(?, ?, ?, ?)");
            pstmt.setLong(1, entity.getId());
            pstmt.setString(2, entity.getSigningDate().toString());
            pstmt.setString(3, "FALSE");
            pstmt.setBigDecimal(4, entity.getRentPrice());
            pstmt.executeUpdate();
        } catch(IOException | SQLException e) {
            logger.error("Pogreška kod spremanja računa u bazu podataka.");
            throw new RepositoryAccessException(e);
        }
    }
}
