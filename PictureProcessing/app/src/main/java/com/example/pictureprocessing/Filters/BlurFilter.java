package com.example.pictureprocessing.Filters;

import android.graphics.Bitmap;

public class BlurFilter {

    /* Takes an image and applies helper methods to blur the image */
    public static Bitmap BlurFilter(Bitmap img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int longestDim = Math.max(height, width);
        int sqrSize = longestDim / 100;
        for (int i = 0; i < width; i += sqrSize) {
            for (int j = 0; j < height; j += sqrSize) {
                SquareBlurHelper(img, i, j, sqrSize);
            }
        }
        return img;
    }

    /* First helper method takes a square of pixels at a time based on a given square size
       and uses this to calculate the average colour for a square of pixels. Uses this
       to edit the pixels and blur the image
     */
    private static void SquareBlurHelper(Bitmap img, int x, int y, int sqrSize) {
        int width = img.getWidth();
        int height = img.getHeight();
        int numberPixels = 0;
        int RGB[] = new int[3];
        for (int j = y; j < (y + sqrSize); j++) {
            for (int i = x; i < (x + sqrSize); i++) {
                if (Utils.isValidPixel(i, j, width, height)) {
                    RGB[0] += Utils.getR(i, j, img);
                    RGB[1] += Utils.getG(i, j, img);
                    RGB[2] += Utils.getB(i, j, img);
                    numberPixels++;
                } else {
                    break;
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            RGB[i] = (int) Math.floor(RGB[i] / numberPixels);
        }

        for (int j = y; j < (y + sqrSize); j++) {
            for (int i = x; i < (x + sqrSize); i++) {
                if (Utils.isValidPixel(i, j, width, height)) {
                    Utils.setRGB(i, j, RGB[0], RGB[1], RGB[2], img);
                } else {
                    break;
                }
            }
        }
    }

}
