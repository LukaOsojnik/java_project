package hr.javafx.realestate.javafxmanagementsystem.model;

import hr.javafx.realestate.javafxmanagementsystem.DbRepository.LeaseRepositoryDatabase;

public class NotificationService implements Runnable {
    private LeaseRepositoryDatabase<LeaseAgreement> leaseRepository;
    private boolean isRunning = true;

    public NotificationService() {
        this.leaseRepository = new LeaseRepositoryDatabase<>();
    }

    @Override
    public void run() {

    }


}