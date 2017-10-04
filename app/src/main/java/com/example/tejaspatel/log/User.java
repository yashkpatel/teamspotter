package com.example.tejaspatel.log;

/**
 * Created by Tejas Patel on 17-Sep-17.
 */

public class User {
    private String username, name, email, mobileno, gender;
    private int id;

    public User( int id, String username, String name, String email, String mobileno, String gender) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.gender = gender;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public String getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }
}
