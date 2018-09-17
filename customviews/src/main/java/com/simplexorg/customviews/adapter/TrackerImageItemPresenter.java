package com.simplexorg.customviews.adapter;

import android.graphics.Color;
import android.view.Gravity;

import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewHolderToken;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewToken;
import com.simplexorg.customviews.adapter.StatusMediator.Observer;
import com.simplexorg.customviews.util.BitmapDecodeTask;
import com.simplexorg.customviews.util.VFactory;

public class TrackerImageItemPresenter extends SimpleImageItemPresenter implements Observer {
    private StatusMediator mMainImageStatusTracker;

    void setStatusTracker(StatusMediator statusTracker) {
        mMainImageStatusTracker = statusTracker;
    }

    @Override
    public void onImageIndicatorClick(ViewToken token) {
        token.text = "Main image indicator, to set main image long press image!";
        token.textColor = Color.WHITE;
        token.gravity = Gravity.TOP;
        token.animated = true;
        token.transparentOverlay = false;
        mView.showToolTip(token);
    }

    @Override
    public void onRemoveImage(String imagePath) {
        if (mMainImageStatusTracker.shouldTrackStatus(this)) {
            super.onRemoveImage(imagePath);
        } else {
            for (int i = 0; i < mModel.size(); i++) {
                String path = mModel.getImagePath(i);
                if (path.equals(imagePath)) {
                    mModel.removeImagePath(i);
                    mView.notifyItemRemoved(i);
                    return;
                }
            }
        }
    }

    @Override
    public BitmapDecodeTask onImageViewReady(ViewHolderToken token, int position) {
        if (mMainImageStatusTracker.shouldTrackStatus(this)) {
            return super.onImageViewReady(token, position);
        }
        BitmapDecodeTask task = VFactory.getInstance().create(BitmapDecodeTask.class);
        task.setThumbnailSize(mDefaultImgSize);
        task.setImagePath(mModel.getImagePath(position));
        return task;
    }

    @Override
    public boolean onImageItemLongClick(ViewHolderToken token, int position) {
        if (mMainImageStatusTracker.shouldTrackStatus(this)) {
            return super.onImageItemLongClick(token, position);
        }
        mModel.setMainImagePath(mModel.getImagePath(mView.getImagePosition(token)));
        mView.setMainImageIndicator(token);
        mMainImageStatusTracker.requestToTrackStatus(this);
        return true;
    }

    @Override
    public void update(int event) {
        if (event == Observer.CLEAR_STATUS) {
            mView.clearMainImageIndicator();
            mModel.setMainImagePath(null);
        }
    }
}
