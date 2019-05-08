package com.galacticCat.chatbleu.model;

public class Item {
    private String name;
    private String weight;

    public Item(String name, String weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }
}