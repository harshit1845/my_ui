package com.example.makesurest;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Helpers {

    public static String GetFinancialYear() {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        int months = Integer.parseInt(dateFormat.format(date));
        String new_date = "";
        Calendar cal = Calendar.getInstance();
        String current_year = String.valueOf(cal.get(Calendar.YEAR));
        if (months > 3) {
            cal.add(Calendar.YEAR, 1); // to get previous year add -1
            String nextYear = String.valueOf(cal.get(Calendar.YEAR));
            new_date = current_year + "-" + nextYear;
        } else {
            cal.add(Calendar.YEAR, -1); // to get previous year add -1
            String nextYear = String.valueOf(cal.get(Calendar.YEAR));
            new_date = nextYear + "-" + current_year;
        }

        Log.d("Month", dateFormat.format(date));
        return new_date;
    }

    public static void isPhone(final Context context) { // checks the device as to allow to install or not

        String tpdevice = "";

        TelephonyManager manager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            tpdevice = "Tablet";
        } else {
            tpdevice = "Mobile";
        }


        //    if(getDeviceName().toString().trim().equals("Xiaomi Redmi 5A")){
//        if (getDeviceName().toString().trim().equals("Zebra Technologies TC20") || getDeviceName().toString().trim().equals("Zebra Technologies TC57") || getDeviceName().toString().trim().equals("Zebra Technologies TC21") || getDeviceName().toString().trim().equals("Xiaomi Redmi Note 4") || getDeviceName().toString().trim().equals("Samsung SM-N950N") || getDeviceName().toString().trim().equals("Samsung SM-G955F") || getDeviceName().toString().trim().equals("Zebra Technologies MC36") || getDeviceName().toString().trim().equals("Motorola Solutions MC32N0") || getDeviceName().toString().trim().equals("Motorola Solutions MC32NO") || getDeviceName().toString().trim().equals("Samsung SM-M315F") || getDeviceName().toString().trim().equals("sdk_gphone_x86_arm") || getDeviceName().toString().trim().equals("Pixel_3a_API_30_x86") || getDeviceName().toString().trim().equals("SEUIC AUTOID9N") || getDeviceName().toString().trim().equals("CipherLab RS35") || getDeviceName().toString().trim().equals("Google sdk_gphone_x86")) {
//
//            //  if(getDeviceName().toString().trim().equals("Zebra Technologies MC36")||getDeviceName().toString().trim().equals("Motorola Solutions MC32N0")||getDeviceName().toString().trim().equals("Xiaomi Redmi 5A")){
//            //   if(getDeviceName().toString().trim().equals("Zebra Technologies MC36")||getDeviceName().toString().trim().equals("Motorola Solutions MC32N0")||getDeviceName().toString().trim().equals("Motorola Solutions MC32NO")||getDeviceName().toString().trim().equals("Motorola Moto E (4) Plus")){
//            //    if(true){
//        }
//        else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//            builder.setTitle(tpdevice + " Exception has occurred!");
//            builder.setMessage(getDeviceName() + " The following error messages and descriptions provide details about enroll Android devices by using unknown Mobile Enrollment.");
//            builder.setCancelable(false);
//
//
//            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
//
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                    // Do nothing
//                    ((Activity) context).finish();
//                }
//            });
//
//            AlertDialog alert = builder.create();
//            alert.show();
//        }
    }

    public static String getDeviceName() { // gets the device name of which this app has been installed
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) { // capitalizes the model name
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static void ScanOk() { // scanner will generate the single beep
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 150);
    }

    public static void ScanError() { // scanner will generate the double beep
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);
    }

    public static void ScanCaseCompleted() { // scanner will generate the single continuous beep
        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen1.startTone(ToneGenerator.TONE_DTMF_S, 1000);
    }


}
