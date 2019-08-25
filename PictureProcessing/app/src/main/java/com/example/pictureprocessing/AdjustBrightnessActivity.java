package com.example.pictureprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class AdjustBrightnessActivity extends AppCompatActivity {

    private Button photoSrcBtn;
    private FloatingActionButton savePhotoActionBtn, goBackBtn;
    private ImageView imageView;
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_brightness);

        DefaultCommands.requestMultiplePermissions(this);

        //Assign the buttons from the design to the instances we created
        photoSrcBtn = (Button) findViewById(R.id.photoSrcBtn);
        savePhotoActionBtn = (FloatingActionButton) findViewById(R.id.savePhotoActionBtn);
        goBackBtn = (FloatingActionButton) findViewById(R.id.goBackBtn);
        imageView = (ImageView) findViewById(R.id.imageView);

        photoSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultCommands.showPictureDialog(activity, activity);
            }
        });

        savePhotoActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultCommands.saveImage(((BitmapDrawable) imageView.getDrawable()).getBitmap(), activity);
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == this.RESULT_CANCELED) {
            Toast.makeText(activity, "Cancelled!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode == GALLERY) {
            if (intent != null) {
                //Gets the filepath of an image from gallery
                Uri contentURI = intent.getData();
                try {
                    //Gets the bitmap of a photo from gallery using the filepath
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            //Gets the bitmap from the taken photo and sets the imageView to it
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}
