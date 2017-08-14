package com.zapps.dailymoney.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.zapps.dailymoney.SMSParser;
import com.zapps.dailymoney.items.SMSItem;

import io.realm.Realm;

/**
 * Created by Zimincom on 2017. 8. 7..
 */

public class SMSReceiver extends BroadcastReceiver {
    SMSParser smsParser = new SMSParser();
    private static final String TAG = "MsgReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "msg " +
            "received", Toast.LENGTH_SHORT).show();
        Realm realm = Realm.getDefaultInstance();

        //get msg content 
        Bundle bundle = intent.getExtras();Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        for (int i = 0; i < messages.length; i++) {
            smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
        }

        String content = smsMessage[0].getMessageBody().toString();
        final SMSItem smsItem = smsParser.parseSMS(content);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (smsItem != null) {
                    SMSItem dbInfo = realm.createObject(SMSItem.class);
                    dbInfo.setMonth(smsItem.getMonth());
                    dbInfo.setDay(smsItem.getDay());
                    dbInfo.setWithdrawAmount(smsItem.getWithdrawAmount());
                    dbInfo.setBankName(smsItem.getBankName());
                    dbInfo.setItemName(smsItem.getItemName());
                }
            }
        });
    }
}
