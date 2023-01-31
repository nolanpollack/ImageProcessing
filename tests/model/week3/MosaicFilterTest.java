package model.week3;

import static org.junit.Assert.assertArrayEquals;

import controller.ExtendedImageUtil;
import model.week1.IFilter;
import model.week1.IPhoto;
import model.week1.ImageCreator.PhotoType;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for MosaicFilter.
 */
public class MosaicFilterTest {

  IFilter filter1;
  IFilter filter2;
  IPhoto photo;


  @Before
  public void setUp() {
    filter1 = new MosaicFilter(10, 1);
    filter2 = new MosaicFilter(1000, 10);
    photo = ExtendedImageUtil.readPhoto("res/newyork", PhotoType.SIMPLE_PHOTO, "png");
  }

  @Test
  public void apply() {
    assertArrayEquals(
        ExtendedImageUtil.readPhoto("res/newyork mosaic", PhotoType.SIMPLE_PHOTO, "png")
            .getPixelSequence(), filter1.apply(photo).getPixelSequence());
    assertArrayEquals(
        ExtendedImageUtil.readPhoto("res/newyork mosaic 2", PhotoType.SIMPLE_PHOTO, "png")
            .getPixelSequence(), filter2.apply(photo).getPixelSequence());
  }
}