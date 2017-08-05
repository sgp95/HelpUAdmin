package com.sgp95.santiago.helpuadmin.model;

public class Contacts {

    private String ContactId;
    private String FirstName;
    private String LastName;
    private String Cargo;
    private String Email;
    private String Telefono01;
    private String Telefono02;

    public String getContactId() {
        return ContactId;
    }

    public void setContactId(String contactId) {
        ContactId = contactId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelefono01() {
        return Telefono01;
    }

    public void setTelefono01(String telefono01) {
        Telefono01 = telefono01;
    }

    public String getTelefono02() {
        return Telefono02;
    }

    public void setTelefono02(String telefono02) {
        Telefono02 = telefono02;
    }
}
