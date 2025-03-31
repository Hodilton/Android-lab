package com.example.lab_9.model;

public class Street {
    private String name;
    private String city;
    private int id;

    public Street(String name, String city, int id) {
        this.name = name;
        this.city = city;
        this.id = id;
    }

    public String getName() { return name; }
    public String getCity() { return city; }
    public int getId() { return id; }

    public void setName(String value) { name = value; }
    public void setCity(String value) { city = value; }
    public void setId(int value) { id = value; }
}