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
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 100;
        int newG = G + 60;
        int newB = B + 20;
        Utils.setR(x, y, newR, img);
        Utils.setG(x, y, newG, img);
        Utils.setB(x, y, newB, img);
    }

    public static void PurpleScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 60;
        int newB = B + 100;
        Utils.setR(x, y, newR, img);
        Utils.setB(x, y, newB, img);
    }

    public static void PinkScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 115;
        int newB = B + 80;
        Utils.setR(x, y, newR, img);
        Utils.setB(x, y, newB, img);
    }

    public static void LimeScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 50;
        int newG = G + 100;
        int newB = B + 50;
        Utils.setR(x, y, newR, img);
        Utils.setG(x, y, newG, img);
        Utils.setB(x, y, newB, img);
    }

    public static void AquaScalePixel(int x, int y, Bitmap img) {
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);
        int newG = G + 100;
        int newB = B + 100;
        Utils.setG(x, y, newG, img);
        Utils.setB(x, y, newB, img);
    }

    public static void WarmScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 100;
        int newG = G + 80;
        int newB = B + 60;
        Utils.setR(x, y, newR, img);
        Utils.setG(x, y, newG, img);
        Utils.setB(x, y, newB, img);
    }

}