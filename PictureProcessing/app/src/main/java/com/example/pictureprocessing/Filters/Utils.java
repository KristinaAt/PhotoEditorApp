package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class Utils {

    /* Below are methods to set the RGB values both in one go and as individual components */

    public static void setRGB(int x, int y, long R, long G, long B, Bitmap img) {
        long RGB = (R << 16) | (G << 8) | B;
        img.setPixel(x, y, (int) RGB);
    }

    public static void setR(int x, int y, int R, Bitmap img) {
        int RGB = img.getPixel(x, y) & 0x00ffff;
        RGB |= (R << 16);
        img.setPixel(x, y, RGB);
    }

    public static void setG(int x, int y, int G, Bitmap img) {
        int RGB = img.getPixel(x, y) & 0xff00ff;
        RGB |= (G << 8);
        img.setPixel(x, y, RGB);
    }

    public static void setB(int x, int y, int B, Bitmap img) {
        int RGB = img.getPixel(x, y) & 0xffff00;
        RGB |= B;
        img.setPixel(x, y, RGB);
    }

  /* Below are getter methods to get the R, G and B values respectively and return their values
     using bitwise masks with the bitwise and operator
   */

    public static int getR(int x, int y, Bitmap img) {
        int ARGB = img.getPixel(x, y);
        return ((ARGB >> 16) & 0xff);
    }

    public static int getG(int x, int y, Bitmap img) {
        int ARGB = img.getPixel(x, y);
        return ((ARGB >> 8) & 0xff);
    }

    public static int getB(int x, int y, Bitmap img) {
        int ARGB = img.getPixel(x, y);
        return (ARGB & 0xff);
    }

    /* Helper method to check that a given pixel is within the scope and range of the
     specified image
   */
    public static boolean isValidPixel(int x, int y, int width, int height) {
        return ((x >= 0) && (x < width) && (y >= 0) && (y < height));
    }
}

