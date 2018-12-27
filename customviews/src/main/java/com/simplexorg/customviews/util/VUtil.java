package com.simplexorg.customviews.util;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.Toast;

import com.simplexorg.customviews.model.ImageDataHolder;

import java.util.ArrayList;
import java.util.List;

public class VUtil {
    public static final String EXTRA_TRANSITION_NAME = "transition";
    public static final String EXTRA_IMAGE_DATA = "imageData";

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

    public String genRandomString(int len) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = (char) ((int) (Math.random() * 255));
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public ArrayList<ImageDataHolder> getImageDataHolderList(List<String> imageUris) {
        ArrayList<ImageDataHolder> imageDataHolderList = new ArrayList<>();
        int transitionNameLength = 8;
        for (String imageUri : imageUris) {
            imageDataHolderList.add(new ImageDataHolder(imageUri, VUtil.getInstance().genRandomString(transitionNameLength)));
        }
        return imageDataHolderList;
    }

    public static VUtil getInstance() {
        if (mUtil == null) {
            mUtil = new VUtil();
        }
        return mUtil;
    }
}
