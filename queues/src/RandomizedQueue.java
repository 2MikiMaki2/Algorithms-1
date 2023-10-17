import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


// TODO: Fix index 2 out of bounds for length 2
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arrayItems;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        arrayItems = (Item[]) new Object[3];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        arrayItems[size++] = item;
        if (size + 1 == arrayItems.length) {
            resize(2 * arrayItems.length);
        }
        int randInd = StdRandom.uniformInt(size);
        Item temp = arrayItems[randInd];
        arrayItems[randInd] = arrayItems[size - 1];
        arrayItems[size - 1] = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item item = arrayItems[size];
        if (size > 0 && size == arrayItems.length / 4) {
            resize(arrayItems.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniformInt(size);
        return arrayItems[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {

        private final Item[] arrayCopy = (Item[]) new Object[arrayItems.length];
        private int currentSize = size;

        public QueueIterator() {
            for (int i = 0; i < size; i++) {
                arrayCopy[i] = arrayItems[i];
            }
        }

        @Override
        public boolean hasNext() {
            return currentSize != 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int randI = StdRandom.uniformInt(currentSize);
            Item item = arrayCopy[randI];
            if (randI != currentSize) {
                Item temp = arrayCopy[currentSize - 1];
                arrayCopy[currentSize - 1] = null;
                arrayCopy[randI] = temp;
                currentSize--;
            }
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = arrayItems[i];
        }
        arrayItems = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        rq.enqueue("E");
        rq.enqueue("F");

        System.out.println("First list check:\n\tSize: " + rq.size());
        for (String s : rq) {
            System.out.println(s);
        }

        System.out.println("Removing: " + rq.dequeue());
        System.out.println("Removing: " + rq.dequeue());
        System.out.println("Sample: " + rq.sample());

        System.out.println("Second list check:\n\tSize: " + rq.size());
        for (String s : rq) {
            System.out.println(s);
        }

    }
}
