package com.example.pictureprocessing.Filters;


import android.graphics.Bitmap;

public class FlipFilter {
    //The function flips an image horizontally
    public static Bitmap FlipHorizontalFilter(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int x = 0; x < (width / 2); x++){
            for(int y = 0; y < height; y++){
                SwapPixels(x, y, (width - x - 1), y, img);
            }
        }
        return img;
    }

    //The function flips an image vertically
    public static Bitmap FlipVerticalFilter(Bitmap img){
        int width = img.getWidth();
        int height = img.getHeight();
        for(int y = 0; y < (height/2); y++){
            for(int x = 0; x < width; x++){
                SwapPixels(x, y, x, (height - y - 1), img);
            }
        }
        return img;
    }

    //Swaps two pixels in an image given their coordinates
    private static void SwapPixels(int x1, int y1, int x2, int y2, Bitmap img){
        int RGBtemp = img.getPixel(x1, y1);
        img.setPixel(x1, y1, img.getPixel(x2, y2));
        img.setPixel(x2, y2, RGBtemp);
    }
}
