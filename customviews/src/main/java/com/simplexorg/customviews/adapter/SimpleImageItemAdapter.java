package com.simplexorg.customviews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simplexorg.customviews.R;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ImageItemViewHolder;
import com.simplexorg.customviews.adapter.StatusMediator.Observer;

import java.util.List;

public class SimpleImageItemAdapter extends RecyclerView.Adapter<ImageItemViewHolder> {
    private static final String TAG = SimpleImageItemAdapter.class.getSimpleName();

    public interface ImageItemsListener {
        void onImageCountChange(int itemCount);

        void onImageClick(String imagePath);
    }

    private Context mContext;
    private SimpleImageItemView mView;
    private SimpleImageItemPresenter mPresenter;
    private SimpleImageItemModel mModel;

    private SimpleImageItemAdapter() {}

    public static SimpleImageItemAdapter newInstance(Context context) {
        SimpleImageItemAdapter adapter = new SimpleImageItemAdapter();
        adapter.mContext = context;
        adapter.mPresenter = new SimpleImageItemPresenter();
        adapter.mView = new SimpleImageItemView();
        adapter.mView.mAdapter = adapter;
        adapter.mModel = new SimpleImageItemModel();
        adapter.mPresenter.attach(adapter.mView, adapter.mModel);
        return adapter;
    }

    /**
     *
     * @param statusTracker object that determines any any moment, whether or
     *                      not adapter should keep track of the main image that
     *                      user has selected.
     */
    public static SimpleImageItemAdapter newTrackerInstance(Context context, StatusMediator statusTracker) {
        SimpleImageItemAdapter adapter = new SimpleImageItemAdapter();
        adapter.mContext = context;
        TrackerImageItemPresenter presenter = new TrackerImageItemPresenter();
        presenter.setStatusTracker(statusTracker);
        adapter.mPresenter = presenter;
        adapter.mView = new SimpleImageItemView();
        adapter.mView.mAdapter = adapter;
        adapter.mModel = new SimpleImageItemModel();
        adapter.mPresenter.attach(adapter.mView, adapter.mModel);
        return adapter;
    }

    Context getContext() {
        return mContext;
    }

    @NonNull
    @Override
    public ImageItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.customviews_list_image_item_simple, parent, false);
        return new ImageItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageItemViewHolder holder, int position) {
        mView.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return mModel.size();
    }

    public List<String> getImagePaths() {
        return mModel.getImagePaths();
    }

    public void setImageItemsListener(ImageItemsListener imageItemsListener) {
        mPresenter.setImageItemsListener(imageItemsListener);
    }

    /**
     *
     * @return all image paths except the main image path.
     */
    public List<String> getSubImagePaths() {
        return mModel.getSubImagePaths();
    }

    public String getMainImagePath() {
        return mModel.getMainImagePath();
    }

    public void addImagePath(String imagePath) {
        mPresenter.onAddImage(imagePath);
    }

    public void removeImagePath(String imagePath) {
        mPresenter.onRemoveImage(imagePath);
    }

    public Observer getStatusObserver() {
        if (mPresenter instanceof Observer) {
            return (Observer) mPresenter;
        }
        return null;
    }

    interface ViewHolderToken {}

    static abstract class ViewToken {
        String text;
        int textColor;
        int gravity;
        boolean animated;
        boolean transparentOverlay;
    }

    static class ImageItemViewHolder extends RecyclerView.ViewHolder implements ViewHolderToken {
        CardView mImageCardView;
        ImageView mImageItemIndicator;
        ImageView mProductImage;

        private ImageItemViewHolder(View v) {
            super(v);
            mImageCardView = v.findViewById(R.id.image_item_card_view);
            mImageItemIndicator = v.findViewById(R.id.image_item_fav);
            mProductImage = v.findViewById(R.id.image_item_small);
        }
    }
}
