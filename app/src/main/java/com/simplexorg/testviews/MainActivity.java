package com.simplexorg.testviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.simplexorg.customviews.adapter.SimpleImageItemAdapter;
import com.simplexorg.customviews.adapter.SimpleImageItemAdapter.ImageItemsListener;
import com.simplexorg.customviews.dialog.CriticalConfirmDialog;
import com.simplexorg.customviews.views.SpinnerButton;

public class MainActivity extends AppCompatActivity implements ImageItemsListener {

    private static final String TAG = "MainActivity";
    private PhotoHandler mPhotoHandler = new PhotoHandler();
    private SimpleImageItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpinnerButton button = findViewById(R.id.btn);
        CriticalConfirmDialog confirmDialog = new CriticalConfirmDialog();
        confirmDialog.setOnConfirmListener(() -> {
            button.spin(true);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(() -> button.spin(false));
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        });
        button.setOnClickListener((View view) -> confirmDialog.show(getSupportFragmentManager(), "CriticalConfirm"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = SimpleImageItemAdapter.newInstance(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(mAdapter);
        Button testBtn = findViewById(R.id.test_btn);

        testBtn.setOnClickListener((View view) -> mPhotoHandler.dispatchTakePhotoIntent(this));
        mAdapter.setImageItemsListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PhotoHandler.REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String currentPhotoPath = mPhotoHandler.getCurrentPhotoPath();
                if (currentPhotoPath != null) {
                    Log.d(TAG, "adding to adapter");
                    mAdapter.addImagePath(currentPhotoPath);
                }
            }
        }
    }

    @Override
    public void onImageCountChange(int itemCount) {
        Log.d("MainActivity", "Image count changed to: " + itemCount);
    }

    @Override
    public void onImageClick(String imagePath) {
        Log.d("MainActivity", "imagePath: " + imagePath);
    }
}
