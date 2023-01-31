package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import model.week1.IPhoto;
import model.week1.IPixel;
import model.week1.ImageCreator;
import model.week1.ImageCreator.PhotoType;
import model.week1.PixelCreator;
import model.week1.PixelCreator.PixelType;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Determines whether the given object is null.
   *
   * @param obj the object that is checked for null equality.
   * @param msg the string description of the object representation.
   * @throws IllegalArgumentException if the object given is null
   */
  public static void checkNull(Object obj, String msg) throws IllegalArgumentException {
    if (obj == null) {
      throw new IllegalArgumentException("the " + msg + " cannot be null");
    }
  }

  /**
   * Read an image file in the PPM format and create an IPhoto with the data given in the file.
   *
   * @param filename the path of the file.
   * @return the IPhoto created from the data in the file
   * @throws IllegalStateException if the file is not found, or the file is not a ppm file.
   */
  public static IPhoto readPPM(String filename, PhotoType photoType) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename + ".ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
    }
    StringBuilder builder = new StringBuilder();
    // read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    // now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    IPixel[][] pixels = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[i][j] = PixelCreator.createWColor(PixelType.SIMPLE_PIXEL, r, g, b);
      }
    }
    return ImageCreator.create(photoType, pixels, maxValue);
  }

  /**
   * Creates a PPM file with the data of the given IPhoto.
   *
   * @param photo the IPhoto to be exported into a PPM file.
   * @param name  the name to give to the file.
   * @throws IllegalArgumentException if a file with the given name already exists.
   * @throws IllegalStateException    if the file could not be created, the filewriter could not
   *                                  write to the file.
   */
  public static void writePPM(IPhoto photo, String name) {
    try {
      // Get the file
      File f = new File(name + ".ppm");

      // Create new file
      // if it does not exist
      if (f.createNewFile()) {
        System.out.println("File created");
      } else {
        throw new IllegalArgumentException("A file already exists with the given name");
      }
    } catch (IOException e) {
      throw new IllegalStateException("Could not create file");
    }
    try {
      FileWriter filewriter = new FileWriter(name + ".ppm");
      filewriter.write(
          "P3 "
              + photo.getWidth()
              + " "
              + photo.getHeight()
              + " "
              + photo.getMaxRGBValue()
              + stringPixels(photo));
      filewriter.close();
    } catch (IOException excep) {
      throw new IllegalStateException("Could not write to file");
    }
  }

  /**
   * Returns the pixels in the given IPhoto as a string containing their rgb values.
   *
   * @param image the image being written to a string.
   * @return a string containing the r g and b values of every pixel separated by a space.
   */
  private static String stringPixels(IPhoto image) {
    StringBuilder builder = new StringBuilder();
    for (IPixel[] rows : image.getPixelSequence()) {
      for (IPixel pixel : rows) {
        builder.append(" "
            + pixel.getColor().getR()
            + " "
            + pixel.getColor().getG()
            + " "
            + pixel.getColor().getB());
      }
    }
    return builder.toString();
  }
}
