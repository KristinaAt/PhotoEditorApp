package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class ColourFilter {

    // Coloured Filters for changing colours of the photo
    public static Bitmap ScaleFilter(Bitmap img, int colour) {
        int width = img.getWidth();
        int height = img.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                switch (colour) {
                    case 0:
                        RedScalePixel(i, j, img);
                        break;
                    case 1:
                        GreenScalePixel(i, j, img);
                        break;
                    case 2:
                        BlueScalePixel(i, j, img);
                        break;
                    case 3:
                        YellowScalePixel(i, j, img);
                        break;
                    case 4:
                        OrangeScalePixel(i, j, img);
                        break;
                    case 5:
                        PurpleScalePixel(i, j, img);
                        break;
                    case 6:
                        PinkScalePixel(i, j, img);
                        break;
                    case 7:
                        LimeScalePixel(i, j, img);
                        break;
                    case 8:
                        AquaScalePixel(i, j, img);
                        break;
                    case 9:
                        WarmScalePixel(i, j, img);
                        break;
                    default:
                        return img;
                }
            }
        }
        return img;
    }

    private static void RedScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getB(x, y, img);
        int newR = R + 100;
        Utils.setR(x, y, newR, img);
    }

    private static void BlueScalePixel(int x, int y, Bitmap img) {
        int B = Utils.getB(x, y, img);
        int newB = B + 100;
        Utils.setB(x, y, newB, img);
    }

    private static void GreenScalePixel(int x, int y, Bitmap img) {
        int G = Utils.getG(x, y, img);
        int newG = G + 100;
        Utils.setG(x, y, newG, img);
    }

    private static void YellowScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int G = Utils.getG(x, y, img);
        int newR = R + 50;
        int newG = G + 50;
        Utils.setR(x, y, newR, img);
        Utils.setG(x, y, newG, img);
        //Utils.setRGB(x, y, newR, newG, Utils.getB(x, y, img), img);
    }

    private static void OrangeScalePixel(int x, int y, Bitmap img) {
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

    private static void PurpleScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 60;
        int newB = B + 100;
        Utils.setR(x, y, newR, img);
        Utils.setB(x, y, newB, img);
    }

    private static void PinkScalePixel(int x, int y, Bitmap img) {
        int R = Utils.getR(x, y, img);
        int B = Utils.getB(x, y, img);
        int newR = R + 115;
        int newB = B + 80;
        Utils.setR(x, y, newR, img);
        Utils.setB(x, y, newB, img);
    }

    private static void LimeScalePixel(int x, int y, Bitmap img) {
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

    private static void AquaScalePixel(int x, int y, Bitmap img) {
        int G = Utils.getG(x, y, img);
        int B = Utils.getB(x, y, img);
        int newG = G + 100;
        int newB = B + 100;
        Utils.setG(x, y, newG, img);
        Utils.setB(x, y, newB, img);
    }

    private static void WarmScalePixel(int x, int y, Bitmap img) {
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