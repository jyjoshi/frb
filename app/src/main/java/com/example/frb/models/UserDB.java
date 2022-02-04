package com.example.frb.models;

public class UserDB {
    String firstName;
    String lastName;
    String password;
    String phone;
    Boolean isTeacher;

    public UserDB(){}

    public UserDB(String phone, String firstName, String lastName, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.isTeacher = false;
    }

    public UserDB(String phone, String firstName, String lastName, String password, Boolean isTeacher) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phone = phone;
        this.isTeacher = isTeacher;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public Boolean getIsTeacher(){ return isTeacher;}
}
