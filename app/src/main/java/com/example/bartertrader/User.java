package com.example.bartertrader;

public class User {
    private String email, firstName, surname, phone, password, url;

    public User(String email, String firstName, String surname, String phone, String password, String url) {
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.phone = phone;
        this.password = password;
       this.url = url;

    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
