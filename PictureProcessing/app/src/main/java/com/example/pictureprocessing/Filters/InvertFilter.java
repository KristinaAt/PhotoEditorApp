package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class InvertFilter {

    //A function that inverts the colours of an image
    public static Bitmap InvertFilter(Bitmap img){
        int NumberOfThreads = 7;
        int width = img.getWidth();
        int height = img.getHeight();
        int segmentHeight = (int) height / NumberOfThreads;
        ArrayList<Thread> threads = new ArrayList<>();
        for(int i = 0; i < NumberOfThreads; i++){
            Thread thread;
            if(i == NumberOfThreads - 1){
                thread = new Thread(new Traverser(width, i*segmentHeight, height - (NumberOfThreads - 1) *segmentHeight, 0, img));
            } else {
                thread = new Thread(new Traverser(width, i*segmentHeight, segmentHeight, 0, img));
            }
            threads.add(thread);
            thread.start();
        }

        for(int i = 0; i < NumberOfThreads; i++){
            try{
                threads.get(i).join();
            }catch (InterruptedException e){
                System.out.println("Error starting thread");
            }
        }
        return img;
    }

    //Helper function for inverting the RGB values of a single pixel
    public static void InvertPixel(int x, int y, Bitmap img){
        img.setPixel(x, y, 0xffffff - img.getPixel(x, y));
    }
}
