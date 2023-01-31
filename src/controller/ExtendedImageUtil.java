package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.week1.IPhoto;
import model.week1.IPixel;
import model.week1.ImageCreator;
import model.week1.ImageCreator.PhotoType;
import model.week1.PixelImpl;

/**
 * Represents an extended set of utilities that can be used with images.
 */
public class ExtendedImageUtil extends ImageUtil {

  /**
   * Converts a file to an IPhoto.
   *
   * @param filename   the name of the file being read.
   * @param photoType  the type of IPhoto being created.
   * @param formatName the type of photo being read. Currently must be jpg, ppm, or png.
   * @return an IPhoto representing the read image.
   */
  public static IPhoto readPhoto(String filename, PhotoType photoType, String formatName) {
    BufferedImage img = null;
    try {
      switch (formatName) {
        case "jpg":
          img = ImageIO.read(new File(filename + ".jpg"));
          break;
        case "png":
          img = ImageIO.read(new File(filename + ".png"));
          break;
        case "ppm":
          return ImageUtil.readPPM(filename, photoType);
        default:
          throw new IllegalArgumentException("Invalid format name");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("could not read file");
    }
    int height = img.getHeight();
    int width = img.getWidth();
    IPixel[][] pixels = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int pixelValue = img.getRGB(j, i);
        pixels[i][j] =
            new PixelImpl(
                (pixelValue >> 16) & 0xFF, (pixelValue >> 8) & 0xFF, (pixelValue >> 0) & 0xFF);
      }
    }
    return ImageCreator.create(photoType, pixels, 255);
  }

  /**
   * Writes an IPhoto to an image.
   *
   * @param filename   the name of the file being exported.
   * @param photo      the IPhoto being converted.
   * @param formatName the file type of the exported photo. Must currently be ppm, png, or jpg.
   * @param directory  the location where the photo should be written to. If null, will default to
   *                   the default directory.
   */
  public static void writePhoto(String filename, IPhoto photo, String formatName, File directory) {
    switch (formatName) {
      case "jpg":
        writeJPEG(directory, filename, ExtendedImageUtil.asBuffered(photo));
        break;
      case "png":
        writePNG(directory, filename, ExtendedImageUtil.asBuffered(photo));
        break;
      case "ppm":
        if (directory == null) {
          writePPM(photo, filename);
        } else {
          writePPM(photo, directory.getName() + "\\" + filename);
        }
        break;

      default:
        throw new IllegalArgumentException("Format type is not supported");
    }
  }

  /**
   * Converts an IPhoto to a buffered image.
   *
   * @param photo the IPhoto being converted.
   * @return a BufferedImage with the pixel values of the IPhoto.
   */
  public static BufferedImage asBuffered(IPhoto photo) {
    IPixel[][] pixelSequence = photo.getPixelSequence();
    BufferedImage img =
        new BufferedImage(photo.getWidth(), photo.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
    //Sets every pixel in the buffered image to the values corresponding to the IPhoto pixels
    for (int i = 0; i < photo.getWidth(); i++) {
      for (int j = 0; j < photo.getHeight(); j++) {
        int color =
            (pixelSequence[j][i].getRValue() << 16)
                | (pixelSequence[j][i].getGValue() << 8)
                | pixelSequence[j][i].getBValue();
        img.setRGB(i, j, color);
      }
    }
    return img;
  }

  /**
   * Writes a JPEG from a buffered image.
   *
   * @param filename the name of the file being created.
   * @param img      the image being converted.
   */
  private static void writeJPEG(File directory, String filename, BufferedImage img) {
    try {
      File output = new File(directory, filename + ".jpg");
      ImageIO.write(img, "jpeg", output);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error in creating image");
    }
  }

  /**
   * Writes a PNG from a buffered image.
   *
   * @param filename the name of the file being created.
   * @param img      the image being converted.
   */
  private static void writePNG(File directory, String filename, BufferedImage img) {
    try {
      File output = new File(directory, filename + ".png");
      ImageIO.write(img, "png", output);
    } catch (IOException e) {
      throw new IllegalArgumentException("Error in creating image");
    }
  }
}
