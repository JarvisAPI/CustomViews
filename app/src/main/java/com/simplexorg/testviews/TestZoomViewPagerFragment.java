package com.simplexorg.testviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.simplexorg.customviews.fragment.ZoomViewPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 2018-12-25.
 * Tests the zoom view pager fragment.
 */

public class TestZoomViewPagerFragment extends AppCompatActivity {
    private static final String TAG = TestZoomViewPagerFragment.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_view_pager);

        List<String> imageUris = new ArrayList<>();
        imageUris.add("0");
        imageUris.add("1");
        imageUris.add("2");
        imageUris.add("3");
        imageUris.add("4");

        ZoomViewPagerFragment fragment = (ZoomViewPagerFragment) getSupportFragmentManager().findFragmentById(R.id.zoom_fragment);
        fragment.setImagesUris(imageUris);
        fragment.setTransitionClass(CustomActivity.class);
        fragment.setImageLoader((String uri, ImageView view) -> {
            Log.d(TAG, "Loading images");
            switch(uri) {
                case "0": view.setImageResource(R.drawable.test_image); break;
                case "1": view.setImageResource(R.drawable.test_image1); break;
                case "2": view.setImageResource(R.drawable.test_image2); break;
                case "3": view.setImageResource(R.drawable.test_image3); break;
                case "4": view.setImageResource(R.drawable.test_image4); break;
            }
        });
    }
}
