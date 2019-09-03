package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utils {

    /* Below are methods to set the RGB values both in one go and as individual components */
    public static void setRGB(int x, int y, int R, int G, int B, Bitmap img){
        R = Math.min(R, 255);
        G = Math.min(G, 255);
        B = Math.min(B, 255);
        img.setPixel(x, y, ((R << 16) + (G << 8) + B));
    }

    public static void setRG(int x,  int y, int R, int G, int RGB, Bitmap img){
        R = Math.min(R, 255);
        G = Math.min(G, 255);
        RGB = (R << 16) + (G << 8) + (RGB & 0xff);
        img.setPixel(x, y, RGB);
    }

    public static void setGB(int x,  int y, int G, int B, int RGB, Bitmap img){
        G = Math.min(G, 255);
        B = Math.min(B, 255);
        RGB = (RGB & 0xff0000) + (G << 8) + B;
        img.setPixel(x, y, RGB);
    }

    public static void setRB(int x,  int y, int R, int B, int RGB, Bitmap img){
        R = Math.min(R, 255);
        B = Math.min(B, 255);
        RGB = (R << 16) + (RGB & 0xff00) + B;
        img.setPixel(x, y, RGB);
    }

    public static void setR(int x, int y, int R, Bitmap img) {
        R = Math.min(R, 255);
        int RGB = img.getPixel(x, y) & 0x00ffff;
        RGB |= (R << 16);
        img.setPixel(x, y, RGB);
    }

    public static void setR(int x, int y, int R, int RGB, Bitmap img) {
        R = Math.min(R, 255);
        RGB = (RGB & 0x00ffff) + (R << 16);

        img.setPixel(x, y, RGB);
    }

    public static void setG(int x, int y, int G, Bitmap img) {
        G = Math.min(G, 255);
        int RGB = img.getPixel(x, y) & 0xff00ff;
        RGB |= (G << 8);
        img.setPixel(x, y, RGB);
    }

    public static void setG(int x, int y, int G, int RGB, Bitmap img) {
        G = Math.min(G, 255);
        RGB = (RGB & 0xff00ff) + (G << 8);
        img.setPixel(x, y, RGB);
    }

    public static void setB(int x, int y, int B, Bitmap img) {
        B = Math.min(B, 255);
        int RGB = img.getPixel(x, y) & 0xffff00;
        RGB |= B;
        img.setPixel(x, y, RGB);
    }

    public static void setB(int x, int y, int B, int RGB, Bitmap img) {
        B = Math.min(B, 255);
        RGB = (RGB & 0xffff00) + B;
        img.setPixel(x, y, RGB);
    }

  /* Below are getter methods to get the R, G and B values respectively and return their values
     using bitwise masks with the bitwise and operator
   */

    public static int getRfromRGB(int RGB) {
        return ((RGB >> 16) & 0xff);
    }

    public static int getGfromRGB(int RGB) {
        return ((RGB >> 8) & 0xff);
    }

    public static int getBfromRGB(int RGB) {
        return (RGB & 0xff);
    }

    public static int getR(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        return ((RGB >> 16) & 0xff);
    }

    public static int getG(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        return ((RGB >> 8) & 0xff);
    }

    public static int getB(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        return (RGB & 0xff);
    }

    /* Helper method to check that a given pixel is within the scope and range of the
     specified image
   */
    public static boolean isValidPixel(int x, int y, int width, int height) {
        return ((x >= 0) && (x < width) && (y >= 0) && (y < height));
    }
}

