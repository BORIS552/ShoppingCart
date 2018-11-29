package com.ssd.boris.shoppingcart.usermodel;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String mobile;

   public User(){

    }

    public User(String name, String email, String password, String mobile){
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public User(int id, String name, String email, String password, String mobile){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getMobile() {
        return mobile;
    }

}
