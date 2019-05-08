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

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }
}