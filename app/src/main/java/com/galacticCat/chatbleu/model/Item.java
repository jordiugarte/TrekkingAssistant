package com.galacticCat.chatbleu.model;

public class Item {
    private String name;
    private String weight;
    private boolean check;

    public Item(String name, String weight) {
        this.name = name;
        this.weight = weight;
    }

    public Item() {

    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

}