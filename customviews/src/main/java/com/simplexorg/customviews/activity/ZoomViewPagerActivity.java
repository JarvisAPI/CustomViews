package com.simplexorg.customviews.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.simplexorg.customviews.R;
import com.simplexorg.customviews.adapter.TransitionImagePagerAdapter;
import com.simplexorg.customviews.fragment.ZoomViewPagerFragment.ImageLoader;
import com.simplexorg.customviews.model.ImageDataHolder;
import com.simplexorg.customviews.util.VUtil;

import java.util.List;

/**
 * Created by william on 2018-12-25.
 * Shows images in a view pager and allows for zooming. If you intent to use this
 * Activity then you must subclass it and implement all of the required abstract methods.
 */

public abstract class ZoomViewPagerActivity extends AppCompatActivity implements ImageLoader {
    private static final String TAG = ZoomViewPagerActivity.class.getSimpleName();
    private TransitionImagePagerAdapter mImageAdapter;
    private ViewPager mViewPager;
    private String mTransitionName;
    private boolean mIsEnding = false;

    public static final int ANIMATION_NONE = 100;
    public static final int ANIMATION_SCROLL = 200;
    protected int mEndAnimation = ANIMATION_NONE;

    private boolean mIsImageLoaded = false;
    private boolean mIsViewPagerSetup = false;

    abstract protected void loadImageIntoView(String uri, ImageView view);

    @Override
    public void loadImage(String uri, ImageView view) {
        loadImageIntoView(uri, view);

        if (mTransitionName != null && mTransitionName.equals(view.getTransitionName())) {
            mIsImageLoaded = true;
            if (mIsViewPagerSetup) {
                startPostponedEnterTransition();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "starting");
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        postponeEnterTransition();
        setContentView(R.layout.customviews_activity_zoom_view_pager);

        mTransitionName = getIntent().getStringExtra(VUtil.EXTRA_TRANSITION_NAME);
        List<ImageDataHolder> imageDataList = getIntent().getParcelableArrayListExtra(VUtil.EXTRA_IMAGE_DATA);

        TransitionImagePagerAdapter adapter = new TransitionImagePagerAdapter(R.layout.customviews_zoom_in_image_item);
        adapter.setImageLoader(this);

        if (imageDataList != null) {
            adapter.setImageDataList(imageDataList);
        }

        mImageAdapter = adapter;

        mViewPager = findViewById(R.id.customviews_view_pager);
        mViewPager.setAdapter(mImageAdapter);

        OnPageChangeListener listener = new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "page scrolled!");
                mIsViewPagerSetup = true;
                if (mIsImageLoaded) {
                    startPostponedEnterTransition();
                    mViewPager.removeOnPageChangeListener(this);
                }
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        };
        mViewPager.addOnPageChangeListener(listener);

        mViewPager.setCurrentItem(adapter.getListPosition(mTransitionName));

        Fade fade = new Fade();
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
    }

    @Override
    public void onBackPressed() {
        if (mIsEnding) {
            return;
        }
        mIsEnding = true;
        int curItem = mImageAdapter.getListPosition(mTransitionName);

        if (mViewPager.getCurrentItem() != curItem) {
            if (mEndAnimation == ANIMATION_SCROLL) {
                mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                    @Override
                    public void onPageSelected(int position) {}

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        switch (state) {
                            case ViewPager.SCROLL_STATE_IDLE:
                                if (mIsEnding) {
                                    ZoomViewPagerActivity.super.onBackPressed();
                                }
                                break;
                        }
                    }
                });

                mViewPager.setCurrentItem(curItem, true);
            } else {
                finish();
            }
        } else {
            super.onBackPressed();
        }
    }
}
