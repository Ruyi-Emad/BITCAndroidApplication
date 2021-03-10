package com.example.haizi.Model;

public class food_list_model {

    String foodName;
    String foodDescription;
    String howManyPersonsCanEatIt;
    String foodPrice;
    String imageUri;
    String category;
    String id;

    public food_list_model()
    {
    }

    public food_list_model(String foodName, String foodDescription, String howManyPersonsCanEatIt, String foodPrice, String imageUri, String category, String id) {
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.howManyPersonsCanEatIt = howManyPersonsCanEatIt;
        this.foodPrice = foodPrice;
        this.imageUri = imageUri;
        this.category = category;
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getHowManyPersonsCanEatIt() {
        return howManyPersonsCanEatIt;
    }

    public void setHowManyPersonsCanEatIt(String howManyPersonsCanEatIt) {
        this.howManyPersonsCanEatIt = howManyPersonsCanEatIt;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}