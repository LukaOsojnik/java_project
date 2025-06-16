package hr.javafx.realestate.javafxmanagementsystem.dbrepository;

import hr.javafx.realestate.javafxmanagementsystem.exception.EmptyRepositoryResultException;
import hr.javafx.realestate.javafxmanagementsystem.exception.RepositoryAccessException;
import hr.javafx.realestate.javafxmanagementsystem.model.InboxMessage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class InboxRepositoryDatabase<T extends InboxMessage> extends AbstractRepositoryDatabase<T> {

    private static final String ERROR_MESSAGE = "Pogreška pri spajaju na bazu.";

    @Override
    public T findById(Long id) {
        try(Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT inbox.id, inbox.invoice_id" +
                    ", inbox.message, inbox.reminder_date FROM inbox WHERE id = ?")){
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return extractFromResultSet(rs);
            }
            else{
                throw new EmptyRepositoryResultException("Inbox message with id " + id + " not found");
            }
        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }

    @Override
    public List<T> findAll() {
        List<T> inboxMessages = new ArrayList<>();
        try(Connection conn = openConnection();
            Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT inbox.id, inbox.invoice_id" +
                    ", inbox.message, inbox.reminder_date FROM inbox");
            while(rs.next()) {
                inboxMessages.add(extractFromResultSet(rs));
            }
            return inboxMessages;
        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }
    public T extractFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long invoiceId = rs.getLong("invoice_id");
        String message = rs.getString("message");
        LocalDate reminderDate = LocalDate.parse(rs.getString("reminder_date"));
        return (T) new InboxMessage(id, invoiceId, message, reminderDate);
    }

    @Override
    public void save(T entity) {
        try(Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO inbox(invoice_id, message, reminder_date) VALUES (?,?,?)")){
            stmt.setLong(1, entity.getInvoiceId());
            stmt.setString(2, entity.getMessage());
            stmt.setString(3, LocalDateTime.now().plusDays(1).toLocalDate().toString());
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }

    public List<InboxMessage> checkForUnpaid() {
        List<InboxMessage> inboxList = new ArrayList<>();
        try(Connection conn = openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, invoice_id, message, reminder_date FROM inbox i " +
                    "WHERE reminder_date = (SELECT MAX(reminder_date) FROM inbox " +
                    "    WHERE invoice_id = i.invoice_id) " +
                    "AND reminder_date <= CURRENT_DATE;")){
            while(rs.next()) {
                inboxList.add(extractFromResultSet(rs));
            }
        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
        return inboxList;
    }

    public void clearMail() {
        try(Connection conn = openConnection();
            PreparedStatement stmt = conn.prepareStatement("delete inbox")){
            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            logger.error(ERROR_MESSAGE);
            throw new RepositoryAccessException(e);
        }
    }


}