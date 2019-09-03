package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ContrastFilter {
    public static Bitmap ContrastFilter(Bitmap img, int contrast){
        int width = img.getWidth();
        int height = img.getHeight();
        int numberOfThreads = 7;
        int segmentHeight = height / numberOfThreads;
        double scale = (259*((float)contrast + 255))/(255*(259 - (float)contrast));
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < numberOfThreads; i++){
            Thread thread;
            if(i == numberOfThreads - 1){
                thread = new Thread(new Traverser(width, i*segmentHeight, height - (segmentHeight*(numberOfThreads - 1)), 4, scale, img));
            } else {
                thread = new Thread(new Traverser(width, i*segmentHeight, segmentHeight, 4, scale, img));
            }
            threads.add(thread);
            thread.start();
        }

        for(int i = 0; i <numberOfThreads; i++){
            try{
                threads.get(i).join();
            }catch (Exception e){
                System.out.println("Failed joining thread" + i);
            }
        }


        return img;
    }

    private static int getNewValue(int value, double correctionFactor){
        double result = correctionFactor*(value - 128) + 128;
        return (int) result;
    }

    public static void adjustContrast(int x, int y, Bitmap img, double scale){
        int RGB = img.getPixel(x, y);
        Utils.setRGB(x, y,  getNewValue(Utils.getRfromRGB(RGB), scale)
                            , getNewValue(Utils.getGfromRGB(RGB), scale)
                            , getNewValue(Utils.getBfromRGB(RGB), scale), img);
    }
}

