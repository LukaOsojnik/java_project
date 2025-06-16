package hr.javafx.realestate.javafxmanagementsystem.controller;

import hr.javafx.realestate.javafxmanagementsystem.model.LeaseAgreement;
import hr.javafx.realestate.javafxmanagementsystem.model.Tenant;
import hr.javafx.realestate.javafxmanagementsystem.dbrepository.LeaseRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.dbrepository.TenantRepositoryDatabase;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class EditLeaseController {

    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField rentPriceTextField;

    String email;
    String phone;
    String rentPrice;
    private LeaseAgreement lease;

    public void initialize(){

    }
    public void passedData(LeaseAgreement lease){
        this.lease = lease;

        this.email = lease.getTenant().getEmail();
        this.phone = lease.getTenant().getContactNumber();
        this.rentPrice = lease.getRentPrice().toString();

        emailTextField.setText(this.email);
        phoneTextField.setText(this.phone);
        rentPriceTextField.setText(this.rentPrice);
    }
    public void editLease() {
        LeaseRepositoryDatabase<LeaseAgreement> leaseRepositoryDatabase = new LeaseRepositoryDatabase<>();
        TenantRepositoryDatabase<Tenant> tenantRepositoryDatabase = new TenantRepositoryDatabase<>();

        StringBuilder sb = new StringBuilder();
        if(!email.equals(emailTextField.getText())){
            tenantRepositoryDatabase.updateTenant(lease.getTenant().getId(),
                    lease.getTenant().getEmail(),
                    lease.getTenant().getContactNumber());
            sb.append("Uspješno uređivanje kontakta najmodavca!\n");
        }
        if(!phone.equals(phoneTextField.getText())){
            leaseRepositoryDatabase.updateLeaseAgreement(lease.getId(),
                    rentPriceTextField.getText());
            sb.append("Uspješno uređivanje broja mobitela!");
        }
        if(!rentPrice.equals(rentPriceTextField.getText())){
            leaseRepositoryDatabase.updateLeaseAgreement(lease.getId(),
                    rentPriceTextField.getText());
            sb.append("Uspješno uređivanje cijene rente!");
        }
        if(!sb.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uspješno uređivanje!");
            alert.setHeaderText("Uspješno ste uredili informacije na ugovoru.");
            alert.setContentText(sb.toString());
            alert.showAndWait();
        }


    }


}
