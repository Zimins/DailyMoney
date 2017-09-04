package com.zapps.dailymoney;

import android.util.Log;

import com.zapps.dailymoney.items.SMSItem;

/**
 * Created by Zimincom on 2017. 8. 7..
 */
public class SMSParser {

    private final int cardIndex = 1;
    private final int dateIndex = 3;
    private final int priceIndex = 4;
    private final int companyIndex = 5;

    public SMSItem parseSMS(String msgContent) {

        String[] tokens = msgContent.split("\n");

        String cardName = tokens[cardIndex];
        // TODO: 2017. 9. 4. 날짜 동적으로
        int year = 2017;
        int month = getMonth(tokens[dateIndex]);
        int day = getDay(tokens[dateIndex]);
        int price = getPrice(tokens[priceIndex]);
        String companyName = tokens[companyIndex];

        return new SMSItem(cardName, month, day, companyName, price);
    }

    private int getDay(String token) {
        int startIdx = token.indexOf('/')+1;
        int endIdx = startIdx + 2;
        String dayString = token.substring(startIdx, endIdx);
        Log.d("parser", dayString);
        if (dayString.startsWith("0")) {
            dayString = dayString.replace("0","");
        }
        return Integer.parseInt(dayString);
    }

    private int getMonth(String token) {
        int startIdx = 0;
        int endIdx = token.indexOf('/');
        String monthString = token.substring(startIdx, endIdx);
        if (monthString.startsWith("0")) {
            monthString = monthString.substring(1);
        }
        return Integer.parseInt(monthString);
    }

    private int getPrice(String token) {


        int startIdx = 0;
        int endIdx = token.indexOf('원');


        return Integer.parseInt(token.substring(startIdx, endIdx).replace(",",""));
    }


}