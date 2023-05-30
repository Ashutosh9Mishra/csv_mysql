package com.csv_mysql.Model;


public class Order {
    private String phoneNumber;
    private String country;

    public Order() {
    }

    public Order(String phoneNumber, String country) {
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



}
