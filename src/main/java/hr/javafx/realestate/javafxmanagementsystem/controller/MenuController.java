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

    public void showEditLease(LeaseAgreement entity) throws IOException {
        FXMLLoader loader = new FXMLLoader(RealEsteteApplication.class.getResource("editLeaseScreen.fxml"));

        Scene scene = new Scene(loader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Uređivanje ugovora");
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

    public void showInvoiceScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("invoiceScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Pregled poruka");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();
    }

    public void showPaidInvoice() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RealEsteteApplication.class.getResource("invoicePaidScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 924, 611);
        RealEsteteApplication.getMainStage().setTitle("Pregled poruka");
        RealEsteteApplication.getMainStage().setScene(scene);
        RealEsteteApplication.getMainStage().show();

    }









}
