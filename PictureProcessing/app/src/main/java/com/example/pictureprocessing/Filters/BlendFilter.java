package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class BlendFilter {
    public static Bitmap BlendFilter(Bitmap imgOne, Bitmap imgTwo) {
        int minWidth = Math.min(imgOne.getWidth(), imgTwo.getWidth());
        int minHeight = Math.min(imgOne.getHeight(), imgTwo.getHeight());
        int numberOfThreads = 7;
        int segmentHeight = minHeight / numberOfThreads;
        Bitmap blendedImg = Bitmap.createBitmap(minWidth, minHeight, Bitmap.Config.RGB_565);
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < numberOfThreads; i++){
            Thread thread;
            if(i == numberOfThreads - 1){
                thread = new Thread(new Traverser(minWidth, i*segmentHeight, minHeight - (segmentHeight * (numberOfThreads - 1)), 3, imgOne, imgTwo, blendedImg));
            } else {
                thread = new Thread(new Traverser(minWidth, i*segmentHeight, segmentHeight, 3, imgOne, imgTwo, blendedImg));
            }
            threads.add(thread);
            thread.start();
        }

        for(int i = 0; i < numberOfThreads; i++){
            try{
                threads.get(i).join();
            } catch (Exception e){
                System.out.println("Failed joining thread " + i);
            }
        }
        return blendedImg;
    }

    public static void BlendPixel(int x, int y, Bitmap img, Bitmap imgTwo, Bitmap blendedImg){
        int RGBone = img.getPixel(x, y);
        int RGBtwo = imgTwo.getPixel(x, y);
        Utils.setRGB(x, y, (Utils.getRfromRGB(RGBone) + Utils.getRfromRGB(RGBtwo)) / 2,
                (Utils.getGfromRGB(RGBone) + Utils.getGfromRGB(RGBtwo)) / 2,
                (Utils.getBfromRGB(RGBone) + Utils.getBfromRGB(RGBtwo)) / 2, blendedImg);
    }
}