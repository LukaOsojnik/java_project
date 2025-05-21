package hr.javafx.realestate.javafxmanagementsystem.model;

import hr.javafx.realestate.javafxmanagementsystem.DbRepository.InboxRepositoryDatabase;
import hr.javafx.realestate.javafxmanagementsystem.DbRepository.LeaseRepositoryDatabase;

import java.time.LocalDate;
import java.util.List;

import static hr.javafx.realestate.javafxmanagementsystem.RealEsteteApplication.logger;

public class NotificationService implements Runnable {
    private LeaseRepositoryDatabase<LeaseAgreement> leaseRepository;
    private InboxRepositoryDatabase<InboxMessage> inboxRepository;
    private boolean isRunning = true;

    public NotificationService() {
        this.leaseRepository = new LeaseRepositoryDatabase<>();
        this.inboxRepository = new InboxRepositoryDatabase<>();
    }

    @Override
    public void run() {

    }


}