package com.simplexorg.customviews.adapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleImageItemModel implements SimpleImageItemAdapterContract.Model {
    private List<String> mImagePaths;
    private String mMainImagePath;

    SimpleImageItemModel() {
        mImagePaths = new ArrayList<>();
    }

    @Override
    public String getMainImagePath() {
        return mMainImagePath;
    }

    @Override
    public void setMainImagePath(String imagePath) {
        mMainImagePath = imagePath;
    }

    @Override
    public List<String> getSubImagePaths() {
        List<String> subImagePaths = new ArrayList<>();
        subImagePaths.addAll(mImagePaths);
        subImagePaths.remove(mMainImagePath);
        return subImagePaths;
    }

    @Override
    public List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();
        imagePaths.addAll(mImagePaths);
        return imagePaths;
    }

    @Override
    public String getImagePath(int position) {
        return mImagePaths.get(position);
    }

    @Override
    public void removeImagePath(int position) {
        mImagePaths.remove(position);
    }

    @Override
    public boolean removeImagePath(String imagePath) {
        return mImagePaths.remove(imagePath);
    }

    @Override
    public void addImagePath(String imagePath) {
        mImagePaths.add(imagePath);
    }

    @Override
    public int size() {
        return mImagePaths.size();
    }

    @Override
    public boolean containsImagePath(String imagePath) {
        return mImagePaths.contains(imagePath);
    }
}
