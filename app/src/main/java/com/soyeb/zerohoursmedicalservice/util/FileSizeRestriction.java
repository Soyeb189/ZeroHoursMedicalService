package com.soyeb.zerohoursmedicalservice.util;

import android.app.Activity;

import com.bd.ehaquesoft.sweetalert.SweetAlertDialog;

import java.io.File;

public class FileSizeRestriction {

    public static int FILE_SIZE_KB = 0;//KiloBytes
    public static double FILE_SIZE_MB = 0;//MegaBytes

    public static int DEFAULT_FILE_SIZE_KB = 2000;//KiloBytes
    public static int MAX_FILE_SIZE_KB = 2000;//KiloBytes

    public static double DEFAULT_FILE_SIZE_MB = 2;//MegaBytes
    public static double MAX_FILE_SIZE_MB = 2;//MegaBytes

    public static int VALIDATION_KB = 1;
    public static int VALIDATION_MB = 2;
    public static int VALIDATION_TYPE = VALIDATION_MB;

    public static boolean isValidFileSize(String FILE_NAME){

        File file = new File(FILE_NAME);
        if (!file.exists() || !file.isFile()) return false;

        if(VALIDATION_TYPE == VALIDATION_MB) {
            FILE_SIZE_MB = getFileSizeMegaBytes(file);
            if (FILE_SIZE_MB <= MAX_FILE_SIZE_MB) {
                return true;
            } else {
                return false;
            }

        }else{
            FILE_SIZE_KB = getFileSizeKiloBytes(file);
            if (FILE_SIZE_KB <= MAX_FILE_SIZE_KB) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void showDocSizeError(Activity activity){

        if(VALIDATION_TYPE == VALIDATION_MB) {

            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("\nSelected file size is " + FILE_SIZE_MB + " MB\nDocument size more then " + FileSizeRestriction.MAX_FILE_SIZE_MB + " MB not allowed!")
                    .setConfirmText("Retry")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();

        }else {
            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("\nSelected file size is " + FILE_SIZE_KB + " KB\nDocument size more then " + FileSizeRestriction.MAX_FILE_SIZE_KB + " KB not allowed!")
                    .setConfirmText("Retry")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        }

    }

    private static double getFileSizeMegaBytes(File file) {
        //return (double) file.length() / (1024 * 1024) + " mb";
        return (double) file.length() / (1024 * 1024);
        //return (int) file.length() / (1024 * 1024);
    }

    private static int getFileSizeKiloBytes(File file) {
        //return (double) file.length() / 1024 + "  kb";
        return (int) file.length() / 1024;
    }

    private static int getFileSizeBytes(File file) {
        //return file.length() + " bytes";
        return (int) file.length();
    }

}
