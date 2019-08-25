package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class InvertFilter {

    //A function that inverts the colours of an image
    public static Bitmap InvertFilter(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                InvertPixel(i, j, img);
            }
        }
        return img;
    }

    //Helper function for inverting the RGB values of a single pixel
    private static void InvertPixel(int x, int y, Bitmap img){
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);

        Utils.setR(x, y, 255 - R, img);
        Utils.setG(x, y, 255 - G, img);
        Utils.setB(x, y, 255 - B, img);
    }
}
