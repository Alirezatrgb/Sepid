package com.example.TBook.entity;

public class Products {
    private int id;
    private String name;
    private String Image;
    private long price;
    private String writer;
    private String information;

    public Products(int id, String name, String image, long price, String writer, String information) {
        this.id = id;
        this.name = name;
        Image = image;
        this.price = price;
        this.writer = writer;
        this.information = information;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    @Override
    public String toString() {
        return "Products{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Image='" + Image + '\'' +
                ", price=" + price +
                ", writer='" + writer + '\'' +
                ", information='" + information + '\'' +
                '}';
    }

    public Products(String name, String image, long price, String writer, String information) {
        this.name = name;
        Image = image;
        this.price = price;
        this.writer = writer;
        this.information = information;
    }
}
