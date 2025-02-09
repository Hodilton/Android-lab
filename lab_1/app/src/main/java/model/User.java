package model;

import com.google.gson.Gson;

public class User {
    private String firstName;
    private String lastName;
    private int age;
    private float salary;

    public User(String firstName, String lastName, String age, String salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = Integer.parseInt(age);
        this.salary = Float.parseFloat(salary);
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public float getSalary() { return salary; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setAge(int age) { this.age = age; }
    public void setSalary(float salary) { this.salary = salary; }

    public static String toJson(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

    public static User fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
}