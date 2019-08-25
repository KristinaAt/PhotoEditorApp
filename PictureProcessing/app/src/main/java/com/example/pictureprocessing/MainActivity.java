package com.example.pictureprocessing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pictureprocessing.Filters.BlurFilter;
import com.example.pictureprocessing.Filters.GreyscaleFilter;
import com.example.pictureprocessing.Filters.InvertFilter;
import com.example.pictureprocessing.Filters.PixelArtFilter;
import com.example.pictureprocessing.Filters.RotationFilter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Creates instances of all the buttons in the design
    private Button photoSrcBtn;
    private FloatingActionButton savePhotoActionBtn;
    private ImageView imageView;
    private Spinner spinner;
    private ImageButton turnLeftBtn, turnRightBtn, horizFlipBtn, vertFlipBtn;
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DefaultCommands.requestMultiplePermissions(this);

        //Assign the buttons from the design to the instances we created
        photoSrcBtn = (Button) findViewById(R.id.photoSrcBtn);
        savePhotoActionBtn = (FloatingActionButton) findViewById(R.id.savePhotoActionBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        turnLeftBtn = (ImageButton) findViewById(R.id.turnLeftBtn);
        turnRightBtn = (ImageButton) findViewById(R.id.turnRightBtn);
        horizFlipBtn = (ImageButton) findViewById(R.id.horizFlipBtn);
        vertFlipBtn = (ImageButton) findViewById(R.id.vertFlipBtn);

        //Assigned the spinner instance to the activity_main spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        //Assigns the drop down options by creating an array(Filters) and its items in string.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Filters, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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

        turnRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e){
                    System.out.println("No photo source selected");
                    return;
                }

                bitmap = com.example.pictureprocessing.Filters.RotationFilter.RotateFilter(bitmap, 90);
                imageView.setImageBitmap(bitmap);
            }
        });

        turnLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e){
                    System.out.println("No photo source selected");
                    return;
                }

                bitmap = com.example.pictureprocessing.Filters.RotationFilter.RotateFilter(bitmap, 270);
                imageView.setImageBitmap(bitmap);
            }
        });

        horizFlipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null, result = null;

                try{
                    bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                } catch (Exception e){
                    System.out.println("No photo source selected");
                }

                try{
                    result = bitmap.copy(Bitmap.Config.RGB_565, true);
                }catch (Exception e){
                    System.out.println("No photo source selected");
                }

                result = com.example.pictureprocessing.Filters.FlipFilter.FlipHorizontalFilter(result);
                imageView.setImageBitmap(result);
            }
        });

        vertFlipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null, result = null;

                try{
                    bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                } catch (Exception e){
                    System.out.println("No photo source selected");
                }

                try{
                    result = bitmap.copy(Bitmap.Config.RGB_565, true);
                }catch (Exception e){
                    System.out.println("No photo source selected");
                }

                result = com.example.pictureprocessing.Filters.FlipFilter.FlipVerticalFilter(result);
                imageView.setImageBitmap(result);
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Bitmap bitmap = null, result = null;
        try{
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (Exception e) {
            System.out.println("No photo source selected");
            return;
        }
        try {
            result = bitmap.copy(Bitmap.Config.RGB_565, true);
        } catch (Exception e) {
            System.out.println("No photo source selected");
            return;
        }

        switch(position){
            case 1:
                result = PixelArtFilter.PixelArtFilter(result);
                break;
            case 2:
                result = BlurFilter.BlurFilter(result);
                break;
            case 3:
                result = InvertFilter.InvertFilter(result);
                break;
            case 4:
                result = GreyscaleFilter.GreyscaleFilter(result);
                break;
            case 5:
                //TO DO
                break;
            default:
                return;
        }
        imageView.setImageBitmap(result);
    }

    private void chooseFlipOption(){

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }
}
