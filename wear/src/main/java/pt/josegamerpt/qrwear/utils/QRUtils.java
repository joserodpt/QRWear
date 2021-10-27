package pt.josegamerpt.qrwear.utils;

import android.graphics.Bitmap;

public class QRUtils {

    public static Bitmap trimLeave5Percent(Bitmap bitmap, int trimColor) {
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        int minY = Integer.MAX_VALUE;
        int maxY = 0;

        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                if (bitmap.getPixel(x, y) != trimColor) {
                    if (x < minX) {
                        minX = x;
                    }
                    if (x > maxX) {
                        maxX = x;
                    }
                    if (y < minY) {
                        minY = y;
                    }
                    if (y > maxY) {
                        maxY = y;
                    }
                }
            }
        }
        int xPc = (int) (bitmap.getWidth() * 0.01);
        int yPc = (int) (bitmap.getHeight() * 0.01);

        minX -= xPc;
        minY -= yPc;
        maxX += xPc;
        maxY += yPc;
        return Bitmap.createBitmap(bitmap, Math.max(minX, 0), Math.max(minY, 0),
                Math.min(maxX - minX + 1, bitmap.getWidth()), Math.min(maxY - minY + 1, bitmap.getHeight()));
    }

}
