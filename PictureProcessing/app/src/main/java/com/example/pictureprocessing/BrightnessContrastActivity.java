package com.example.pictureprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.security.spec.ECField;

public class BrightnessContrastActivity extends AppCompatActivity {
    //Creates instances of all the buttons, image view and the spinner in the design
    private FloatingActionButton savePhotoActionBtn, goBackBtn;
    private ImageView imageView;
    private SeekBar brightScale, contrastScale;
    private Activity activity = this;
    private Bitmap image;
    //Constants for dealing with magic numbers
    private int PROGRESS_DIVIDE_CONST = 50;
    private int PROGRESS_SUBTRACT_CONST = 255;

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
                tempImage = com.example.pictureprocessing.Filters.AdjustBrightnessFilter.BrightnessFilter(tempImage, progress / PROGRESS_DIVIDE_CONST);
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
                int progress = seekBar.getProgress() - PROGRESS_SUBTRACT_CONST;
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
                String filePath = DefaultCommands.saveImage(bitmap, activity);
                intent.putExtra("filePath", filePath);
                startActivity(intent);
            }
        });

        //Set the imageView of the current activity to the image from the main activity
        try {
            Bitmap bitmap = null;
            File imgFile = new File(this.getIntent().getStringExtra("filePath"));
            if(imgFile.exists()){
                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
            imgFile.delete();
            image = bitmap;
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(this, "Error transferring photo between activities", Toast.LENGTH_SHORT).show();
        }

    }

}
