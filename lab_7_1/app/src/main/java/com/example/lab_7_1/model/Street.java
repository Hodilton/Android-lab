package com.example.lab_7_1.model;

public class Street {
    private String name;
    private int length;
    private int imageResourceId;

    public Street(String name, int length, int imageResourceId) {
        this.name = name;
        this.length = length;
        this.imageResourceId = imageResourceId;
    }

    public String getName() { return name; }
    public int getLength() { return length; }
    public int getImageResourceId() { return imageResourceId; }

    public void setName(String value) { name = value; }
    public void setLength(int value) { length = value; }
    public void setImageResourceId(int value) { imageResourceId = value; }
}