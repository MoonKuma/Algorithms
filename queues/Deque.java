/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 2019-06-22
 *  Description: Deque is implemented through a linked list as according to the requirements
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    public Deque() {
        // construct an empty deque
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    class Node {
        private Item item;
        private Node befroe;
        private Node next;

        public Node(Item item, Node before, Node next) {
            this.item = item;
            this.befroe = before;
            this.next = next;
        }
    }

    public boolean isEmpty() {
        // is the deque empty?
        return this.size == 0;
    }

    public int size() {
        // return the number of items on the deque
        return this.size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        Node newNode = new Node(item, null, this.first);
        if (this.first != null) {
            this.first.befroe = newNode;
        }
        this.first = newNode;
        if (isEmpty()) {
            this.last = newNode;
        }
        this.size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        Node newNode = new Node(item, this.last, null);
        if (this.last != null) {
            this.last.next = newNode;
        }
        this.last = newNode;
        if (isEmpty()) {
            this.first = newNode;
        }
        this.size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty!");
        }
        Item firstItem = this.first.item;
        this.first = this.first.next;
        if (this.first != null) {
            this.first.befroe = null;
        }
        this.size--;
        if (isEmpty()) {
            this.last = null;
        }
        return firstItem;
    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty()) {
            throw new NoSuchElementException("Quene is empty!");
        }
        Item lastItem = this.last.item;
        this.last = this.last.befroe;
        if (this.last != null) {
            this.last.next = null;
        }
        this.size--;
        if (isEmpty()) {
            this.first = null;
        }
        return lastItem;
    }

    public static void main(String[] args) {
        // test
        String[] test = "1,2,3,4,5,6,7,8,9,0".split(",");
        Deque<String> deque = new Deque<>();
        for (String i:test) {
            deque.addFirst(i);
        }
        for (String i:test) {
            deque.addLast(i);
        }
        for (int i = 0; i < test.length; i++) {
            System.out.println(deque.removeFirst());
            System.out.println(deque.removeLast());
        }
    }
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node current = first;
            @Override
            public boolean hasNext() { return current != null; }
            @Override
            public Item next() {
                if (!hasNext()) { throw new NoSuchElementException("No element inside itertor!"); }
                Item item = current.item;
                current = current.next;
                return item;
            }
        };
    }
}
