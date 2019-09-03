package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

import static com.example.pictureprocessing.Filters.ColourFilter.*;

public class Traverser implements Runnable {

    private int width;
    private int startRow;
    private int segmentHeight;
    private int filterNumber;
    private Bitmap img, imgTwo, blendedImg;
    private int colour;
    private double scale;

    public Traverser(int width, int startRow, int segmentHeight, int filterNumber, Bitmap img) {
        this.width = width;
        this.startRow = startRow;
        this.segmentHeight = segmentHeight;
        this.filterNumber = filterNumber;
        this.img = img;
    }

    public Traverser(int width, int startRow, int segmentHeight, int filterNumber, int colour, Bitmap img) {
        this(width, startRow, segmentHeight, filterNumber, img);
        this.colour = colour;
    }

    public Traverser(int width, int startRow, int segmentHeight, int filterNumber, Bitmap img, Bitmap imgTwo, Bitmap blendedImg) {
        this(width, startRow, segmentHeight, filterNumber, img);
        this.imgTwo = imgTwo;
        this.blendedImg = blendedImg;
    }

    public Traverser(int width, int startRow, int segmentHeight, int filterNumber, double scale, Bitmap img) {
        this(width, startRow, segmentHeight, filterNumber, img);
        this.scale = scale;
    }

    @Override
    public void run() {
        switch (filterNumber) {
            //InvertFilterCase
            case 0:
                for (int i = 0; i < width; i++) {
                    for (int j = startRow; j < startRow + segmentHeight; j++) {
                        InvertFilter.InvertPixel(i, j, img);
                    }
                }
                break;
            //ColourScale Filter
            case 1:
                for (int i = 0; i < width; i++) {
                    for (int j = startRow; j < startRow + segmentHeight; j++) {
                        switch (colour) {
                            case 0:
                                RedScalePixel(i, j, img);
                                break;
                            case 1:
                                OrangeScalePixel(i, j, img);
                                break;
                            case 2:
                                YellowScalePixel(i, j, img);
                                break;
                            case 3:
                                GreenScalePixel(i, j, img);
                                break;
                            case 4:
                                BlueScalePixel(i, j, img);
                                break;
                            case 5:
                                PurpleScalePixel(i, j, img);
                                break;
                            case 6:
                                PinkScalePixel(i, j, img);
                                break;
                            case 7:
                                AquaScalePixel(i, j, img);
                                break;
                            case 8:
                                LimeScalePixel(i, j, img);
                                break;
                            case 9:
                                WarmScalePixel(i, j, img);
                                break;
                            default:
                                return;
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < width; i++) {
                    for (int j = startRow; j < startRow + segmentHeight; j++) {
                        AdjustBrightnessFilter.ScalePixel(i, j, scale, img);
                    }
                }
                break;
            case 3:
                for (int i = 0; i < width; i++) {
                    for (int j = startRow; j < startRow + segmentHeight; j++) {
                        BlendFilter.BlendPixel(i, j, img, imgTwo, blendedImg);
                    }
                }
                break;
            case 4:
                for(int i = 0; i < width; i++){
                    for(int j = startRow; j < startRow + segmentHeight; j++){
                        ContrastFilter.adjustContrast(i, j, img, scale);
                    }
                }
                break;
            default:
                return;
        }
    }
}
