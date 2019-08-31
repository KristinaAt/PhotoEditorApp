package com.example.pictureprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.spec.ECField;

public class BrightnessContrastActivity extends AppCompatActivity {
    //Creates instances of all the buttons, image view and the spinner in the design
    private FloatingActionButton savePhotoActionBtn, goBackBtn;
    private ImageView imageView;
    private SeekBar brightScale, contrastScale;
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;
    private Activity activity = this;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness_contrast);

        DefaultCommands.requestMultiplePermissions(this);

        //Assign the buttons from the design to the instances we created
        savePhotoActionBtn = (FloatingActionButton) findViewById(R.id.savePhotoActionBtn);
        goBackBtn = (FloatingActionButton) findViewById(R.id.goBackBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        brightScale = (SeekBar) findViewById(R.id.brightScale);
        contrastScale = (SeekBar) findViewById(R.id.contrastScale);
        brightScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bitmap tempImage = null;
                double progress = seekBar.getProgress();
                //Try catch block for copying the bitmap from the imageView
                try {
                    tempImage = image.copy(Bitmap.Config.RGB_565, true);
                } catch (Exception e) {
                    System.out.println("No photo selected");
                    return;
                }
                tempImage = com.example.pictureprocessing.Filters.AdjustBrightnessFilter.BrightnessFilter(tempImage, progress / 50);
                imageView.setImageBitmap(tempImage);
            }
        });

        contrastScale.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bitmap tempImage = null;
                //Converts the progress into a number in the range [-255, 255]
                int progress = seekBar.getProgress() - 255;
                //Try catch block for copying the bitmap from the imageView
                try {
                    tempImage = image.copy(Bitmap.Config.RGB_565, true);
                } catch (Exception e) {
                    System.out.println("No photo selected");
                    return;
                }
                tempImage = com.example.pictureprocessing.Filters.ContrastFilter.ContrastFilter(tempImage, progress);
                imageView.setImageBitmap(tempImage);
            }
        });

        /*Calls a helper function that saves a photo in a directory
        of the phone using a given bitmap
        */
        savePhotoActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultCommands.saveImage(((BitmapDrawable) imageView.getDrawable()).getBitmap(), activity);
            }
        });

        //When the button is clicked the program goes to the main activity
        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MainActivity.class);
                Bitmap bitmap = null;
                //Try catch block for getting the bitmap from the imageView
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e) {
                    System.out.println("No photo selected");
                }
                DefaultCommands.addBitmapToIntent(intent, bitmap);
                startActivity(intent);
            }
        });

        //Set the imageView of the current activity to the image from the main activity
        Bitmap bitmap = null;
        try {
            bitmap = DefaultCommands.getImageFromIntent(this.getIntent());
            image = bitmap;
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            System.out.println("No photo found!");
        }

    }

}
