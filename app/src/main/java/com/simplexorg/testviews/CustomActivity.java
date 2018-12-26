package com.simplexorg.testviews;

import android.util.Log;
import android.widget.ImageView;

import com.simplexorg.customviews.activity.ZoomViewPagerActivity;

public class CustomActivity extends ZoomViewPagerActivity {
    private static final String TAG = CustomActivity.class.getSimpleName();

    @Override
    public void loadImageIntoView(String uri, ImageView view) {
        Log.d(TAG, "Loading images in activity");
        switch(uri) {
            case "0": view.setImageResource(R.drawable.test_image); break;
            case "1": view.setImageResource(R.drawable.test_image1); break;
            case "2": view.setImageResource(R.drawable.test_image2); break;
            case "3": view.setImageResource(R.drawable.test_image3); break;
            case "4": view.setImageResource(R.drawable.test_image4); break;
        }
    }
}