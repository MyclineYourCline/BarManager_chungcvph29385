package com.example.barmanage.modle;

import java.io.Serializable;

public class importedItem implements Serializable {
    private String  drinkPrice, dateAdd,unitCount,receiptsID;
    private int  drinkID;

    public importedItem(String drinkPrice, String dateAdd, String unitCount, String receiptsID, int drinkID) {
        this.drinkPrice = drinkPrice;
        this.dateAdd = dateAdd;
        this.unitCount = unitCount;
        this.receiptsID = receiptsID;
        this.drinkID = drinkID;
    }

    public importedItem() {
    }
    public double sumMonny(){
        return Double.parseDouble(drinkPrice.trim())* Double.parseDouble(unitCount.trim());
    }

    public String getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(String drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public String getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

    public String getReceiptsID() {
        return receiptsID;
    }

    public void setReceiptsID(String receiptsID) {
        this.receiptsID = receiptsID;
    }

    public int getDrinkID() {
        return drinkID;
    }

    public void setDrinkID(int drinkID) {
        this.drinkID = drinkID;
    }
}
