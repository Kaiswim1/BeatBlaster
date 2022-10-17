import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditImage {
    private static int Truncate(int value)
    {

        if (value < 0) {
            value = 0;
        }
        else if (value > 255) {
            value = 255;
        }
        return value;
    }

    // Method 2
    // To adjust the brightness of image
    public BufferedImage AdjustBrightness(BufferedImage image, int brightnessValue) throws IOException {

        // Taking image path and reading pixels

        // Declaring an array for spectrum of colors
        int rgb[];


        // Outer loop for width of image
        for (int i = 0; i < image.getWidth(); i++) {

            // Inner loop for height of image
            for (int j = 0; j < image.getHeight(); j++) {

                rgb = image.getRaster().getPixel(
                        i, j, new int[3]);

                // Using(calling) method 1
                int red
                        = Truncate(rgb[0] + brightnessValue);
                int green
                        = Truncate(rgb[1] + brightnessValue);
                int blue
                        = Truncate(rgb[2] + brightnessValue);

                int arr[] = { red, green, blue };

                // Using setPixel() method
                image.getRaster().setPixel(i, j, arr);
            }
        }

        // Throwing changes over the image as read above
        return image;
    }

    public BufferedImage applyColorFilter(BufferedImage image, int redPercent, int greenPercent, int bluePercent) throws IOException {

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = image.getRGB(x,y);

                int alpha = (pixel>>24)&0xff;
                int red = (pixel>>16)&0xff;
                int green = (pixel>>8)&0xff;
                int blue = pixel&0xff;

                pixel = (alpha<<24) | (redPercent*red/100<<16) | (greenPercent*green/100<<8) | (bluePercent*blue/100);

                image.setRGB(x, y, pixel);
            }
        }

        return image;
    }

}
