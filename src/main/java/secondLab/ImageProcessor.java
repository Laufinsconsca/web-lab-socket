package secondLab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {
    private static final double[][] kernel = {{1./16, 1./8, 1./16},{1./8, 1./4, 1./8},{1./16, 1./8, 1./16}};

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("secondLab/image.jpg"));
            applyFilter(image, kernel);
            //ImageIO.write()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void applyFilter(BufferedImage image, double[][] kernel){
        for (int i = 1; i < image.getWidth() - 1; i++) {
            for (int j = 1; j < image.getHeight() - 1; j++) {
                applyToAPixel(image, kernel, i, j);
            }
        }
    }

    private static void applyToAPixel(BufferedImage image, double[][] filter, int x, int y) {
        if (x == 0 || x == image.getWidth() - 1) {
            throw new RuntimeException("A x-axis boundary pixel");
        }
        if (y == 0 || y == image.getHeight() - 1) {
            throw new RuntimeException("A y-axis boundary pixel");
        }
        if (x < 0 || y < 0 || x > image.getWidth() - 1 || y > image.getHeight() - 1) {
            throw new RuntimeException("Wrong coordinates");
        }
        int pixel;
        for (int i = x - 1; i < x + 1; i++) {
            for (int j = y - 1; j < y + 1; j++) {
                pixel = image.getRGB(i, j);
                double filterBit = filter[i - x + 1][j - y + 1];
                image.setRGB(i, j, (((int)(((pixel & 0xff000000) >> 24)*filterBit)) << 24)
                                | (((int)(((pixel & 0xff0000) >> 16)*filterBit)) << 16)
                                | (((int)(((pixel & 0xff00) >> 8)*filterBit)) << 8)
                                | ((int)((pixel & 0xff)*filterBit)));
            }
        }
    }
}
