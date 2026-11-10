package com.suphawinee.financeManagement.models;

public class Plate {
    private final int id;
    private String name;
    private double price;
    private String fileName;
    private int quantity;

    public Plate(int id, String name, double price, String fileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.fileName = fileName;
        this.quantity = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity() {
        this.quantity++;
    }

    public void desQuantity() {
        if(this.quantity > 0) {
            this.quantity--;
        }
    }

    public void setQuantity(int quantity) {
        if(quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public double getSubTotal() {
        return price * quantity;
    }
}
