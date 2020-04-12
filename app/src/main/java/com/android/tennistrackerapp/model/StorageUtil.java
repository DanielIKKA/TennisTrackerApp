package com.android.tennistrackerapp.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


// --------------------------------------------
// SOURCE :
// https://stackoverflow.com/questions/17674634/saving-and-reading-bitmaps-images-from-internal-memory-in-android
// --------------------------------------------
public class StorageUtil {

    private static final String IMAGE_DIRECTORY = "imageDir";

    private Context context;

    public StorageUtil(Context context) {
        this.context = context;
    }

    public String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());

        File directory = cw.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, name+".png");

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(fos).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path) {
        File f = new File(path, "profile.jpg");
        return BitmapFactory.decodeFile(path);
    }
}
