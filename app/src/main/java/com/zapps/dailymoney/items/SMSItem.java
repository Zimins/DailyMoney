package com.zapps.dailymoney.items;

import io.realm.RealmObject;

public class SMSItem extends RealmObject{
    private int month;
    private int day;
    private int withdrawAmount;
    private int imageSrc;
    private String itemName;
    private String bankName;
    private String tags;
    private String location;
    public boolean isDetail;


    public SMSItem(){}
    public SMSItem(String itemName, int price) {
        this.itemName = itemName;
        this.withdrawAmount = price;
    }

    public SMSItem(String bankName, int month, int day, String itemName, int price) {
        this.bankName = bankName;
        this.month = month;
        this.day = day;
        this.itemName = itemName;
        this.withdrawAmount = price;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setWithdrawAmount (int withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public int getWithdrawAmount() {
        return withdrawAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
