package com.sgp95.santiago.helpuadmin.model;



public class User {

    private String Code;
    private String Email;
    private String FirstName;
    private String Image;
    private String LastName;

    public User(){}

    public User(String email, String firstName, String image, String lastName) {
        Email = email;
        FirstName = firstName;
        Image = image;
        LastName = lastName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
