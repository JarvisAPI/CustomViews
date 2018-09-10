package com.simplexorg.testviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.simplexorg.customviews.dialog.CriticalConfirmDialog;
import com.simplexorg.customviews.views.SpinnerButton;

public class MainActivity extends AppCompatActivity {

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
    }
}
