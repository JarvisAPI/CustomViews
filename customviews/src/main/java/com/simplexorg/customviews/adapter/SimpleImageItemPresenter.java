package com.simplexorg.customviews.adapter;


import android.support.annotation.NonNull;
import android.util.Log;

import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ImageItemsListener;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewHolderToken;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewToken;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapterContract.Model;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapterContract.View;
import com.simplexorg.customviews.util.BitmapDecodeTask;
import com.simplexorg.customviews.util.VFactory;
import com.simplexorg.customviews.util.VUtil;

public class SimpleImageItemPresenter implements SimpleImageItemAdapterContract.Presenter {
    protected View mView;
    protected Model mModel;
    int mDefaultImgSize = 200;
    private ImageItemsListener mImageItemsListener;

    void setImageItemsListener(ImageItemsListener imageItemsListener) {
        mImageItemsListener = imageItemsListener;
    }

    @Override
    public void attach(@NonNull View view, @NonNull Model model) {
        mView = view;
        mModel = model;
        mView.setPresenter(this);
    }

    @Override
    public void onImageItemClick(int position) {
        if (mImageItemsListener != null) {
            mImageItemsListener.onImageClick(mModel.getImagePath(position));
        }
    }

    @Override
    public void onRemoveImage(String imagePath) {
        int removedPosition = 0;
        boolean removedImage = false;
        for (int i = 0; i < mModel.size(); i++) {
            String path = mModel.getImagePath(i);
            if (path.equals(imagePath)) {
                removedPosition = i;
                removedImage = true;
                mModel.removeImagePath(i);
                mView.notifyItemRemoved(i);
                break;
            }
        }
        if (removedImage && imagePath.equals(mModel.getMainImagePath())) {
            // If main image is removed we need to remark any image as the default main image.
            // clear so that it can be overwritten during bind of new main image.
            mModel.setMainImagePath(null);
            mView.clearMainImageIndicator();
            if (mModel.size() != 0) {
                // Notify any item that is not removed as changed so that they can become new main image.
                mView.notifyItemChanged(removedPosition != 0 ? 0 : 1);
            }
        }
        if (mImageItemsListener != null) {
            mImageItemsListener.onImageCountChange(mModel.size());
        }
    }

    @Override
    public void onAddImage(String imagePath) {
        Log.d("AdapterPresenter", "onAddImage: mModel mainImage: " + mModel.getMainImagePath());
        Log.d("AdapterPresenter", "onAddImage: mModel ImagePaths: " + mModel.getImagePaths());
        Log.d("AdapterPresenter", "onAddImage: mModel size" + mModel.size());
        Log.d("AdapterPresenter", "onAddImage: " + imagePath);
        if (VUtil.getInstance().fileIsImage(imagePath)) {
            mModel.addImagePath(imagePath);
            mView.notifyItemInserted(mModel.size() - 1);
            if (mImageItemsListener != null) {
                mImageItemsListener.onImageCountChange(mModel.size());
            }
        } else {
            mView.displayToast("No Valid Image Selected!");
        }
    }

    @Override
    public void onImageIndicatorClick(ViewToken token) {
    }

    @Override
    public boolean onImageItemLongClick(ViewHolderToken token, int position) {
        if (mModel.getMainImagePath() != null) {
            if (!mModel.getMainImagePath().equals(mModel.getImagePath(position))) {
                mView.clearMainImageIndicator();
                mView.setMainImageIndicator(token);
                mModel.setMainImagePath(mModel.getImagePath(position));
            }
        }
        return true;
    }

    @Override
    public BitmapDecodeTask onImageViewReady(ViewHolderToken token, int position) {
        Log.d("Presenter", "Token: " + token);
        BitmapDecodeTask task = VFactory.getInstance().create(BitmapDecodeTask.class);
        task.setThumbnailSize(mDefaultImgSize);
        task.setImagePath(mModel.getImagePath(position));

        if (mModel.getMainImagePath() == null) {
            mModel.setMainImagePath(mModel.getImagePath(position));
            mView.setMainImageIndicator(token);
        }
        return task;
    }

}
