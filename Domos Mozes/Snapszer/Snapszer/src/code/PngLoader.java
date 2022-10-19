package code;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PngLoader {
    public static BufferedImage loadImage(String imgId) {
        BufferedImage image = null;
        try {
            URL url = PngLoader.class.getResource("/res/" + imgId);
            //System.out.println(url.getPath());
            image = ImageIO.read(url);
        } catch (MalformedURLException mue) {
            System.err.println("url: " + mue.getMessage());
        } catch (IllegalArgumentException iae) {
            System.err.println("arg: " + iae.getMessage());
        } catch (IOException ioe) {
            System.err.println("read: " + ioe.getMessage());
        }
        if (image == null) {
            image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
            System.out.println("unable to load image, returning default");
        }
        return image;
    }
    public static BufferedImage rotate(BufferedImage image, int radius) {
        BufferedImage rotatedImage = null;
        try {
            final double rads = Math.toRadians(radius);
            final double sin = Math.abs(Math.sin(rads));
            final double cos = Math.abs(Math.cos(rads));
            final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
            final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
            rotatedImage = new BufferedImage(w, h, image.getType());
            final AffineTransform at = new AffineTransform();
            at.translate(w / 2, h / 2);
            at.rotate(rads, 0, 0);
            at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
            final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            rotateOp.filter(image, rotatedImage);
        } catch (IllegalArgumentException iae) {
            System.err.println("arg: " + iae.getMessage());
        } catch (NullPointerException io) {
            System.err.println("arg: " + io.getMessage());
        }
        return rotatedImage;
    }
}

