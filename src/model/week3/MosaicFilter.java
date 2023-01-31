package model.week3;

import controller.ImageUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import model.week1.IFilter;
import model.week1.IPhoto;
import model.week1.IPixel;
import model.week1.PhotoImpl;
import model.week1.PixelImpl;

/**
 * Represents a filter function object to apply a mosaic effect to a photo.
 */
public class MosaicFilter implements IFilter {

  /**
   * Number of seeds in the mosaic.
   */
  private final int seeds;

  /**
   * List of cluster in the mosaic.
   */
  private final List<Cluster> clusters;

  Random rand;

  /**
   * Initializes a new MosaicFilter.
   *
   * @param seeds number of seeds in the mosaic.
   */
  public MosaicFilter(int seeds) {
    if (seeds == 0) {
      throw new IllegalArgumentException("Seeds cannot be 0");
    }
    this.seeds = seeds;
    clusters = new ArrayList<>();
    rand = new Random();
  }

  /**
   * Initializes a new MosaicFilter for testing.
   *
   * @param seeds    number of seeds in the mosaic.
   * @param randSeed seed for the random object .
   */
  public MosaicFilter(int seeds, int randSeed) {
    this.seeds = seeds;
    clusters = new ArrayList<>();
    rand = new Random(randSeed);
  }

  @Override
  public IPhoto apply(IPhoto image) {
    ImageUtil.checkNull(image, "image");
    //Pixel sequence of the photo being processed.
    IPixel[][] sequence = image.getPixelSequence();

    //Adds a new cluster with a random x and y location within the bounds of the image. Creates as
    // many clusters as there are seeds.
    for (int i = 0; i < seeds; i++) {
      clusters.add(
          new Cluster(rand.nextInt(image.getWidth() - 1), rand.nextInt(image.getHeight() - 1)));
    }
    //For each pixel in the image, finds the closest cluster from their x and y value, then adds
    // the pixel to the cluster. It also gives the pixel a posn representing the pixels position
    // in the image.
    for (int i = 0; i < image.getHeight(); i++) {
      for (int j = 0; j < image.getWidth(); j++) {
        Cluster closestCluster = null;
        double distToClosestClust = Double.MAX_VALUE;
        for (Cluster cluster : clusters) {
          double distToClust = Math.sqrt(
              Math.pow((i - cluster.getY()), 2) + Math.pow((j - cluster.getX()), 2));
          if (distToClust < distToClosestClust) {
            closestCluster = cluster;
            distToClosestClust = distToClust;
          }
        }
        closestCluster.addPixel(sequence[i][j], new Posn(i, j));
      }
    }
    //Creates a new 2d array of IPixels, and goes through each cluster to add the pixels in the spot
    // determined by their Posn location.
    IPixel[][] toReturn = new IPixel[image.getHeight()][image.getWidth()];
    for (Cluster cluster : clusters) {
      Map<Posn, IPixel> pixelPosnMap = cluster.changePixels();
      for (Posn posn : pixelPosnMap.keySet()) {
        toReturn[posn.getX()][posn.getY()] = pixelPosnMap.get(posn);
      }
    }

    return new PhotoImpl(toReturn, image.getMaxRGBValue());
  }

  /**
   * Represents an object that stores an x and y value.
   */
  private class Posn {

    private final int x;
    private final int y;

    /**
     * Initializes a new Posn.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    private Posn(int x, int y) {
      this.x = x;
      this.y = y;
    }

    /**
     * Gets the x coordinate of the posn.
     *
     * @return the x coordinate.
     */
    private int getX() {
      return this.x;
    }

    /**
     * Gets the y coordinate of the posn.
     *
     * @return the y coordinate.
     */
    private int getY() {
      return this.y;
    }
  }

  /**
   * Represents a cluster in a mosaic image.
   */
  private class Cluster {

    private final int x;
    private final int y;
    /**
     * A map containing all IPixels in the cluster indexed by their position in the original image.
     */
    private final Map<Posn, IPixel> pixelMap;

    /**
     * Initializes a new Cluster.
     *
     * @param x the x coordinate of the middle of the cluster.
     * @param y the y coordinate of the middle of the cluster.
     */
    private Cluster(int x, int y) {
      this.x = x;
      this.y = y;
      this.pixelMap = new HashMap<>();
    }

    /**
     * Adds a pixel to the cluster.
     *
     * @param pixel the pixel being added.
     * @param posn  the position of the pixel in the original image.
     */
    private void addPixel(IPixel pixel, Posn posn) {
      pixelMap.put(posn, pixel);
    }

    /**
     * Gets the x coordinate of the cluster.
     *
     * @return the x coordinate.
     */
    private int getX() {
      return x;
    }

    /**
     * Gets the y coordinate of the cluster.
     *
     * @return the y coordinate.
     */
    private int getY() {
      return y;
    }

    /**
     * Changes every pixel in the cluster to have the same RGB value as their average.
     *
     * @return a new Map containing the original posns of the pixels and a pixel with the average
     *     RGB value.
     */
    private Map<Posn, IPixel> changePixels() {
      Map<Posn, IPixel> toReturn = new HashMap<>();
      if (pixelMap.size() != 0) {
        int averageRed = averageRed();
        int averageGreen = averageGreen();
        int averageBlue = averageBlue();
        IPixel averagePixel = new PixelImpl(averageRed, averageGreen, averageBlue);
        for (Posn posn : pixelMap.keySet()) {
          toReturn.put(posn, averagePixel);
        }
      }
      return toReturn;
    }

    /**
     * The average red value of all pixels in the cluster.
     *
     * @return the value representing the average red value of all pixels in the cluster.
     */
    private int averageRed() {
      int total = 0;
      for (Posn posn : pixelMap.keySet()) {
        total += pixelMap.get(posn).getRValue();
      }
      return total / pixelMap.size();
    }

    /**
     * The average green value of all pixels in the cluster.
     *
     * @return the value representing the average green value of all pixels in the cluster.
     */
    private int averageGreen() {
      int total = 0;
      for (Posn posn : pixelMap.keySet()) {
        total += pixelMap.get(posn).getGValue();
      }
      return total / pixelMap.size();
    }

    /**
     * The average blue value of all pixels in the cluster.
     *
     * @return the value representing the average blue value of all pixels in the cluster.
     */
    private int averageBlue() {
      int total = 0;
      for (Posn posn : pixelMap.keySet()) {
        total += pixelMap.get(posn).getBValue();
      }
      return total / pixelMap.size();
    }
  }
}
