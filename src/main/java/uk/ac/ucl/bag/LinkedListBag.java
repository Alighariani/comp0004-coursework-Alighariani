package uk.ac.ucl.bag;

import java.util.Iterator;

public class LinkedListBag<T extends Comparable> extends AbstractBag<T> {

    private static class Element<E extends Comparable> {
        public E value;
        public int count;
        public Element<E> next;

        public Element(E value, int occurrences, Element<E> next) {
            this.value = value;
            this.count = occurrences;
            this.next = next;
        }
    }

    private int maxSize;
    private Element contents;


    public LinkedListBag() throws BagException
    {
        this(MAX_SIZE);
    }

    public LinkedListBag(int maxSize) throws BagException
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
        this.contents = null;
    }

    @Override
    public void add(T value) throws BagException {
        addWithOccurrences(value, 1);
    }

    @Override
    public void addWithOccurrences(T value, int occurrences) throws BagException {
        if (contents == null)
        {
            contents = new Element(value, occurrences, null);
        }

        Element element = contents;
        Element previous = null;
        int size = 0;
        while (element != null)
        {
            if (element.value.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                element.count++;
                return;
            }
            else
            {
                previous = element;
                element = element.next;
            }
            size++;
        }
        if (size < maxSize)
        {
            previous.next = new Element(value, occurrences, null);
        }
        else
        {
            throw new BagException("Bag is full");
        }
    }

    @Override
    public boolean contains(T value) {
        Element element = contents;

        while (element != null)
        {
            if (element.value.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                return true;
            }
            element = element.next;

        }
        return false;
    }

    @Override
    public int countOf(T value) {
        Element element = contents;
        while (element != null) {
            if (element.value.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                return element.count;
            }
            element = element.next;
        }
        return 0;
    }

    @Override
    public void remove(T value) {
        Element element = contents;
        Element previous = null;
        while (element != null)
        {
            if (element.value.compareTo(value) == 0) // Must use compareTo to compare values.
            {
                if(element.count == 1) {
                    if (previous == null) {
                        contents = element.next;
                    } else {
                        previous.next = element.next;
                    }
                }
                else {
                    element.count--;
                }
                return;
            }
            else
            {
                previous = element;
                element = element.next;
            }
        }
    }

    @Override
    public int size() {
        Element element = contents;
        int size = 0;
        while (element != null) {
            element = element.next;
            size++;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return contents == null;
    }

    private class LinkedListUniqueIterator implements Iterator<T>
    {
        private Element<T> element;

        public LinkedListUniqueIterator() {
            this.element = contents;
        }

        public boolean hasNext()
        {
            return element != null;
        }

        public T next()
        {
            T value = element.value;
            element = element.next;
            return value;
        }
    }

    private class LinkedListIterator implements Iterator<T>
    {
        private int index = 0;
        private int count = 0;
        private Iterator<T> valueIterator;
        private Element<T> currentElement;


        public LinkedListIterator() {
            this.valueIterator = iterator();
            currentElement = contents;
        }

        public boolean hasNext()
        {
            if (currentElement ==  null) return false;
            if (count < currentElement.count) return true;
            if ((count == currentElement.count) && (currentElement.next != null)) return true;

            return false;
        }

        public T next()
        {

            if (count == currentElement.count){
                currentElement = currentElement.next;
                count = 1;
                return currentElement.value;
            }


            else{
                count++;
                return currentElement.value;
            }

        }
    }

    @Override
    public Iterator<T> allOccurrencesIterator() {
        return new LinkedListIterator();
    }



    @Override
    public Iterator<T> iterator() {
        return new LinkedListUniqueIterator();
    }




}
