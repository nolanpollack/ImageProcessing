import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import model.week1.BlurFilter;
import model.week1.CheckerboardImage;
import model.week1.GrayscaleColorTransformation;
import model.week1.IColor;
import model.week1.IModelImage;
import model.week1.IPhoto;
import model.week1.IPixel;
import model.week1.OGColor;
import model.week1.PixelImpl;
import model.week1.SepiaColorTransformation;
import model.week1.SharpenFilter;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that holds the tests for the functionality/exceptions of the model classes.
 */
public abstract class ModelImplTests {

  private IModelImage model;
  private IPhoto image;
  private IPhoto image2;

  @Before
  public void init() {
    model = constructModel();
    IColor color1 = new OGColor(255, 0, 255);
    IColor color2 = new OGColor(255, 0, 0);
    image = new CheckerboardImage(1, 2, 255, color1, color2, 1);
    image2 = new CheckerboardImage(3, 3, 100, color1, color2, 1);
  }

  public abstract IModelImage constructModel();

  @Test
  public void testAddPhotoNullTagException() {
    try {
      model.addPhoto(null, image);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag cannot be null", e.getMessage());
    }
  }

  @Test
  public void testAddPhotoTagInUsage() {
    model.addPhoto("whatever", image);
    try {
      model.addPhoto("whatever", image);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag is already in use", e.getMessage());
    }
  }

  @Test
  public void testCorrectAddPhoto() {
    model.addPhoto("first", image);
    assertTrue(model.getPhoto("first").equals(image));
  }

  @Test
  public void testReplaceNullTagException() {
    try {
      model.replace(null, image);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag cannot be null", e.getMessage());
    }
  }

  @Test
  public void testReplaceTagInUsage() {
    try {
      model.replace("whatever", image);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag whatever does not exist", e.getMessage());
    }
  }

  @Test
  public void testCorrectReplace() {
    model.addPhoto("replaceable", image);
    assertTrue(model.getPhoto("replaceable").equals(image));
    model.replace("replaceable", image2);
    assertTrue(model.getPhoto("replaceable").equals(image2));
  }

  @Test
  public void testRemovePhotoNullTagException() {
    try {
      model.removePhoto(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag cannot be null", e.getMessage());
    }
  }

  @Test
  public void testRemovePhotoInvalidTag() {
    try {
      model.removePhoto("what");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The tag what does not exist", e.getMessage());
    }
  }

  @Test
  public void testCorrectRemovePhoto() {
    model.addPhoto("whatever", image);
    assertTrue(model.getPhoto("whatever").equals(image));
    model.removePhoto("whatever");
    try {
      model.getPhoto("whatever");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The tag whatever does not exist", e.getMessage());
    }
  }

  @Test
  public void testGetPhotoNullTagException() {
    try {
      model.getPhoto(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag cannot be null", e.getMessage());
    }
  }

  @Test
  public void testGetPhotoInvalidTag() {
    try {
      model.getPhoto("nonexistent");
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The tag nonexistent does not exist", e.getMessage());
    }
  }

  @Test
  public void testCorrectGetPhoto() {
    model.addPhoto("correct", image2);
    assertTrue(model.getPhoto("correct").equals(image2));
  }

  @Test
  public void applyFilterNullTagException() {
    try {
      model.applyFilter(null, new BlurFilter());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the tag cannot be null", e.getMessage());
    }
  }

  @Test
  public void applyFilterNullFilterException() {
    try {
      model.applyFilter("hello", null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("the filter cannot be null", e.getMessage());
    }
  }

  @Test
  public void applyFilterInvalidTag() {
    try {
      model.applyFilter("hell", new GrayscaleColorTransformation());
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("The tag hell does not exist", e.getMessage());
    }
  }

  @Test
  public void applySepiaFilter() {
    model.addPhoto("sepia", image);
    IPhoto alteredPhoto = model.applyFilter("sepia", new SepiaColorTransformation());
    IPixel pixel2 =
        new PixelImpl(
            (int) (.393 * 255 + .189 * 255),
            (int) (.349 * 255 + .168 * 255),
            (int) (.272 * 255 + .131 * 255));
    IPixel pixel1 = new PixelImpl((int) (.393 * 255), (int) (.349 * 255), (int) (.272 * 255));
    IPixel pixelOG1 = new PixelImpl(255, 0, 0);
    IPixel pixelOG2 = new PixelImpl(255, 0, 255);
    // altered photo created the sepia filter correctly
    assertEquals(pixel1.getRValue(), alteredPhoto.getPixel(0, 0).getRValue());
    assertEquals(pixel1.getGValue(), alteredPhoto.getPixel(0, 0).getGValue());
    assertEquals(pixel1.getBValue(), alteredPhoto.getPixel(0, 0).getBValue());

    assertEquals(pixel2.getRValue(), alteredPhoto.getPixel(0, 1).getRValue());
    assertEquals(pixel2.getGValue(), alteredPhoto.getPixel(0, 1).getGValue());
    assertEquals(pixel2.getBValue(), alteredPhoto.getPixel(0, 1).getBValue());

    // original photo in the string tag remains the same
    assertEquals(pixelOG1.getRValue(), model.getPhoto("sepia").getPixel(0, 0).getRValue());
    assertEquals(pixelOG1.getGValue(), model.getPhoto("sepia").getPixel(0, 0).getGValue());
    assertEquals(pixelOG1.getBValue(), model.getPhoto("sepia").getPixel(0, 0).getBValue());

    assertEquals(pixelOG2.getRValue(), model.getPhoto("sepia").getPixel(0, 1).getRValue());
    assertEquals(pixelOG2.getGValue(), model.getPhoto("sepia").getPixel(0, 1).getGValue());
    assertEquals(pixelOG2.getBValue(), model.getPhoto("sepia").getPixel(0, 1).getBValue());
  }

  @Test
  public void applyGrayscaleFilter() {
    model.addPhoto("grayscale", image);
    IPhoto alteredPhoto = model.applyFilter("grayscale", new GrayscaleColorTransformation());
    IPixel pixel2 =
        new PixelImpl(
            (int) (.2126 * 255 + .0722 * 255),
            (int) (.2126 * 255 + .0722 * 255),
            (int) (.2126 * 255 + .0722 * 255));
    IPixel pixel1 = new PixelImpl((int) (.2126 * 255), (int) (.2126 * 255), (int) (.2126 * 255));
    IPixel pixelOG1 = new PixelImpl(255, 0, 0);
    IPixel pixelOG2 = new PixelImpl(255, 0, 255);
    // altered photo created the sepia filter correctly
    assertEquals(pixel1.getRValue(), alteredPhoto.getPixel(0, 0).getRValue());
    assertEquals(pixel1.getGValue(), alteredPhoto.getPixel(0, 0).getGValue());
    assertEquals(pixel1.getBValue(), alteredPhoto.getPixel(0, 0).getBValue());

    assertEquals(pixel2.getRValue(), alteredPhoto.getPixel(0, 1).getRValue());
    assertEquals(pixel2.getGValue(), alteredPhoto.getPixel(0, 1).getGValue());
    assertEquals(pixel2.getBValue(), alteredPhoto.getPixel(0, 1).getBValue());

    // original photo in the string tag remains the same
    assertEquals(pixelOG1.getRValue(), model.getPhoto("grayscale").getPixel(0, 0).getRValue());
    assertEquals(pixelOG1.getGValue(), model.getPhoto("grayscale").getPixel(0, 0).getGValue());
    assertEquals(pixelOG1.getBValue(), model.getPhoto("grayscale").getPixel(0, 0).getBValue());

    assertEquals(pixelOG2.getRValue(), model.getPhoto("grayscale").getPixel(0, 1).getRValue());
    assertEquals(pixelOG2.getGValue(), model.getPhoto("grayscale").getPixel(0, 1).getGValue());
    assertEquals(pixelOG2.getBValue(), model.getPhoto("grayscale").getPixel(0, 1).getBValue());
  }

  @Test
  public void applyBlurFilter() {
    model.addPhoto("blur", image);
    IPhoto alteredPhoto = model.applyFilter("blur", new BlurFilter());
    int r1 = (int) (255 * (1.0 / 4.0));
    int r2 = (int) (255 * (1.0 / 8.0));
    int pixel1r = r1 + r2;
    IPixel pixel1 = new PixelImpl(pixel1r, (int) (0.0), (int) (255 * (1.0 / 8.0)));

    int r3 = (int) (255 * (1.0 / 8.0));
    int r4 = (int) (255 * (1.0 / 4.0));
    int pixel2r = r3 + r4;
    IPixel pixel2 = new PixelImpl((int) (pixel2r), (int) (0.0), (int) (255 * (1.0 / 4.0)));

    IPixel pixelOG1 = new PixelImpl(255, 0, 0);
    IPixel pixelOG2 = new PixelImpl(255, 0, 255);
    // altered photo created the sepia filter correctly
    assertEquals(pixel1.getRValue(), alteredPhoto.getPixel(0, 0).getRValue());
    assertEquals(pixel1.getGValue(), alteredPhoto.getPixel(0, 0).getGValue());
    assertEquals(pixel1.getBValue(), alteredPhoto.getPixel(0, 0).getBValue());

    assertEquals(pixel2.getRValue(), alteredPhoto.getPixel(0, 1).getRValue());
    assertEquals(pixel2.getGValue(), alteredPhoto.getPixel(0, 1).getGValue());
    assertEquals(pixel2.getBValue(), alteredPhoto.getPixel(0, 1).getBValue());

    // original photo in the string tag remains the same
    assertEquals(pixelOG1.getRValue(), model.getPhoto("blur").getPixel(0, 0).getRValue());
    assertEquals(pixelOG1.getGValue(), model.getPhoto("blur").getPixel(0, 0).getGValue());
    assertEquals(pixelOG1.getBValue(), model.getPhoto("blur").getPixel(0, 0).getBValue());

    assertEquals(pixelOG2.getRValue(), model.getPhoto("blur").getPixel(0, 1).getRValue());
    assertEquals(pixelOG2.getGValue(), model.getPhoto("blur").getPixel(0, 1).getGValue());
    assertEquals(pixelOG2.getBValue(), model.getPhoto("blur").getPixel(0, 1).getBValue());
  }

  @Test
  public void applySharpenFilter() {
    IColor color1 = new OGColor(16, 64, 32);
    IColor color2 = new OGColor(32, 16, 64);
    IPhoto photo1 = new CheckerboardImage(1, 2, 255, color1, color2, 1);
    model.addPhoto("sharpen", photo1);
    IPhoto alteredPhoto = model.applyFilter("sharpen", new SharpenFilter());
    int r1 = (int) (32 * (1.0));
    int r2 = (int) (16 * (1.0 / 4.0));
    int g1 = (int) (16 * (1.0));
    int g2 = (int) (64 * (1.0 / 4.0));
    int b1 = (int) (64 * (1.0));
    int b2 = (int) (32 * (1.0 / 4.0));

    int pixel1r = r1 + r2;
    int pixel1g = g1 + g2;
    int pixel1b = b1 + b2;
    IPixel pixel1 = new PixelImpl(pixel1r, pixel1g, pixel1b);

    int r3 = (int) (32 * (1.0 / 4.0));
    int r4 = (int) (16 * (1.0));
    int g3 = (int) (16 * (1.0 / 4.0));
    int g4 = (int) (64 * (1.0));
    int b3 = (int) (64 * (1.0 / 4.0));
    int b4 = (int) (32 * (1.0));
    int pixel2r = r3 + r4;
    int pixel2g = g3 + g4;
    int pixel2b = b3 + b4;
    IPixel pixel2 = new PixelImpl(pixel2r, pixel2g, pixel2b);

    IPixel pixelOG1 = new PixelImpl(32, 16, 64);
    IPixel pixelOG2 = new PixelImpl(16, 64, 32);
    // altered photo created the sepia filter correctly
    assertEquals(pixel1.getRValue(), alteredPhoto.getPixel(0, 0).getRValue());
    assertEquals(pixel1.getGValue(), alteredPhoto.getPixel(0, 0).getGValue());
    assertEquals(pixel1.getBValue(), alteredPhoto.getPixel(0, 0).getBValue());

    assertEquals(pixel2.getRValue(), alteredPhoto.getPixel(0, 1).getRValue());
    assertEquals(pixel2.getGValue(), alteredPhoto.getPixel(0, 1).getGValue());
    assertEquals(pixel2.getBValue(), alteredPhoto.getPixel(0, 1).getBValue());

    // original photo in the string tag remains the same
    assertEquals(pixelOG1.getRValue(), model.getPhoto("sharpen").getPixel(0, 0).getRValue());
    assertEquals(pixelOG1.getGValue(), model.getPhoto("sharpen").getPixel(0, 0).getGValue());
    assertEquals(pixelOG1.getBValue(), model.getPhoto("sharpen").getPixel(0, 0).getBValue());

    assertEquals(pixelOG2.getRValue(), model.getPhoto("sharpen").getPixel(0, 1).getRValue());
    assertEquals(pixelOG2.getGValue(), model.getPhoto("sharpen").getPixel(0, 1).getGValue());
    assertEquals(pixelOG2.getBValue(), model.getPhoto("sharpen").getPixel(0, 1).getBValue());
  }

  @Test
  public void testMultipleFilters() {
    model.addPhoto("sepia", image);
    IPhoto alteredPhoto = model.applyFilter("sepia", new SepiaColorTransformation());
    model.addPhoto("altered", alteredPhoto);
    IPhoto altered2 = model.applyFilter("altered", new BlurFilter());
    IPixel pixelOG2 =
        new PixelImpl(
            (int) (.393 * 255 + .189 * 255),
            (int) (.349 * 255 + .168 * 255),
            (int) (.272 * 255 + .131 * 255));
    IPixel pixelOG1 = new PixelImpl((int) (.393 * 255), (int) (.349 * 255), (int) (.272 * 255));
    IPixel pixel1 =
        new PixelImpl(
            (int) (pixelOG1.getRValue() * (1.0 / 4.0) + pixelOG2.getRValue() * (1.0 / 8.0)),
            (int) (pixelOG1.getGValue() * (1.0 / 4.0) + pixelOG2.getGValue() * (1.0 / 8.0)),
            (int) (pixelOG1.getBValue() * (1.0 / 4.0) + pixelOG2.getBValue() * (1.0 / 8.0)));
    IPixel pixel2 =
        new PixelImpl(
            (int) (pixelOG1.getRValue() * (1.0 / 8.0) + pixelOG2.getRValue() * (1.0 / 4.0)),
            (int) (pixelOG1.getGValue() * (1.0 / 8.0) + pixelOG2.getGValue() * (1.0 / 4.0)),
            (int) (pixelOG1.getBValue() * (1.0 / 8.0) + pixelOG2.getBValue() * (1.0 / 4.0)));
    // altered photo created the blur over sepia filter correctly
    assertEquals(pixel1.getRValue(), altered2.getPixel(0, 0).getRValue());
    assertEquals(pixel1.getGValue(), altered2.getPixel(0, 0).getGValue());
    assertEquals(29, altered2.getPixel(0, 0).getBValue());

    assertEquals(pixel2.getRValue(), altered2.getPixel(0, 1).getRValue());
    assertEquals(pixel2.getGValue(), altered2.getPixel(0, 1).getGValue());
    assertEquals(33, altered2.getPixel(0, 1).getBValue());

    // original photo in the string tag remains the same
    assertEquals(pixelOG1.getRValue(), model.getPhoto("altered").getPixel(0, 0)
        .getRValue());
    assertEquals(pixelOG1.getGValue(), model.getPhoto("altered").getPixel(0, 0)
        .getGValue());
    assertEquals(pixelOG1.getBValue(), model.getPhoto("altered").getPixel(0, 0)
        .getBValue());

    assertEquals(pixelOG2.getRValue(), model.getPhoto("altered").getPixel(0, 1)
        .getRValue());
    assertEquals(pixelOG2.getGValue(), model.getPhoto("altered").getPixel(0, 1)
        .getGValue());
    assertEquals(pixelOG2.getBValue(), model.getPhoto("altered").getPixel(0, 1)
        .getBValue());
  }
}
