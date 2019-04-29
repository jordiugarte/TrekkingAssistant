package com.galacticCat.chatbleu;

public class ItemList {

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    private String name;
    private int weight;

    public ItemList (String name, int weight){
        this.name = name;
        this.weight = weight;
    }


}
