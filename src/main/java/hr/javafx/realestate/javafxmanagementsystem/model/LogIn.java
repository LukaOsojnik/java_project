package hr.javafx.realestate.javafxmanagementsystem.model;

public class LogIn {
    private final String email;
    private String role;


    public LogIn(String email, String role){
        this.email = email;
        this.role = role;
    }
    public String getEmail() {
        return email;
    }

    public String getRole(){
        return role;
    }
}
