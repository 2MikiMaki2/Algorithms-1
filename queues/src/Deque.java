import java.util.Iterator;
import java.util.NoSuchElementException;

// TODO: fix loitering, fix memory issues
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private int size;

    private class Node {
        private Item item;
        private Node next;

        public void setNext(Node next) {
            this.next = next;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        size++;
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            addFirst(item);
        } else {
            Node newLast = new Node();
            newLast.setItem(item);
            Node lastIndex = get(size);
            lastIndex.setNext(newLast);
            size++;
        }

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        size--;
        Item item = first.item;
        first = first.next;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node last = get(size);
        Item item = last.item;
        last = null;
        if (size == 1) {
            first = null;
        } else {
            Node beforeLast = get(size - 1);
            beforeLast.setNext(null);
        }
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    private Node get(int nodeIndex) {
        Node index = first;
        for (int i = 1; i < nodeIndex; i++) {
            if (index.next != null) {
                index = index.next;
            } else {
                return index;
            }
        }
        return index;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Double> deck = new Deque<>();
        deck.addLast(0.1);
        deck.addFirst(0.5);
        deck.addFirst(0.6);
        System.out.println("Size: " + deck.size());
        System.out.println("First linked list check");
        for (Double a : deck) {
            System.out.println(a);
        }
        System.out.println("Second linked list check");
        double num = deck.removeFirst();
        double num2 = deck. removeLast();
        System.out.println("Size: " + deck.size() + ", removed nums: " + num + " " + num2);
        for (Double a : deck) {
            System.out.println(a);
        }

        /*Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.removeLast();
        for (Integer n : deque) {
            System.out.print(n + " ");
        }
        deque.removeLast();
        System.out.println(deque.size());
        deque.addLast(8);
        System.out.println(deque.size());
        deque.addFirst(11);
        System.out.println(deque.size());
        for (Integer n : deque) {
            System.out.print(n + " ");
        }*/

    }
}
