package com.example.mytriggersms.SmsTrigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.mytriggersms.Controller.MyHttpRequest;
import com.example.mytriggersms.DBConnection.DAOComingSms;
import com.example.mytriggersms.DBConnection.DAOsms;
import com.example.mytriggersms.entity.ComingSms;
import com.example.mytriggersms.entity.Sms;

import java.util.ArrayList;
import java.util.List;

public class SmsTrigger extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(final Context context, Intent intent) {
        MyHttpRequest myHttpRequest = new MyHttpRequest(context);
        final DAOComingSms db = new DAOComingSms(context);
        List<Sms> smsListesi = new ArrayList<>();
        DAOsms DBsms = new DAOsms(context);
        smsListesi = DBsms.getsmsInfo(1);
        ComingSms Csms;

        try {
            Bundle bundle = intent.getExtras();
            Object[] pdusObj = (Object[]) bundle.get("pdus");
            for (int i = 0; i < pdusObj.length; i++) {
                SmsMessage message = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                String sender = message.getDisplayOriginatingAddress();
                String content = message.getDisplayMessageBody();
                //Toast.makeText(context, "Gönderen : " + sender + "\nmesaj: " + content, Toast.LENGTH_LONG).show();
                for (Sms s : smsListesi) {
                    String[] tittles = s.getSmsTitle().split(",");
                    if (s.getStatus().equals("1")) {
                        for (int j = 0; j < tittles.length; j++) {
                            String temp = tittles[j];
                            if (temp.equals(sender)) {
                                Csms = new ComingSms(sender, content);
                                db.InsertComingSms(Csms);
                                //Toast.makeText(context, "Mesaj Kaydedildi Count : " + db.dabatableCount(), Toast.LENGTH_LONG).show();
                                myHttpRequest.verileriYolla(sender, content, s.getPassword(), new MyHttpRequest.ResultListener() {
                                    @Override
                                    public void onReady(boolean isSuccess, String result) {
                                        if (isSuccess) {
                                            //Toast.makeText(context, "Mesaj Gönderildi", Toast.LENGTH_LONG).show();
                                            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        //Toast.makeText(context, "Programı aktif hale getirin!!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SmsTrigger", "Exception" + e);

        }


    }
}