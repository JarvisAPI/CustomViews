package com.simplexorg.customviews.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.simplexorg.customviews.activity.LocalZoomInImageActivity;
import com.simplexorg.customviews.activity.ZoomInImageActivity;
import com.simplexorg.customviews.util.VUtil;

/**
 * Created by william on 2018-12-24.
 * Click to go to zoom in activity image view.
 */

public class ZoomInImageView extends AppCompatImageView {
    private int mResource = 0;
    private Activity mActivity;

    public ZoomInImageView(Context context) {
        super(context);
        setInternalClickListener();
    }

    public ZoomInImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResource = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        setInternalClickListener();
    }

    public ZoomInImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResource = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        setInternalClickListener();
    }

    @Override
    public void setImageResource(@DrawableRes int res) {
        mResource = res;
    }

    public void attachTransitionActivity(Activity activity) {
        mActivity = activity;
    }

    private void setInternalClickListener() {
        setOnClickListener((View view) -> {
            if (mResource != 0) {
                Intent intent = new Intent(getContext(), LocalZoomInImageActivity.class);
                intent.putExtra(ZoomInImageActivity.IMAGE_RES, mResource);
                if (mActivity != null) {
                    intent.putExtra(VUtil.EXTRA_TRANSITION_NAME, getTransitionName());
                    ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                            ZoomInImageView.this, ViewCompat.getTransitionName(ZoomInImageView.this));
                    ActivityCompat.startActivity(mActivity, intent, optionsCompat.toBundle());
                } else {
                    getContext().startActivity(intent);
                }
            }
        });
    }
}
