package com.simplexorg.testviews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PhotoHandler {
    private static final String TAG = PhotoHandler.class.getSimpleName();
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;

    /**
     * Dispatch an intent to take a photo. When intent finishes, a callback will be
     * made to the calling activity with the REQUEST_IMAGE_CAPTURE request code.
     * @param activity the activity to invoke intent from.
     */
    public void dispatchTakePhotoIntent(Activity activity) {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePhotoIntent.resolveActivity(activity.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(activity);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Unable to take photo", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(activity, "com.simplexorg.testviews.fileprovider", photoFile);
                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile(Activity activity) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    /**
     * Clears the Pictures that are stored on the app.
     */
    public static void clearImagePaths(Activity activity) {
        File picturesDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (picturesDir != null && picturesDir.isDirectory()) {
            for (String pathName : picturesDir.list()) {
                Log.d(TAG, "pathName: " + pathName);
                if (pathName.startsWith("JPEG_")) {
                    File photo = new File(picturesDir, pathName);
                    if (!photo.delete()) {
                        Log.i(TAG, "Unable to delete photo");
                    }
                }
            }
        }
    }
}
