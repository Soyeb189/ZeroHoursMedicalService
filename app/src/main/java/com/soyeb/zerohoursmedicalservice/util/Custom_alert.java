package com.soyeb.zerohoursmedicalservice.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bd.ehaquesoft.sweetalert.SweetAlertDialog;


public class Custom_alert {

    public static SweetAlertDialog showProgressDialog(Activity activity) {
        SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Please wait...");
        pDialog.setCanceledOnTouchOutside(false);
        return pDialog;

    }

    public static SweetAlertDialog showProgressDialog(Activity activity, String text) {
        SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(""+text);
        pDialog.setCanceledOnTouchOutside(false);
        return pDialog;

    }


    public static void showInternetConnectionMessage(Activity activity) {


        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Internet Connection").setContentText("No Internet Connection").show();


    }

    public static void showErrorMessage(Activity activity, String message){

        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Oops!").setContentText(message).show();

    }

    public static void showWarningMessage(Activity activity, String message){

        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Warning!").setContentText(message).show();

    }

    public static void showSuccessMessage(Activity activity,String message){


        new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successful.")
                .setContentText(message).show();

    }

    public static void shwoCommingSoon(Activity activity, String message){

        new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE)
                .setContentText(message).show();


    }

    public static boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static void showInternetConnectionMessage(Activity activity,String lan){
        if(!((activity)).isFinishing()) {
            if ("BAN".equals(lan)) {
                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("ইন্টারনেট কানেকশান ").setContentText("কোনো ইন্টারনেট সংযোগ নেই!").show();
            } else {

                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText(" Internet connection").setContentText("No Internet Connection !").show();
            }
        }

    }

    public static void showErrorMessage(Activity activity,String lan,String message){
        if(!((activity)).isFinishing()) {
            if ("BAN".equals(lan)) {
                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("সতর্ক!").setContentText(message).show();
            } else {

                new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Attention!").setContentText(message).show();
            }
        }
    }


}
