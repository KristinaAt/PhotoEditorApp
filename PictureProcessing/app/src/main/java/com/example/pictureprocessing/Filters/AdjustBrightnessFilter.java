package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class AdjustBrightnessFilter {

  /* Brightness filter takes an image and based on the scale brightens or darkens
     the photo. For scale < 1 it will darken and for scale > 1 it will brighten
     every pixel.
  */

    public static Bitmap BrightnessFilter(Bitmap img, double scale){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                ScalePixel(i, j, scale, img);
            }
        }
        return img;
    }


    /* Helper method takes one pixel and a scale and multiplies the pixel by the
       scale unless it is going to overflow.
     */
    public static void ScalePixel(int x, int y, double scale, Bitmap img){
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);

        int newR = (int) (R * scale);
        int newG = (int) (G * scale);
        int newB = (int) (B * scale);

        if(newR > 255){
            Utils.setR(x, y,255, img);
        } else {
            Utils.setR(x, y, newR, img);
        }

        if(newG > 255){
            Utils.setG(x, y,255, img);
        } else {
            Utils.setG(x, y, newG, img);
        }

        if(newB > 255){
            Utils.setB(x, y,255, img);
        } else {
            Utils.setB(x, y, newB, img);
        }
    }
}