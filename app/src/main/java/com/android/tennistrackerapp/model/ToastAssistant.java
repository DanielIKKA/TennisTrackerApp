package com.android.tennistrackerapp.model;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastAssistant {

    public static ToastAssistant generalInstance = new ToastAssistant();

    private Context context;

    public void displayShortToast(String message) {
        Toast toast = Toast.makeText(this.context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }

    public void displayLongToast(String message) {
        Toast toast = Toast.makeText(this.context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }

    public static void initToastAssistant(Context context) {
        generalInstance.context = context;
    }
}
