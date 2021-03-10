package com.example.haizi.Model;

public class food_type_model {
    private  int image;
    public static food_list_model list_model;

   // private   int image ;
   private   String cardText;

    public food_type_model(int image, String cardText) {
        this.image = image;
        this.cardText = cardText;

    }


    public food_type_model() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText = cardText;
    }


}
