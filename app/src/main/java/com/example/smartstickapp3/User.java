package com.example.smartstickapp3;

public class User {
    String Name,Passw,Email;
    public User(){ }

    public User(String name, String passw, String email) {
        Name=name;
        Passw=passw;
        Email=email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) { Name = name; }

    public String getPassw() {
        return Passw;
    }

    public void setPassw(String passw) {
        Passw=passw;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email=email;
    }
}
