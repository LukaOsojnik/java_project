package hr.javafx.realestate.javafxmanagementsystem.model;
import java.time.LocalDate;
import java.util.List;

public class NotificationService implements Runnable {
    private List<Tenant> tenants;

    public NotificationService(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    @Override
    public void run() {
        while (true) {
            for (Tenant tenant : tenants) {
                if (shouldSendReminder(tenant)) {
                    sendReminder(tenant);
                }
            }
            try {
                Thread.sleep(86400000); // Run daily
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean shouldSendReminder(Tenant tenant) {
        LocalDate today = LocalDate.now();
        return tenant.equals(today);
    }

    private void sendReminder(Tenant tenant) {
        System.out.println("Reminder sent to: " + tenant.getEmail());
    }
}

