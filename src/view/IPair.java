package view;

/**
 * Represents a pair of stored values.
 *
 * @param <K> the first type of the pair.
 * @param <T> the second type in the pair.
 */
public interface IPair<K, T> {

  /**
   * Gets the key from the pair.
   *
   * @return the object stored as the key in the pair.
   */
  public K getKey();

  /**
   * Gets the value from the pair.
   *
   * @return the object stored as the value in the pair.
   */
  public T getValue();
}
