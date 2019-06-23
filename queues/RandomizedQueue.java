/* *****************************************************************************
 *  Name: MoonKuma
 *  Date: 2019-06-22
 *  Description: RandomizedQueue is implemented through a array
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size; // number of valid elements
    private Item[] items; // container


    public RandomizedQueue() {
        // construct an empty randomized queue
        this.size = 0;
        this.items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return this.size == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return this.size;
    }

    public void enqueue(Item item) {
        // add the item
        if (item==null) { throw new IllegalArgumentException("Intering null object is not permitted!");}
        if (this.size == items.length) {
            resize(2*items.length);
        }
        items[this.size++] = item;
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty()) throw new NoSuchElementException();
        int target = StdRandom.uniform(this.size);
        swap(target, this.size - 1);
        Item item = items[--this.size];
        items[this.size] = null; // to avoid loitering
        if (this.size > 0 && this.size == this.items.length/4) {
            resize(this.items.length/4);
        }
        return item;
    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (isEmpty()) throw new NoSuchElementException();
        int target = StdRandom.uniform(this.size);
        return items[target];
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private int n = size;
            private int[] orders; // this is to prevent the paralled calling

            @Override
            public boolean hasNext() {
                return n > 0;
            }

            @Override
            public Item next() {
                if (!hasNext()) { throw new NoSuchElementException("No element inside itertor!"); }
                if (orders == null) {
                    orders = new int[n];
                    for (int i=0; i<n; i++) {
                        orders[i] = i;
                    }
                    StdRandom.shuffle(orders);
                }
                int target = StdRandom.uniform(n);
                return items[orders[--n]];
            }
        };
    }

    private void resize(int newSize) {
        Item[] items = (Item[]) new Object[newSize];
        for (int i = 0; i < this.size; i++) {
            items[i] = this.items[i];
        }
        this.items = items;
    }

    // swap the one ready to be removed to the last of the list;
    // this helps to make the list not poriferous
    private void swap(int i, int j) {
        Item tmp = this.items[i];
        this.items[i] = this.items[j];
        this.items[j] = tmp;
    }

    public static void main(String[] args) {
        // test
        String[] test = "1,2,3,4,5,6,7,8,9,0".split(",");
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        for (String i:test) {
            randomizedQueue.enqueue(i);
        }
        System.out.println("Testing iterator independent");
        for (int i = 0; i < 3; i++) {
            System.out.println("Testing iterator #"+i);
            Iterator<String> testIterator = randomizedQueue.iterator();
            while (testIterator.hasNext()) {
                System.out.print(testIterator.next());
            }
            System.out.println();
        }
        System.out.println("Testing iterator paralled");
        for (int i = 0; i < 2; i++) {
            System.out.println("Testing iterator #"+i);
            Iterator<String> testIterator = randomizedQueue.iterator();
            Iterator<String> testIterator2 = randomizedQueue.iterator();
            while (testIterator.hasNext() && testIterator2.hasNext()) {
                System.out.print(testIterator.next());
                System.out.println("---"+ testIterator2.next());
            }
            System.out.println();
        }
        System.out.println("Testing dequeue");
        for (int i = 0; i < test.length; i++) {
            System.out.print(randomizedQueue.dequeue());
        }
    }
}
