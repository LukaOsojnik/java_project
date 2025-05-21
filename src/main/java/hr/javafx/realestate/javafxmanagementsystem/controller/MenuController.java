package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication;
import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class MenuController<T extends LeaseAgreement> {
    public void showSearchPropertyScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("searchPropertyScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Pretraga nekretnina");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();

    }
    public void showSearchUserPropertyScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("searchUserPropertyScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Pretraga nekretnina");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();

    }

    public void showAddPropertyScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("addPropertyScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Spremanje imovine");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();
    }

    public void showSearchLeaseScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("searchLeaseScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Pretraga ugovora");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();

    }

    public void showAddLeaseScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("addLeaseScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Spremanje ugovora");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();
    }

    public void showEditLease(T entity) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("editLeaseScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 924, 611);

        EditLeaseController controller = fxmlLoader.getController();
        controller.setEmail(entity.getTenant().getEmail());
        controller.setPhone(entity.getTenant().getContactNumber());
        controller.setNewPrice(entity.getRentPrice().toString());
        controller.passLeaseAgreement(entity);

        RealEsteteApplication.getMainStage().setTitle("Spremanje ugovora");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();
    }

    public void showInboxScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("inboxScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Pregled poruka");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();
    }






}
