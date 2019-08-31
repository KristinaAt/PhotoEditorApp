package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class ContrastFilter {
    public static Bitmap ContrastFilter(Bitmap img, int contrast){
        int width = img.getWidth();
        int height = img.getHeight();
        float correctionFactor = (259*((float)contrast + 255))/(255*(259 - (float)contrast));
        int R, G, B;
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                R = Utils.getR(i, j, img);
                Utils.setR(i, j, getNewValue(R, correctionFactor), img);
                G = Utils.getG(i, j, img);
                Utils.setG(i, j, getNewValue(G, correctionFactor), img);
                B = Utils.getB(i, j, img);
                Utils.setB(i, j, getNewValue(B, correctionFactor), img);
            }
        }
        return img;
    }

    private static int getNewValue(int value, float correctionFactor){
        float result = correctionFactor*(value - 128) + 128;
        return (int) result;
    }
}
