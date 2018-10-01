package com.example.saharsh.bechdo;

public class Cycle {

    private String Contact;
    private String Cost;
    private String Url;
    private String Model;

    public Cycle(){

    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model=model;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public Cycle(String contact, String cost, String url,String model) {
        Contact = contact;
        Cost = cost;
        Url = url;
        Model=model;
    }
}
