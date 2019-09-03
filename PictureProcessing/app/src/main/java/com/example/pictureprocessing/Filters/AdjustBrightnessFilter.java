package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class AdjustBrightnessFilter {

  /* Brightness filter takes an image and based on the scale brightens or darkens
     the photo. For scale < 1 it will darken and for scale > 1 it will brighten
     every pixel.
  */

    public static Bitmap BrightnessFilter(Bitmap img, double scale){
        int numberOfThreads = 7;
        int width = img.getWidth();
        int height = img.getHeight();
        int segmentHeight = height / numberOfThreads;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < numberOfThreads; i++){
            Thread thread;
            if(i == numberOfThreads - 1){
                thread = new Thread(new Traverser(width, i*segmentHeight, height - (numberOfThreads - 1)*segmentHeight,2, scale, img));
            } else{
                thread = new Thread(new Traverser(width, i*segmentHeight, segmentHeight,2, scale, img));
            }
            threads.add(thread);
            thread.start();
        }

        for(int i = 0; i < numberOfThreads; i++){
            try{
                threads.get(i).join();
            } catch (Exception e){
            }
        }
        return img;
    }


    /* Helper method takes one pixel and a scale and multiplies the pixel by the
       scale unless it is going to overflow.
     */
    public static void ScalePixel(int x, int y, double scale, Bitmap img){
        int RGB = img.getPixel(x, y);
        int newR = (int) (Utils.getRfromRGB(RGB)*scale);
        int newG = (int) (Utils.getGfromRGB(RGB)*scale);
        int newB = (int) (Utils.getBfromRGB(RGB)*scale);
        Utils.setRGB(x, y, newR, newG, newB, img);
    }
}