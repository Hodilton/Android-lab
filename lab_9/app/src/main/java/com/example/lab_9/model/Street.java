package com.example.lab_9.model;

public class Street {
    private String name;
    private int id;

    public Street(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }
    public int getId() { return id; }

    public void setName(String value) { name = value; }
    public void setId(int value) { id = value; }
}