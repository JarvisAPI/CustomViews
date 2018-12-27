package com.simplexorg.customviews.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by william on 2018-12-25.
 * Holds the data required to do transitions properly.
 */

public class ImageDataHolder implements Parcelable {
    public final String imageUri;
    public final String transitionName;

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<ImageDataHolder>() {
        @Override
        public ImageDataHolder createFromParcel(Parcel parcel) {
            return new ImageDataHolder(parcel);
        }

        @Override
        public ImageDataHolder[] newArray(int size) {
            return new ImageDataHolder[size];
        }
    };

    private ImageDataHolder(Parcel parcel) {
        imageUri = parcel.readString();
        transitionName = parcel.readString();
    }

    public ImageDataHolder(String imageUri, String transitionName) {
        this.imageUri = imageUri;
        this.transitionName = transitionName;
    }

    @Override
    public String toString() {
        return String.format(Locale.CANADA, "{imageUri: %s, transitionName: %s}", imageUri, transitionName);
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageUri);
        parcel.writeString(transitionName);
    }
}
