package hr.javafx.realestate.javafxmanagementsystem.model;

public abstract class Person extends Entity{

    private String firstName;
    private String lastName;
    private String contactNumber;
    private String email;

    protected Person(Long id, String firstName, String lastName, String contactNumber, String email) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
    }
    protected Person(String firstName, String lastName, String contactNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }
}
