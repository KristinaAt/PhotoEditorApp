package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

//Fixing Github issues

public class RotationFilter {
    /*Given an image the function rotates the image based on
    the rotate angle and calls a helper function for each of
    the three cases : 90, 180 or 270 degrees
     */
    public static Bitmap RotateFilter(Bitmap img, int rotateAngle) {
        int width = img.getWidth();
        int height = img.getHeight();
        Bitmap rotatedImg = Bitmap.createBitmap(height, width, Bitmap.Config.RGB_565);
        switch (rotateAngle) {
            case 90:
                RotateFilter90(img, rotatedImg);
                return rotatedImg;
            case 180:
                rotatedImg = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                RotateFilter180(img, rotatedImg);
                return rotatedImg;
            case 270:
                RotateFilter270(img, rotatedImg);
                return rotatedImg;
            default:
                System.out.println("Invalid rotation angle!");
        }
        return null;
    }

    /* Given an image it traverses its pixels and populates the output image transferring the pixels as required
       The three helper methods are called based on the rotation angle */
    private static void RotateFilter90(Bitmap originalImg, Bitmap rotatedImg) {
        int width = originalImg.getWidth();
        int height = originalImg.getHeight();
        int RGB;
        for(int i =0; i < width; i++){
            for(int j = 0; j < height; j++){
                RGB = originalImg.getPixel(i, j);
                rotatedImg.setPixel(height - j - 1, i, RGB);
            }
        }
    }

    private static void RotateFilter180(Bitmap originalImg, Bitmap rotatedImg){
        int width = originalImg.getWidth();
        int height = originalImg.getHeight();
        int RGB;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                RGB = originalImg.getPixel(i, j);
                rotatedImg.setPixel(width - i - 1, height - j - 1, RGB);
            }
        }
    }

    private static void RotateFilter270(Bitmap originalImg, Bitmap rotatedImg) {
        int width = originalImg.getWidth();
        int height = originalImg.getHeight();
        int RGB;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                RGB = originalImg.getPixel(i, j);
                rotatedImg.setPixel(j, width - i - 1, RGB);
            }
        }
    }
}