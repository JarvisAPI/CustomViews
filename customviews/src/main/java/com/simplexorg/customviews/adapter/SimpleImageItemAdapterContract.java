package com.simplexorg.customviews.adapter;


import android.support.annotation.NonNull;

import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewHolderToken;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ViewToken;
import com.simplexorg.customviews.util.BitmapDecodeTask;

import java.util.List;

public interface SimpleImageItemAdapterContract {
    interface Model {
        String getMainImagePath();

        void setMainImagePath(String imagePath);

        List<String> getSubImagePaths();

        List<String> getImagePaths();

        String getImagePath(int position);

        void removeImagePath(int position);

        boolean removeImagePath(String imagePath);

        void addImagePath(String imagePath);

        int size();

        boolean containsImagePath(String imagePath);
    }

    interface View {
        void setPresenter(Presenter presenter);

        void notifyItemRemoved(int position);

        void notifyItemChanged(int position);

        void notifyItemInserted(int position);

        void displayToast(String msg);

        int getImagePosition(ViewHolderToken token);

        void setMainImageIndicator(ViewHolderToken token);

        void clearMainImageIndicator();

        void showToolTip(ViewToken token);
    }

    interface Presenter {
        void attach(@NonNull SimpleImageItemAdapterContract.View view,
                    @NonNull SimpleImageItemAdapterContract.Model model);

        void onImageItemClick(int position);

        void onRemoveImage(String imagePath);

        void onAddImage(String imagePath);

        void onImageIndicatorClick(ViewToken token);

        boolean onImageItemLongClick(ViewHolderToken token, int position);

        /**
         *
         * @param position the position of the image view.
         * @return configured decoding task.
         */
        BitmapDecodeTask onImageViewReady(ViewHolderToken token, int position);
    }
}
