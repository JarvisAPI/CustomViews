package com.simplexorg.customviews.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simplexorg.customviews.R;
import com.simplexorg.customviews.fragment.ZoomViewPagerFragment.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 2018-12-25.
 * Shows images.
 */

public class ImagePagerAdapter extends PagerAdapter {
    private static final String TAG = ImagePagerAdapter.class.getSimpleName();

    public interface OnImageClickListener {
        void onImageClick(View view);
    }

    protected List<String> mImageUris;
    private ImageLoader mImageLoader;
    private int mResourceId;
    private OnImageClickListener mOnImageClickListener;

    ImagePagerAdapter(@LayoutRes int resourceId) {
        mResourceId = resourceId;
        mImageUris = new ArrayList<>();
    }

    public void setImageUris(List<String> imageUris) {
        mImageUris.clear();
        mImageUris.addAll(imageUris);
        notifyDataSetChanged();
    }


    public void setOnImageClickListener(OnImageClickListener listener) {
        mOnImageClickListener = listener;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mImageUris.size();
    }

    protected void afterInstantiateView(ImageView imageView, int position) {
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem");
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(mResourceId, container, false);
        ImageView imageView = itemView.findViewById(R.id.customviews_image);
        afterInstantiateView(imageView, position);

        imageView.setOnClickListener(this::onImageViewClick);

        if (mImageLoader != null) {
            Log.d(TAG, "Loading image");
            mImageLoader.loadImage(mImageUris.get(position), imageView);
        } else {
            Log.e(TAG, "ImageLoader is null, cannot load image");
        }
        container.addView(itemView);
        return itemView;
    }

    private void onImageViewClick(View view) {
        if (mOnImageClickListener != null) {
            Log.d(TAG, "Image clicked!");
            mOnImageClickListener.onImageClick(view);
        }
    }

    public List<String> getImageUris() {
        return mImageUris;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
