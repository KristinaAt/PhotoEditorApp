package com.example.pictureprocessing;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class DefaultCommands {

    //Codes for the source selection
    private static int GALLERY = 1, CAMERA = 2;
    //Sets the name for the directory where the images will be saved
    private static final String IMAGE_DIRECTORY = "/tempImages";
    //Constants for dealing with magic numbers
    private static int QUALITY = 25;

    //Saves a photo in a directory of the phone using a given bitmap
    public static String saveImage(Bitmap bitmap, Context context) {
        //Compresses the given bitmap to a bytestream
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, bytes);
        //Creates a file in the directory where the photos will be saved
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        String filePath ="";
        try {
            //Creates a file whose name is based on the current date and time and has extension .jpg
            File file = new File(wallpaperDirectory, "IMG-" + Calendar.getInstance().getTimeInMillis() + ".jpg");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            //Uses the compressed bitmap and writes into the FileOutputStream
            fo.write(bytes.toByteArray());
            //Scans the file and makes it visible in the phone media
            MediaScannerConnection.scanFile(context, new String[]{file.getPath()},
                    new String[]{"image/jpeg"}, null);
            filePath = file.getPath();
            fo.close();
            //Adds to the log the information that we have saved the photo
            Log.d("TAG", "File Saved::---&gt;" + file.getAbsolutePath());
            Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    //Requests the user for multiple permissions for accessing its gallery and camera
    public static void requestMultiplePermissions(final Activity activity) {
        Dexter.withActivity(activity)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        return;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(activity.getApplicationContext(),"Some Error!", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread().check();

    }

    //Gives two options for selection an input image(gallery or camera)
    public static void showPictureDialog(Context context, final Activity activity) {
        //Creates an alert dialog for selecting one of the two options for image source
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Select Action!");
        String[] pictureDialogItems = {"Select photo from gallery", "Take a photo from camera"};
        //Calls a corresponding helper function based on the selected option
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int option) {
                switch (option) {
                    case 0:
                        choosePhotoFromGallery(activity);
                        break;
                    case 1:
                        takePhotoFromCamera(activity);
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    //Function called when photo from gallery option selected
    private static void choosePhotoFromGallery(Activity activity) {
        /*Creates a new intention describing that we require to pick a photo from the internal storage
          of the phone from the gallery and use that image later.

          Makes use of the android provider to get a filepath to where the images are stored
         */
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        //Starts trying to get the user to pick the photo using the new intention and status code for gallery
        activity.startActivityForResult(galleryIntent, GALLERY);
    }

    //Function called when take a photo with camera option selected
    private static void takePhotoFromCamera(Activity activity) {
        /*Creates a new intention describing that we want to take a photo with the camera of the phone
          and use the image later
         */
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //Starts trying to take the photo using the new intention and the status code for camera
        activity.startActivityForResult(intent, CAMERA);
    }

    /*Given an intent and a bitmap the function adds the bitmap to the intent as an extra
      so that we can pass a bitmap from the main activity to the new one
     */
    public static void addBitmapToIntent(Intent intent, Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY, stream);
        byte[] byteArray = stream.toByteArray();
        intent.putExtra("image", byteArray);
    }

    //Gets the extra named "image" from a given intent
    public static Bitmap getImageFromIntent(Intent intent){
        byte[] byteArray = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return bitmap;
    }
}
