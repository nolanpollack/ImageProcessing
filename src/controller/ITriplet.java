package controller;

/**
 * Represents an object containing three other objects.
 *
 * @param <K> the first object type.
 * @param <T> the second object type.
 * @param <V> the third object type.
 */
public interface ITriplet<K, T, V> {

  /**
   * The first object stored in this ITriplet.
   *
   * @return the first object stored.
   */
  public K getFirst();

  /**
   * The second object stored in this ITriplet.
   *
   * @return the second object stored.
   */
  public T getSecond();

  /**
   * The third object stored in this ITriplet.
   *
   * @return the third object stored.
   */
  public V getThird();

}
