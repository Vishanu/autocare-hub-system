package com.project.model;

public class Vehicle {
    private int id;
    private int userId;
    private String type;
    private String model;
    private String number;
    private String ownerName;

    public Vehicle() {
    }

    public Vehicle(int id, int userId, String type, String model, String number) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.model = model;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
