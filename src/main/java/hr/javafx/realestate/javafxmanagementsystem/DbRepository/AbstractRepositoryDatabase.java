package hr.javafx.realestate.javafxmanagementsystem.DbRepository;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Služi za nasljeđivanje ostalih repozitorija u projektu.
 * Unutar klase se sprema statična metoda openConnection koja se poziva nakon svakog dohvata ili spremanja
 * podataka unutar baze.
 * @param <T>
 */

public abstract class AbstractRepositoryDatabase<T> {
    private static Path databasePath = Path.of("src/main/java/hr/javafx/realestate/javafxmanagementsystem/DbRepository/dataBase.properties");

    /**
     * Služi za kreiranje konekcije između programa i baze.
     * @return vraća connection s bazom podataka
     * @throws IOException poziva se u slučaju problema s dohvatom datoteke dataBase.properties
     * @throws SQLException poziva u slučaju problema s bazom podataka
     */

    static Connection openConnection() throws IOException, SQLException {

        Properties props = new Properties();
        try (FileReader reader = new FileReader(databasePath.toFile())) {
            props.load(reader);
            return DriverManager.getConnection(
                    props.getProperty("urlBase"),
                    props.getProperty("username"),
                    props.getProperty("password"));
        }
    }

    /**
     * Vraća objekt koji sadrži id iz parametra
     * @param id id objekta koji se traži
     * @return vraža objekt traženog id-a
     */

    public abstract T findById(Long id);

    /**
     * Vraća genericku listu čije podatke uzima iz baze podataka
     * @return
     */
    public abstract List<T> findAll();

    /**
     * Prima objekt entity te sprema isti u bazu podataka
     * @param entity
     */
    public abstract void save(T entity);
}
