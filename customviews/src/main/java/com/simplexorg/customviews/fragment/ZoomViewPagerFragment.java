package com.simplexorg.customviews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simplexorg.customviews.R;
import com.simplexorg.customviews.activity.ZoomViewPagerActivity;
import com.simplexorg.customviews.adapter.TransitionImagePagerAdapter;
import com.simplexorg.customviews.model.ImageDataHolder;
import com.simplexorg.customviews.util.VUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 2018-12-25.
 * View pager with list of images that can be zoomed in via starting the zoom in activity.
 * In order to use this fragment properly you must extend the ZoomViewPagerActivity abstract class
 * and implement all the required abstract methods then pass in the custom class you created via
 * the setTransitionClass method.
 */

public class ZoomViewPagerFragment extends Fragment {
    private static final String TAG = ZoomViewPagerFragment.class.getSimpleName();

    public interface ImageLoader {
        void loadImage(String uri, ImageView view);
    }

    private boolean mIsTransitioning;
    private ViewPager mViewPager;
    private TabLayout mTabDots;
    private TransitionImagePagerAdapter mImageAdapter;
    private Class<? extends ZoomViewPagerActivity> mTransitionClass;

    public ZoomViewPagerFragment() {
        mImageAdapter = new TransitionImagePagerAdapter(R.layout.customviews_plain_image_item);
    }

    public void setImagesUris(List<String> uris) {
        ArrayList<ImageDataHolder> imageDataHolderList = VUtil.getInstance().getImageDataHolderList(uris);
        mImageAdapter.setImageDataList(imageDataHolderList);
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageAdapter.setImageLoader(imageLoader);
    }

    public void setTransitionClass(Class<? extends ZoomViewPagerActivity> transitionClass) {
        mTransitionClass = transitionClass;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customviews_fragment_zoom_view_pager, container, false);
        mViewPager = v.findViewById(R.id.customviews_view_pager);
        mTabDots = v.findViewById(R.id.customviews_tab_dots);
        mViewPager.setAdapter(mImageAdapter);
        Log.d(TAG, "setupWithViewPager: ");
        mTabDots.setupWithViewPager(mViewPager);
        mImageAdapter.setOnImageClickListener(this::onImageClick);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mIsTransitioning = false;
    }

    private void onImageClick(View view) {
        if (getActivity() != null && !mIsTransitioning && mTransitionClass != null) {
            Log.d(TAG, "Fragment onImageClick!!");
            mIsTransitioning = true;

            Intent intent = new Intent(getActivity(), mTransitionClass);

            intent.putParcelableArrayListExtra(VUtil.EXTRA_IMAGE_DATA, mImageAdapter.getImageDataList());
            intent.putExtra(VUtil.EXTRA_TRANSITION_NAME, view.getTransitionName());

            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    view, ViewCompat.getTransitionName(view));
            startActivity(intent, optionsCompat.toBundle());
        }
    }
}
