package com.simplexorg.customviews.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.Toast;

public class VUtil {
    private static VUtil mUtil;

    public void displayToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public boolean fileIsImage(String path) {
        BitmapFactory.Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outWidth != -1 && options.outHeight != -1;
    }

    public static VUtil getInstance() {
        if (mUtil == null) {
            mUtil = new VUtil();
        }
        return mUtil;
    }
}
