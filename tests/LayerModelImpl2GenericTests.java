import model.week1.IModelImage;
import model.week3.LayerModelImpl2;

/**
 * Tests functionality of LayerModelImpl2.
 */
public class LayerModelImpl2GenericTests extends ModelImplTests {

  @Override
  public IModelImage constructModel() {
    return new LayerModelImpl2();
  }
}
