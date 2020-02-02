package com.kong.cameratest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;

public class CallSystemCameraActivity extends AppCompatActivity {
    private Button m_ButtonPhotograph;
    private ImageView m_ImagePhotoStorage;
    private File photoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_system_camera);
        m_ButtonPhotograph=findViewById(R.id.buttonPhotograph);
        m_ButtonPhotograph.setOnClickListener(new CallSystemCameraActivity.MyOnClickListener());
        m_ImagePhotoStorage=findViewById(R.id.imagePhotoStorage);

        photoFile=new File(CallSystemCameraActivity.this.getExternalCacheDir().getPath(),System.currentTimeMillis()+".jpg");

    }

    private class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(intent,100);
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if(resultCode==100)
        {
            m_ImagePhotoStorage.setImageURI(Uri.fromFile(photoFile));
        }
    }
}
