package com.zapps.dailymoney;

/**
 * Created by Zimincom on 2017. 8. 20..
 */

public class DayInfo {

    // TODO: 2017. 9. 4. 이름 변경

    int month;
    int day;
    int sumOfDay;

    public DayInfo(){}

    public DayInfo(int month, int day, int sumOfDay) {
        this.month = month;
        this.day = day;
        this.sumOfDay = sumOfDay;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getSumOfDay() {
        return sumOfDay;
    }

    public void setSumOfDay(int sumOfDay) {
        this.sumOfDay = sumOfDay;
    }
}
