package com.example.DoAnJava.daos;

public class CartItem {

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    private String name;
    private double price;

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    private String imageList;



    // Constructor
    public CartItem(Long id, String imageList,String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageList= imageList;
    }
}
