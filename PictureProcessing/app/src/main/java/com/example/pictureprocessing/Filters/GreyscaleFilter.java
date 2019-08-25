package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class GreyscaleFilter {
    //A function that grayscales the colours of an image
    public static Bitmap GreyscaleFilter(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                AveragePixel(i, j, img);
            }
        }
        return img;
    }

    //Helper function for averaging the RGB values of a single pixel
    private static void AveragePixel(int x, int y, Bitmap img){
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);
        int average = (R + G + B) / 3;
        Utils.setR(x, y, average, img);
        Utils.setG(x, y, average, img);
        Utils.setB(x, y, average, img);
    }

}
