package com.example.pictureprocessing;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pictureprocessing.Filters.BlurFilter;
import com.example.pictureprocessing.Filters.GreyscaleFilter;
import com.example.pictureprocessing.Filters.InvertFilter;
import com.example.pictureprocessing.Filters.PixelArtFilter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Creates instances of all the buttons, image view and the spinner in the design
    private Button photoSrcBtn, applyFilter;
    private FloatingActionButton savePhotoActionBtn;
    private ImageView imageView;
    private Spinner spinner;
    private ImageButton turnLeftBtn, turnRightBtn, horizFlipBtn, vertFlipBtn;
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;
    //Constants for dealing with magic numbers
    private int ROTATE_LEFT_ANGLE = 270, ROTATE_RIGHT_ANGLE = 90;
    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DefaultCommands.requestMultiplePermissions(this);

        //Assign the buttons from the design to the instances we created
        photoSrcBtn = (Button) findViewById(R.id.blendWithBtn);
        applyFilter = (Button) findViewById(R.id.applyFilterBtn);
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

        //When the button is clicked a picture dialog with two photo source options is shown
        photoSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DefaultCommands.showPictureDialog(activity, activity);
            }
        });

        /*When pressed the button calls a helper function that processes
        the image based on the selected filter
         */
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyFilter(spinner.getSelectedItemPosition());
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

        //When the button is pressed the image is rotated to the right
        turnRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                //Try catch block for getting the bitmap from the imageView
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e) {
                    System.out.println("No photo source selected");
                    Toast.makeText(activity, "Please select a photo!", Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = com.example.pictureprocessing.Filters.RotationFilter.RotateFilter(bitmap, ROTATE_RIGHT_ANGLE);
                imageView.setImageBitmap(bitmap);
            }
        });

        //When the button is pressed the image is rotated to the left
        turnLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null;
                //Try catch block for getting the bitmap from the imageView
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e) {
                    System.out.println("No photo source selected");
                    Toast.makeText(activity, "Please select a photo!", Toast.LENGTH_SHORT).show();
                    return;
                }

                bitmap = com.example.pictureprocessing.Filters.RotationFilter.RotateFilter(bitmap, ROTATE_LEFT_ANGLE);
                imageView.setImageBitmap(bitmap);
            }
        });

        //When the button is pressed the image is flipped horizontally
        horizFlipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null, result = null;
                //Try catch block for getting the bitmap from the imageView
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e) {
                    System.out.println("No photo source selected");
                    Toast.makeText(activity, "Please select a photo!", Toast.LENGTH_SHORT).show();
                }
                //Try catch block for copying the bitmap from the imageView
                try {
                    result = bitmap.copy(Bitmap.Config.RGB_565, true);
                } catch (Exception e) {
                    System.out.println("No photo source selected");
                    return;
                }

                result = com.example.pictureprocessing.Filters.FlipFilter.FlipHorizontalFilter(result);
                imageView.setImageBitmap(result);
            }
        });

        //When the button is pressed the image is flipped vertically
        vertFlipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = null, result = null;
                //Try catch block for getting the bitmap from the imageView
                try {
                    bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                } catch (Exception e) {
                    System.out.println("No photo source selected");
                    Toast.makeText(activity, "Please select a photo!", Toast.LENGTH_SHORT).show();
                }
                //Try catch block for copying the bitmap from the imageView
                try {
                    result = bitmap.copy(Bitmap.Config.RGB_565, true);
                } catch (Exception e) {
                    System.out.println("No photo source selected");
                    return;
                }

                result = com.example.pictureprocessing.Filters.FlipFilter.FlipVerticalFilter(result);
                imageView.setImageBitmap(result);
            }
        });

        /*Gets the image extra from the intent and sets the imageView to it so that when
         we return to the main activity we will have the imageView set to the filtered image
          */
        Bitmap bitmap = null;
        try {
            File file = new File(this.getIntent().getStringExtra("filePath"));
            bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
            file.delete();
        } catch (Exception e) {
            System.out.println("No photo selected");
        }
    }

    //Based on the result code it gets a bitmap and sets the imageView
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

    public void applyFilter(int position) {
        Bitmap bitmap = null, result = null;
        //Try catch block for getting the bitmap from the imageView
        try {
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } catch (Exception e) {
            System.out.println("No photo source selected");
            Toast.makeText(activity, "Please select a photo!", Toast.LENGTH_SHORT).show();
            return;
        }
        //Try catch block for copying the bitmap from the imageView
        try {
            result = bitmap.copy(Bitmap.Config.RGB_565, true);
        } catch (Exception e) {
            System.out.println("No photo source selected");
            return;
        }
        //Switch case for filtering the image base on the option selected from the drop down menu4
        String filePath = "";
        switch (position) {
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
                //Adds the image which is to be filtered to the BrightnessContrastActivity intent
                Intent intentBrightness = new Intent(activity, BrightnessContrastActivity.class);
                filePath = DefaultCommands.saveImage(bitmap, this);
                intentBrightness.putExtra("filePath", filePath);
                startActivity(intentBrightness);
                return;
            case 6:
                //Adds the image which is to be filtered to the ColourScaleActivity intent
                Intent intentColourFilter = new Intent(activity, ColourScaleActivity.class);
                filePath = DefaultCommands.saveImage(bitmap, this);
                intentColourFilter.putExtra("filePath", filePath);
                startActivity(intentColourFilter);
                return;
            case 7:
                //Adds the image which is to be filtered to the BlendFilterActivity intent
                Intent intentBlendFilter = new Intent(activity, BlendFilterActivity.class);
                filePath = DefaultCommands.saveImage(bitmap, this);
                intentBlendFilter.putExtra("filePath", filePath);
                startActivity(intentBlendFilter);
                return;
            default:
                return;
        }
        imageView.setImageBitmap(result);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        return;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        return;
    }
}
