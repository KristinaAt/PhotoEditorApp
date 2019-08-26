package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class ColourFilter {

    // Coloured Filters for changing colours of the photo
    public static Bitmap BlueScaleFilter(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                BlueScalePixel(i, j, img);
            }
        }
        return img;
    }

    private static void RedScalePixel(int x, int y, Bitmap img){
        int R = Utils.getB(x, y, img);
        int newR = R + 10;
        if(newR > 255){
            Utils.setR(x, y,255, img);
        } else {
            Utils.setR(x, y, newR, img);
        }
    }

    private static void BlueScalePixel(int x, int y, Bitmap img){
        int B = Utils.getB(x, y, img);
        int newB = B + 10;
        if(newB > 255){
            Utils.setB(x, y,255, img);
        } else {
            Utils.setB(x, y, newB, img);
        }
    }

    private static void GreenScalePixel(int x, int y, Bitmap img){
        int G = Utils.getG(x, y, img);
        int newG = G + 10;
        if(newG > 255){
            Utils.setG(x, y,255, img);
        } else {
            Utils.setG(x, y, newG, img);
        }
    }
}