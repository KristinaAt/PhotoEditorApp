package com.example.pictureprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class ColourScaleActivity extends AppCompatActivity implements View.OnClickListener {
    //Creates instances of all the buttons and image view in the design
    private FloatingActionButton savePhotoActionBtn, goBackBtn;
    private ImageView imageView;
    private Button redBtn, orangeBtn, yellowBtn, greenBtn, blueBtn,
                   purpleBtn, pinkBtn, aquaBtn, clearBtn;
    private Bitmap image;
    private Activity activity = this;

    @Override
    public void onClick(View view) {
        Bitmap tempImage = null;
        //Try catch block for copying the bitmap from the imageView
        try {
            tempImage = image.copy(Bitmap.Config.RGB_565, true);
        } catch (Exception e) {
            System.out.println("No photo selected");
            return;
        }
        //Calls the ColourFilter function based on the pressed coloured button
        switch (view.getId()) {
            case R.id.redBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 0);
                break;
            case R.id.orangeBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 1);
                break;
            case R.id.yellowBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 2);
                break;
            case R.id.greenBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 3);
                break;
            case R.id.blueBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 4);
                break;
            case R.id.purpleBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 5);
                break;
            case R.id.pinkBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 6);
                break;
            case R.id.aquaBtn:
                tempImage = com.example.pictureprocessing.Filters.ColourFilter.ScaleFilter(tempImage, 7);
                break;
            case R.id.clearBtn:
                break;
            default:
                return;
        }

        imageView.setImageBitmap(tempImage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_scale);

        DefaultCommands.requestMultiplePermissions(this);

        redBtn = (Button) findViewById(R.id.redBtn);
        redBtn.setOnClickListener(this);
        orangeBtn = (Button) findViewById(R.id.orangeBtn);
        orangeBtn.setOnClickListener(this);
        yellowBtn = (Button) findViewById(R.id.yellowBtn);
        yellowBtn.setOnClickListener(this);
        greenBtn = (Button) findViewById(R.id.greenBtn);
        greenBtn.setOnClickListener(this);
        blueBtn = (Button) findViewById(R.id.blueBtn);
        blueBtn.setOnClickListener(this);
        purpleBtn = (Button) findViewById(R.id.purpleBtn);
        purpleBtn.setOnClickListener(this);
        pinkBtn = (Button) findViewById(R.id.pinkBtn);
        pinkBtn.setOnClickListener(this);
        aquaBtn = (Button) findViewById(R.id.aquaBtn);
        aquaBtn.setOnClickListener(this);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(this);

        savePhotoActionBtn = (FloatingActionButton) findViewById(R.id.savePhotoActionBtn);
        goBackBtn = (FloatingActionButton) findViewById(R.id.goBackBtn);
        imageView = (ImageView) findViewById(R.id.imageView);

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
                String filepath = DefaultCommands.saveImage(bitmap, activity);
                intent.putExtra("filePath", filepath);
                startActivity(intent);
            }
        });

        //Set the imageView of the current activity to the image from the main activity
        Bitmap bitmap = null;
        try {
            File file = new File(activity.getIntent().getStringExtra("filePath"));
            if(file.exists()){
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
            image = bitmap;
            imageView.setImageBitmap(bitmap);
            file.delete();
        } catch (Exception e) {
            System.out.println("No photo found!");
        }
    }
}
