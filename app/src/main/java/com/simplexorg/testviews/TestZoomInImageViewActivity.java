package com.simplexorg.testviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.simplexorg.customviews.views.ZoomInImageView;

public class TestZoomInImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_in_image);

        ZoomInImageView imageView = findViewById(R.id.image_view);
        imageView.attachTransitionActivity(this);
        imageView.setTransitionName("transition");
    }
}
