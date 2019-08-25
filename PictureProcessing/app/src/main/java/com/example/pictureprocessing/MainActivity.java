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
    //Sets the name for the directory where the images will be saved
    private static final String IMAGE_DIRECTORY = "/tempImages";
    //Codes for the source selection
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestMultiplePermissions();

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
                showPictureDialog();
            }
        });

        savePhotoActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveImage(((BitmapDrawable) imageView.getDrawable()).getBitmap());
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

    //Gives two options for selection an input image(gallery or camera)
    private void showPictureDialog() {
        //Creates an alert dialog for selecting one of the two options for image source
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action!");
        String[] pictureDialogItems = {"Select photo from gallery", "Take a photo from camera"};
        //Calls a corresponding helper function based on the selected option
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int option) {
                switch (option) {
                    case 0:
                        choosePhotoFromGallery();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    //Function called when photo from gallery option selected
    private void choosePhotoFromGallery() {
        /*Creates a new intention describing that we require to pick a photo from the internal storage
          of the phone from the gallery and use that image later.

          Makes use of the android provider to get a filepath to where the images are stored
         */
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //Starts trying to get the user to pick the photo using the new intention and status code for gallery
        startActivityForResult(galleryIntent, GALLERY);
    }

    //Function called when take a photo with camera option selected
    private void takePhotoFromCamera() {
        /*Creates a new intention describing that we want to take a photo with the camera of the phone
          and use the image later
         */
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //Starts trying to take the photo using the new intention and the status code for camera
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == this.RESULT_CANCELED) {
            Toast.makeText(MainActivity.this, "Cancelled!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (requestCode == CAMERA) {
            //Gets the bitmap from the taken photo and sets the imageView to it
            Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    //Saves a photo in a directory of the phone using a given bitmap
    private void saveImage(Bitmap bitmap) {
        //Compresses the given bitmap to a bytestream
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        //Creates a file in the directory where the photos will be saved
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            //Creates a file whose name is based on the current date and time and has extension .jpg
            File file = new File(wallpaperDirectory, "IMG-" + Calendar.getInstance().getTimeInMillis() + ".jpg");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            //Uses the compressed bitmap and writes into the FileOutputStream
            fo.write(bytes.toByteArray());
            //Scans the file and makes it visible in the phone media
            MediaScannerConnection.scanFile(this, new String[]{file.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            //Adds to the log the information that we have saved the photo
            Log.d("TAG", "File Saved::---&gt;" + file.getAbsolutePath());
            Toast.makeText(MainActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Requests the user for multiple permissions for accessing its gallery and camera
    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(),"Some Error!", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread().check();

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
