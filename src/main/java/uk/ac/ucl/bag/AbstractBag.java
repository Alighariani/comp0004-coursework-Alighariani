package uk.ac.ucl.bag;

/**
 * This class implements methods common to all concrete bag implementations
 * but does not represent a complete bag implementation.<br />
 * <p>
 * New bag objects are created using a BagFactory, which can be configured in the application
 * setup to select which bag implementation is to be used.
 */

import java.util.Iterator;

public abstract class AbstractBag<T extends Comparable> implements Bag<T> {
  public Bag<T> createMergedAllOccurrences(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this) {
      result.addWithOccurrences(value, this.countOf(value));
    }
    for (T value : b) {
      result.addWithOccurrences(value, b.countOf(value));
    }
    return result;
  }

  public Bag<T> createMergedAllUnique(Bag<T> b) throws BagException {
    Bag<T> result = BagFactory.getInstance().getBag();
    for (T value : this) {
      if (!result.contains(value)) result.add(value);
    }
    for (T value : b) {
      if (!result.contains(value)) result.add(value);
    }
    return result;
  }

  @Override
  public String toString() {
    String string = "[ ";
    Iterator<T> valueIterator = iterator();
    while (valueIterator.hasNext()) {
      T value = valueIterator.next();
      int count = countOf(value);
      string += value.toString() + ":" + count;
      if (valueIterator.hasNext()) {
        string += ',';
      }
    }
    string += " ]";
    return string;
  }

  public void removeAllCopies()
  {
    Iterator<T> valueIterator = iterator();
    while (valueIterator.hasNext()) {
      T value = valueIterator.next();
      int count = countOf(value);
      while( count > 1)
      {
        remove(value);
        count --;
      }
    }
  }

}
