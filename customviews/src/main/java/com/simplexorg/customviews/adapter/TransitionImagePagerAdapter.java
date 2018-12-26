package com.simplexorg.customviews.adapter;

import android.support.v4.view.ViewCompat;
import android.widget.ImageView;

import com.simplexorg.customviews.model.ImageDataHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by william on 2018-12-26.
 * For transitioning shared elements.
 */

public class TransitionImagePagerAdapter extends ImagePagerAdapter {
    private List<String> mTransitionNames;

    public TransitionImagePagerAdapter(int resourceId) {
        super(resourceId);
        mTransitionNames = new ArrayList<>();
    }

    public void setImageDataList(List<ImageDataHolder> imageDataList) {
        mImageUris.clear();
        for (ImageDataHolder dataHolder : imageDataList) {
            mImageUris.add(dataHolder.imageUri);
            mTransitionNames.add(dataHolder.transitionName);
        }
        notifyDataSetChanged();
    }

    @Override
    protected void afterInstantiateView(ImageView imageView, int position) {
        ViewCompat.setTransitionName(imageView, mTransitionNames.get(position));
    }

    public ArrayList<ImageDataHolder> getImageDataList() {
        ArrayList<ImageDataHolder> imageDataHolders = new ArrayList<>();
        for (int i = 0; i < mImageUris.size(); i++) {
            imageDataHolders.add(new ImageDataHolder(mImageUris.get(i), mTransitionNames.get(i)));
        }
        return imageDataHolders;
    }

    public int getListPosition(String transitionName) {
        return mTransitionNames.indexOf(transitionName);
    }

    public String getTransitionName(int position) {
        return mTransitionNames.get(position);
    }
}
