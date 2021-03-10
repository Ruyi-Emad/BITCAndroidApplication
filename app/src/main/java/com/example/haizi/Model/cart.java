package com.example.haizi.Model;

public class cart {

   private String plateName , price ,quantity , category ,Pid ;

    public cart() {
    }

    public cart(String plateName, String price, String quantity, String category, String pid) {
        this.plateName = plateName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        Pid = pid;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }
}
