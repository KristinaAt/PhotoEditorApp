package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class BlendFilter {
    public static Bitmap BlendFilter(Bitmap imgOne, Bitmap imgTwo) {
        int minWidth = Math.min(imgOne.getWidth(), imgTwo.getWidth());
        int minHeight = Math.min(imgOne.getHeight(), imgTwo.getHeight());
        Bitmap blendedImg = Bitmap.createBitmap(minWidth, minHeight, Bitmap.Config.RGB_565);
        for (int i = 0; i < minWidth; i++) {
            for (int j = 0; j < minHeight; j++) {
                int avgR = (Utils.getR(i, j, imgOne) + Utils.getR(i, j, imgTwo)) / 2;
                int avgG = (Utils.getG(i, j, imgOne) + Utils.getG(i, j, imgTwo)) / 2;
                int avgB = (Utils.getB(i, j, imgOne) + Utils.getB(i, j, imgTwo)) / 2;
                Utils.setR(i, j, avgR, blendedImg);
                Utils.setG(i, j, avgG, blendedImg);
                Utils.setB(i, j, avgB, blendedImg);
            }
        }
        return blendedImg;
    }
}