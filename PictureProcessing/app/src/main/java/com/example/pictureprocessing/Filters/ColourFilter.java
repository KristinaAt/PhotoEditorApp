package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ColourFilter {

    // Coloured Filters for changing colours of the photo
    public static Bitmap ScaleFilter(Bitmap img, int colour) {
        int numberOfThreads = 7;
        int width = img.getWidth();
        int height = img.getHeight();
        int segmentHeight = height / numberOfThreads;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < numberOfThreads; i++){
            Thread thread;
            if(i == numberOfThreads - 1){
                thread = new Thread(new Traverser(width, i*segmentHeight, height - (numberOfThreads - 1) *segmentHeight, 1, colour, img));
            } else {
                thread = new Thread(new Traverser(width, i*segmentHeight, segmentHeight, 1, colour, img));
            }
            threads.add(thread);
            thread.start();
        }

        for(int i = 0; i < numberOfThreads; i++) {
            try {
                threads.get(i).join();
            } catch (InterruptedException e) {
                System.out.println("Error starting thread");
            }
        }
        return img;
    }

    public static void RedScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setR(x, y, Utils.getRfromRGB(RGB) + 100, RGB, img);
    }

    public static void BlueScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setB(x, y, Utils.getBfromRGB(RGB) + 100, RGB, img);
    }

    public static void GreenScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setG(x, y, Utils.getGfromRGB(RGB) + 100, RGB, img);
    }

    public static void YellowScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setRG(x, y, Utils.getRfromRGB(RGB) + 50, Utils.getGfromRGB(RGB) + 50, RGB, img);
    }

    public static void OrangeScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setRGB(x, y, Utils.getRfromRGB(RGB) + 100, Utils.getGfromRGB(RGB) + 60, Utils.getBfromRGB(RGB) + 20, img);
    }

    public static void PurpleScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setRB(x, y, Utils.getRfromRGB(RGB) + 60, Utils.getBfromRGB(RGB) + 100, RGB, img);
    }

    public static void PinkScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setRB(x, y, Utils.getRfromRGB(RGB) + 115, Utils.getBfromRGB(RGB) + 80, RGB, img);
    }

    public static void LimeScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setRGB(x, y, Utils.getRfromRGB(RGB) + 50, Utils.getGfromRGB(RGB) + 100, Utils.getBfromRGB(RGB) + 50, img);
    }

    public static void AquaScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setGB(x, y, Utils.getGfromRGB(RGB) + 100, Utils.getBfromRGB(RGB) + 100, RGB, img);
    }

    public static void WarmScalePixel(int x, int y, Bitmap img) {
        int RGB = img.getPixel(x, y);
        Utils.setRGB(x, y, Utils.getRfromRGB(RGB) + 100, Utils.getGfromRGB(RGB) + 80, Utils.getBfromRGB(RGB) + 60, img);
    }
}