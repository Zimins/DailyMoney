package com.zapps.dailymoney;

/**
 * Created by Zimincom on 2017. 9. 4..
 */

public class DistinctInfo {

    // TODO: 2017. 9. 4. name, package

    private String itemName;
    private int sum;

    public DistinctInfo(String itemName, int sum) {
        this.itemName = itemName;
        this.sum = sum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
