package com.example.barmanage.modle;

public class drinks {
    private String dinkName, unitName;

    public drinks(String dinkName, String unitName) {
        this.dinkName = dinkName;
        this.unitName = unitName;
    }

    public String getDinkName() {
        return dinkName;
    }

    public void setDinkName(String dinkName) {
        this.dinkName = dinkName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
