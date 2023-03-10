package com.example.barmanage.modle;

public class drinks {
    private String dinkName;
    private String drinkID;
    private int unitID;


    public drinks() {
    }

    public String getDinkName() {
        return dinkName;
    }

    public void setDinkName(String dinkName) {
        this.dinkName = dinkName;
    }

    public String getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(String drinkID) {
        this.drinkID = drinkID;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public drinks(String dinkName, String drinkID, int unitID) {
        this.dinkName = dinkName;
        this.drinkID = drinkID;
        this.unitID = unitID;
    }
}
