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
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class AdjustBrightnessActivity extends AppCompatActivity {

    private FloatingActionButton savePhotoActionBtn, goBackBtn;
    private ImageView imageView;
    private SeekBar brightScale;
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;
    private Activity activity = this;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_brightness);

        DefaultCommands.requestMultiplePermissions(this);

        //Assign the buttons from the design to the instances we created
        savePhotoActionBtn = (FloatingActionButton) findViewById(R.id.savePhotoActionBtn);
        goBackBtn = (FloatingActionButton) findViewById(R.id.goBackBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        brightScale = (SeekBar) findViewById(R.id.brightScale);
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
                try{
                    tempImage = image.copy(Bitmap.Config.RGB_565, true);
                } catch (Exception e){
                    System.out.println("No photo selected");
                    return;
                }

                tempImage = com.example.pictureprocessing.Filters.AdjustBrightnessFilter.BrightnessFilter(tempImage, progress/50);
                imageView.setImageBitmap(tempImage);
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
                Bitmap bitmap = null;
                try{
                    bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                } catch (Exception e){
                    System.out.println("No photo selected");
                }
                DefaultCommands.addBitmapToIntent(intent, bitmap);
                startActivity(intent);
            }
        });

        //Set the imageView of the current activity to the image from the main activity
        Bitmap bitmap = null;
        try{
            bitmap = DefaultCommands.getImageFromIntent(this.getIntent());
            image = bitmap;
            imageView.setImageBitmap(bitmap);
        } catch (Exception e){
            System.out.println("No photo found!");
        }

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
