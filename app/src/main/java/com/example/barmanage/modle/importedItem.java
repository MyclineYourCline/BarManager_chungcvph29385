package com.example.barmanage.modle;

public class importedItem {
    private String drinkName, drinkPrice, unitCount, dateAdd;

    public importedItem(String drinkName, String drinkPrice, String unitCount, String dateAdd) {
        this.drinkName = drinkName;
        this.drinkPrice = drinkPrice;
        this.unitCount = unitCount;
        this.dateAdd = dateAdd;
    }
    public int sumMonny(){
        int sum = Integer.parseInt(unitCount.trim())*Integer.parseInt(drinkPrice.trim());
        return sum;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public String getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }
}
