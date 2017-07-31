package com.zapps.dailymoney.items;

import android.location.Location;

/**
 * Created by Zimincom on 2017. 7. 31..
 */

public class SMSItem {
    private int month;
    private int day;
    private int price;
    private int imageSrc;
    private String itemName;
    private String bankName;
    private String[] tags;
    private Location location;

    public SMSItem(String itemName, int price) {
        this.itemName = itemName;
        this.price = price;
    }
}
