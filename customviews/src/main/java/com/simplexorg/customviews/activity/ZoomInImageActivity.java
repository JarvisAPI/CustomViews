package com.simplexorg.customviews.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.Window;

import com.github.chrisbanes.photoview.PhotoView;
import com.simplexorg.customviews.R;
import com.simplexorg.customviews.util.VUtil;

/**
 * Created by william on 2018-12-24.
 * Activity to zoom into image.
 */

public class ZoomInImageActivity extends AppCompatActivity {
    public static final String IMAGE_RES = "imageRes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.customviews_activity_zoom_in_image);

        PhotoView mPhotoView = findViewById(R.id.customviews_photo_view);

        int imageRes = getIntent().getIntExtra(IMAGE_RES, 0);
        mPhotoView.setImageResource(imageRes);

        String transitionName = getIntent().getStringExtra(VUtil.EXTRA_TRANSITION_NAME);

        if (transitionName != null) {
            mPhotoView.setTransitionName(transitionName);

            Fade fade = new Fade();
            fade.excludeTarget(android.R.id.statusBarBackground, true);
            fade.excludeTarget(android.R.id.navigationBarBackground, true);

            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }
}
