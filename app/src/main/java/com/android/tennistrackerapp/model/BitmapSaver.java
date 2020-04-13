package com.android.tennistrackerapp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;


// --------------------------------------------
// SOURCE :
// https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
// --------------------------------------------
public class BitmapSaver {

    public static byte[] bitmapToBytes(Bitmap bitmap, int percent_quality) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, percent_quality, stream);
        byte[] byteArray = stream.toByteArray();
        bitmap.recycle();

        return byteArray;
    }

    public static Bitmap byteToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0 ,bytes.length);
    }
}
