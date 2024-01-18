package invaders.utilities;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * This class provides utility methods for working with javafx images
 */

public class ImageUtils {
    /**
     * Loads an image from a file and fills it with a colour
     * 
     * @param imgPath the image name, the image with the given name should be in the
     *                resources directory
     * @param width   the width of the loaded image
     * @param height  the height of the loaded image
     * @param colour  the colour used to fill the image
     * @return the loaded image, fill with a single colour if the image is
     */
    public static Image loadImageWithColour(URL img_url, double width, double height, String colour) {
        try {
            String path = Path.of(img_url.toURI()).toString();

            Image originImg = new Image(new File(path).toURI().toString(), width, height, true, false);
            // Fill image with colour
            Color playerColour = Color.web(colour);

            WritableImage filledImg = new WritableImage(
                    (int) originImg.getWidth(),
                    (int) originImg.getHeight());

            for (int x = 0; x < filledImg.getWidth(); x++) {
                for (int y = 0; y < filledImg.getHeight(); y++) {
                    // If alpha is 0, set to transparent
                    if (originImg.getPixelReader().getColor(x, y).getOpacity() == 0) {
                        filledImg.getPixelWriter().setColor(x, y, Color.TRANSPARENT);
                    } else {
                        filledImg.getPixelWriter().setColor(x, y, playerColour);
                    }
                }
            }

            return filledImg;
        } catch (Exception e) {
            e.printStackTrace();
            return fillImgWithColour(new WritableImage((int) width, (int) height), colour);
        }
    }

    /**
     * Fill an image with a single colour
     * 
     * @param img    the source image
     * @param colour the colour used to fill the image
     */
    public static Image fillImgWithColour(WritableImage img, String colour) {
        Color color = Color.web(colour);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                img.getPixelWriter().setColor(x, y, color);
            }
        }

        return img;
    }
}

