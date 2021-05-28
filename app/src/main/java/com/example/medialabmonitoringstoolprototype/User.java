package com.example.medialabmonitoringstoolprototype;

public class User {

    public String name;
    public String email;
    public String age;
    public Integer radius;

    public User() {

    }

    public User( String name, String email, String age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.radius = 0;
    }
}
