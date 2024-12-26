package com.example.testing1.Domain;

import java.io.Serializable;

public class ItemDomain implements Serializable {

    private int id;  // id tetap ada, tapi bisa di set otomatis
    private String title;
    private String pic;
    private String description;
    private Double fee;
    private String category;
    private boolean isAvailable;

    // Konstruktor tanpa parameter id (id otomatis diatur oleh database)
    public ItemDomain(String title, String pic, String description, Double fee, String category, boolean isAvailable) {
        this.id = 0;  // Atur id default ke 0, karena id akan diatur otomatis oleh database saat insert
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Konstruktor dengan parameter id (digunakan ketika sudah ada id yang diberikan)
    public ItemDomain(int id, String title, String pic, String description, Double fee, String category, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.category = category;
        this.isAvailable = isAvailable;
    }

    // Getter dan Setter untuk id, title, pic, description, fee, category, dan isAvailable

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
