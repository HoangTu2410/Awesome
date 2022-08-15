package com.rikkei.awesome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.rikkei.awesome.ui.home.profile.ImageResultListener;
import com.rikkei.awesome.ui.splash.SplashFragment;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layoutActivity;
    private ImageResultListener imageResultListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutActivity = findViewById(R.id.layout_activity);
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.home_container, new SplashFragment(), "fragment_splash");
            transaction.commit();
        }
    }

    public ConstraintLayout getLayoutActivity() {
        return layoutActivity;
    }

    public void setImageResultListener(ImageResultListener imageResultListener) {
        this.imageResultListener = imageResultListener;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            imageResultListener.onResultImage(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}