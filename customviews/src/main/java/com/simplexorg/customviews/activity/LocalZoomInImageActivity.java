package com.simplexorg.customviews.activity;

import android.widget.ImageView;

/**
 * Created by william on 2018-12-26.
 * For browsing local images.
 */

public class LocalZoomInImageActivity extends ZoomInImageActivity {

    @Override
    protected void setupImageView(ImageView imageView) {
        int imageRes = getIntent().getIntExtra(IMAGE_RES, 0);
        imageView.setImageResource(imageRes);
        startPostponedEnterTransition();
    }
}
