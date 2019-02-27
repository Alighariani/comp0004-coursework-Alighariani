package uk.ac.ucl.bag;

import java.util.HashMap;
import java.util.Iterator;

public class MapBag<T extends Comparable> extends AbstractBag<T>
{
    private int maxSize;
    private HashMap<T, Integer> hstructure;


    public MapBag() throws BagException
    {
        this(MAX_SIZE);
    }

    public MapBag(int maxSize) throws BagException
    {
        if (maxSize > MAX_SIZE)
        {
            throw new BagException("Attempting to create a Bag with size greater than maximum");
        }
        if (maxSize < 1)
        {
            throw new BagException("Attempting to create a Bag with size less than 1");
        }
        this.maxSize = maxSize;
        this.hstructure = new HashMap<>();
    }

    @Override
    public void add(T value) throws BagException
    {

        addWithOccurrences(value, 1);
    }

    @Override
    public void addWithOccurrences(T value, int occurrences) throws BagException {
        if (hstructure.containsKey(value))
        {
            hstructure.put(value, hstructure.get(value)+occurrences);

        }
        else if (hstructure.size() < maxSize)
        {
            hstructure.put(value, occurrences);
        }
        else{
            throw new BagException("Bag is full");
        }

    }

    @Override
    public boolean contains(T value) {
        return hstructure.containsKey(value);
    }

    @Override
    public int countOf(T value) {
        return hstructure.get(value);
    }

    @Override
    public void remove(T value) {
        if (countOf(value) == 1) {
            hstructure.remove(value);
        }
        else if (countOf(value) > 1)
        {
            hstructure.put(value, hstructure.get(value)-1);
        }
    }

    @Override
    public int size() {
        return hstructure.size();
    }

    @Override
    public boolean isEmpty() {
        return hstructure.isEmpty();
    }



    @Override
    public Iterator<T> allOccurrencesIterator() {
        return new MapIterator();
    }

    private class MapIterator implements Iterator<T>
    {
        private int count = 0;
        private Iterator<T> keyIterator;
        private T currentElement;

        public MapIterator() {
            this.keyIterator = iterator();
        }

        public boolean hasNext()
        {
            if(currentElement == null) return keyIterator.hasNext();
            if (count < hstructure.get(currentElement)) return true;
            if (count == hstructure.get(currentElement)) return keyIterator.hasNext();

            return false;
        }

        public T next()
        {
            if (currentElement == null || count == hstructure.get(currentElement)){
                currentElement = keyIterator.next();
                count = 1;
                return currentElement;
            }


            else{
                count++;
                return currentElement;
            }

        }
    }

    @Override
    public Iterator<T> iterator() {
        return hstructure.keySet().iterator();
    }



}
