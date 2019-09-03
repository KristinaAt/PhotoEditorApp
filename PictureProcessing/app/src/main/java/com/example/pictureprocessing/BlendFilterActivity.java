package com.example.pictureprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;

public class BlendFilterActivity extends AppCompatActivity {
    //Creates instances of all the buttons and imageView in the design
    private FloatingActionButton savePhotoActionBtn, goBackBtn;
    private ImageView imageView;
    private Button blendWithBtn, undoBtn;
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;
    private Activity activity = this;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blend_filter);

        DefaultCommands.requestMultiplePermissions(this);

        //Assign the buttons from the design to the instances we created
        imageView = (ImageView) findViewById(R.id.imageView);
        savePhotoActionBtn = (FloatingActionButton) findViewById(R.id.savePhotoActionBtn);
        goBackBtn = (FloatingActionButton) findViewById(R.id.goBackBtn);
        blendWithBtn = (Button) findViewById(R.id.blendWithBtn);
        undoBtn = (Button) findViewById(R.id.undoBtn);

        /*Calls a helper function that saves a photo in a directory
        of the phone using a given bitmap
         */
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
                String filePath = DefaultCommands.saveImage(bitmap, activity);
                intent.putExtra("filePath", filePath);
                startActivity(intent);
            }
        });

        blendWithBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultCommands.showPictureDialog(activity, activity);
            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(image);
            }
        });

        //Set the imageView of the current activity to the image from the main activity
        Bitmap bitmap = null;
        try{
            File file = new File(activity.getIntent().getStringExtra("filePath"));
            if(file.exists()){
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
            image = bitmap;
            imageView.setImageBitmap(bitmap);
            file.delete();
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
                    bitmap = com.example.pictureprocessing.Filters.BlendFilter.BlendFilter(image, bitmap);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            //Gets the bitmap from the taken photo and sets the imageView to it
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
            bitmap = com.example.pictureprocessing.Filters.BlendFilter.BlendFilter(image, bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}