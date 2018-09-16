package com.simplexorg.customviews.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class BitmapDecodeTask extends AsyncTask<String, Void, Bitmap> {
    private WeakReference<ImageView> mImageViewReference;
    private int mThumbnailSize;
    private boolean mDecodeIntoThumbnail;
    private String mImagePath;

    public BitmapDecodeTask() {

    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public void setReceiver(ImageView imageView) {
        mImageViewReference = new WeakReference<>(imageView);
    }

    public void setThumbnailSize(int thumbnailSize) {
        mDecodeIntoThumbnail = true;
        mThumbnailSize = thumbnailSize;
    }

    @Nullable
    private Bitmap getThumbnailBitmap(String path) {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1)) {
            return null;
        }
        int originalSize = (bounds.outHeight > bounds.outWidth) ? bounds.outHeight : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / mThumbnailSize;
        return BitmapFactory.decodeFile(path, opts);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if (mDecodeIntoThumbnail) {
            return getThumbnailBitmap(mImagePath);
        }
        return BitmapFactory.decodeFile(mImagePath);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            final ImageView imageView = mImageViewReference.get();
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
                imageView.invalidate();
            }
        }
    }
}
