package com.galacticCat.chatbleu.model;

public class Item {
    private String name;
    private String weight;
    private boolean check;

    public Item(String name, String weight, boolean check) {
        this.name = name;
        this.weight = weight;
        this.check = check;
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

    public void setCheck(boolean check) {
        this.check = check;
    }
}