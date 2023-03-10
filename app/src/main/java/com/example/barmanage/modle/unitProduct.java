package com.example.barmanage.modle;

public class unitProduct {
   private String unitName;
   private String unitID;

    public unitProduct() {
    }

    public unitProduct(String unitName, String unitID) {
        this.unitName = unitName;
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
}
