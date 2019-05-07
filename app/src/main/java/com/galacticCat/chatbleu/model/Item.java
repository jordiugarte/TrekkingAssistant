package com.galacticCat.chatbleu.model;

import com.google.gson.annotations.Expose;

public class Item {

    @Expose
    private String name;

    @Expose
    private int weight;

    @Expose
    private boolean check;

    public Item(String name, int weight, boolean check) {
        this.name = name;
        this.weight = weight;
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isCheck() {
        return check;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
