package commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.ExportCommand;
import controller.ExtendedImageUtil;
import controller.IPhotoCommand;
import controller.Status;
import controller.StatusResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.week1.IPhoto;
import model.week1.IPixel;
import model.week1.ImageCreator.PhotoType;
import model.week1.PhotoImpl;
import model.week1.PixelImpl;
import model.week2.ILayerModel;
import model.week2.LayerModelImpl;
import org.junit.Test;

/**
 * Class that holds the tests for the functionality of the ExportCommand class and its exceptions.
 */
public class ExportCommandTests {

  private ILayerModel model = new LayerModelImpl();
  private IPhotoCommand exportCommand = new ExportCommand(model);
  private List<String> list;

  @Test
  public void testToString() {
    assertEquals("export", exportCommand.toString());
  }

  @Test
  public void testOnlyOneInput() {
    list = new ArrayList<>(Arrays.asList("export"));
    StatusResult result = exportCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testMoreThan2Input() {
    list = new ArrayList<>(Arrays.asList("export this and that"));
    StatusResult result = exportCommand.run(list);
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.INVALID_INPUT, null, null).getThird(),
        result.getThird());
  }

  @Test
  public void testNoVisibleLayersWONullPhotos() {
    model.createLayer("hello");
    list = new ArrayList<>(Arrays.asList("export", "hello.jpg"));
    try {
      exportCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("no valid layer to retrieve", e.getMessage());
    }
  }

  @Test
  public void testInvalidFileType() {
    model.createLayer("hello");
    model.changeCurrent("hello");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(10, 20, 30);
    pixels[0][1] = new PixelImpl(30, 2, 10);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    list = new ArrayList<>(Arrays.asList("export", "hello.gif"));
    try {
      exportCommand.run(list);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("Format type is not supported", e.getMessage());
    }
  }

  @Test
  public void testExportJPG() {
    model.createLayer("hello");
    model.changeCurrent("hello");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(100, 100, 100);
    pixels[0][1] = new PixelImpl(0, 0, 0);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    list = new ArrayList<>(Arrays.asList("export", "try.jpg"));
    StatusResult result = exportCommand.run(list);

    IPhoto exported = ExtendedImageUtil.readPhoto("try",
        PhotoType.SIMPLE_PHOTO, "jpg");
    model.createLayer("exported");
    model.changeCurrent("exported");
    model.loadPhoto(exported);

    assertEquals(new StatusResult(Status.SUCCESS, "try", "jpg").getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, "try", "jpg").getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, "try", "jpg").getThird(),
        result.getThird());
  }

  @Test
  public void testExportPNG() {
    model.createLayer("hello");
    model.changeCurrent("hello");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(100, 100, 100);
    pixels[0][1] = new PixelImpl(0, 0, 0);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    list = new ArrayList<>(Arrays.asList("export", "try.png"));
    StatusResult result = exportCommand.run(list);

    IPhoto exported = ExtendedImageUtil.readPhoto("try",
        PhotoType.SIMPLE_PHOTO, "png");
    model.createLayer("exported");
    model.changeCurrent("exported");
    model.loadPhoto(exported);

    IPhoto export = model.getPhoto("exported");
    IPhoto original = model.getPhoto("hello");

    assertTrue(export.equals(original));
    assertEquals(new StatusResult(Status.SUCCESS, "try", "png").getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, "try", "png").getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, "try", "png").getThird(),
        result.getThird());
  }

  @Test
  public void testExportPPM() {
    model.createLayer("hello");
    model.changeCurrent("hello");
    IPixel[][] pixels = new IPixel[1][2];
    pixels[0][0] = new PixelImpl(100, 100, 100);
    pixels[0][1] = new PixelImpl(0, 0, 0);
    IPhoto photo = new PhotoImpl(pixels, 255);
    model.loadPhoto(photo);
    list = new ArrayList<>(Arrays.asList("export", "try.ppm"));
    StatusResult result = exportCommand.run(list);

    IPhoto exported = ExtendedImageUtil.readPhoto("try",
        PhotoType.SIMPLE_PHOTO, "ppm");
    model.createLayer("exported");
    model.changeCurrent("exported");
    model.loadPhoto(exported);

    IPhoto export = model.getPhoto("exported");
    IPhoto original = model.getPhoto("hello");

    assertTrue(export.equals(original));
    assertEquals(new StatusResult(Status.SUCCESS, "try", "ppm").getFirst(),
        result.getFirst());
    assertEquals(new StatusResult(Status.SUCCESS, "try", "ppm").getSecond(),
        result.getSecond());
    assertEquals(new StatusResult(Status.SUCCESS, "try", "ppm").getThird(),
        result.getThird());
  }
}
