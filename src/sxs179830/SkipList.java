/* Starter code for LP2 */

package sxs179830;

import java.io.File;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

// Skeleton for skip list implementation.
public class SkipList<T extends Comparable<? super T>> {

    private static final int PossibleLevels = 33;
    private Entry head, tail;
    private Entry[] last;
    private Random random;
    private int size, maxLevel;

    static class Entry<E extends Comparable<? super E>> {
        E element;
        Entry<E>[] next;
        Entry<E> prev;

        public Entry(E x, int lev) {
            element = x;
            next = new Entry[lev];
            prev = null;
            // add more code if needed
        }

        public E getElement() {
            return element;
        }

        public int compareTo(E o) {
            return (element).compareTo(o);
        }
    }

    // Constructor
    public SkipList() {
        head = new Entry<>(null, PossibleLevels);
        tail = new Entry<>(null, PossibleLevels);
        for(int i = 0; i < PossibleLevels; i++) {
            head.next[i] = tail;
        }
        tail.prev = head;
        random = new Random();
        size = 0;
        maxLevel = 5;
    }

    /**
     * Add x to list. If x already exists, reject it. Returns true if new node is added to list
     * @param x Element to add in the skip list
     * @return true if the element is inserted otherwise false
     * */
    public boolean add(T x) {
        if (contains(x)) return false;
        int lev = chooseLevel();
        Entry newEntry = new Entry<>(x, lev);
        if(this.size > 0) {
            for (int i = 0; i < lev; i++) {
                newEntry.next[i] = last[i].next[i];
                last[i].next[i] = newEntry;
            }
            newEntry.prev = last[0];
            newEntry.next[0].prev = newEntry;
        } else {
            for (int i = 0; i < lev; i++) {
                newEntry.next[i] = tail;
                head.next[i] = newEntry;
            }
            tail.prev = newEntry;
            newEntry.prev = head;
        }
        System.out.println(x + " : "  + lev);
        this.size++;
        printLastArray();
        return true;
    }

    /**
     * Helper method to find the element, and populate last array with the order in which the
     * element is searched
     * @param x Element to search
     */
    public void find(T x) {
        last = new Entry[this.maxLevel];
        if(this.size > 0) {
            Entry temp = this.head;
            for(int i = this.maxLevel-1; i >= 0 && temp != null; i--) {
                while(temp.next[i].getElement() != null && temp.next[i].compareTo(x) < 0) {
                    temp = temp.next[i];
                }
                last[i] = temp;
            }
        }
    }

    // Find smallest element that is greater or equal to x
    public T ceiling(T x) {
        return null;
    }

    /**
     * Checks whether the list contains x or not
     * @param x Element to be searched
     * @return true if element is in the list otherwise returns false
     */
    public boolean contains(T x) {
        find(x);
        return last[0] != null && last[0].next[0] != null && last[0].next[0].getElement() == x;
    }

    /**
     * Return first element of list
     * @return first element of list if skiplist is not empty otherwise null
     */
    public T first() {
        if(this.size == 0) return null;
        else {
            Entry res = head.next[0];
            return (T) res.getElement();
        }
    }

    // Find largest element that is less than or equal to x
    public T floor(T x) {
        return null;
    }

    // Return element at index n of list.  First element is at index 0.
    public T get(int n) {
        return null;
    }

    // O(n) algorithm for get(n)
    public T getLinear(int n) {
        return null;
    }

    // Optional operation: Eligible for EC.
    // O(log n) expected time for get(n). Requires maintenance of spans, as discussed in class.
    public T getLog(int n) {
        return null;
    }

    /**
     * Checks if the list empty?
     * @return true if list empty otherwise return false
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Iterate through the elements of list in sorted order
    public Iterator<T> iterator() {
        return null;
    }

    /**
     * Return the last element of list
     * @return last element of list otherwise null
     */
    public T last() {
        if(size == 0) return null;
        else {
            Entry res = tail.prev;
            return (T) res.getElement();
        }
    }

    // Optional operation: Reorganize the elements of the list into a perfect skip list
    // Not a standard operation in skip lists. Eligible for EC.
    public void rebuild() {

    }

    /**
     * Remove x from list.  Removed element is returned. Return null if x not in list
     * @param x element to remove from the list
     * @return the element if removed otherwise return null
     */
    public T remove(T x) {
        if(!contains(x)) return null;
        Entry elementToRemove = last[0].next[0];
        for(int i = 0; i < elementToRemove.next.length; i++) {
            last[i].next[i] = elementToRemove.next[i];
        }
        elementToRemove.next[0].prev = last[0];
        this.size--;
        return x;
    }

    // Return the number of elements in the list

    /**
     * @return size of the skip list
     */
    public int size() {
        return this.size;
    }

    /***
     * Randomly return the length of level for new element to be inserted
     * @return integer value
     */
    private int chooseLevel() {
        int level = 1 + Integer.numberOfTrailingZeros(random.nextInt());
        if (level > maxLevel) maxLevel = level;
        return level;
    }

    private void printSkiplist() {
        Entry a = head.next[0];
        System.out.print("List (" + this.size + "): ");
        for(int i =0; i < this.size; i++) {
            System.out.print(a.getElement() + "  ");
            a = a.next[0];
        }
        System.out.println();
    }

    private void printLastArray() {
        System.out.print("Last Array: ");
        for(Entry a : this.last) {
            if(a != null)
                System.out.println(a.getElement() + "  ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws Exception {


        SkipList<Integer> sk = new SkipList<>();
        sk.add(1);
        sk.add(7);
        sk.add(9);
        sk.add(3);
        sk.add(13);
        sk.add(11);
        sk.add(15);
        sk.add(5);
        sk.add(21);
        sk.add(29);
        sk.add(39);
        sk.add(31);
        sk.add(17);

//        Scanner sc;
//        if (args.length > 0) {
//            File file = new File(args[0]);
//            sc = new Scanner(file);
//        } else {
//            sc = new Scanner(System.in);
//        }
//        String operation = "";
//        long operand = 0;
//        int modValue = 999983;
//        long result = 0;
//        Long returnValue = null;
//        SkipList<Long> skipList = new SkipList<>();
//        // Initialize the timer
//        Timer timer = new Timer();
//        while (!((operation = sc.next()).equals("End"))) {
//            switch (operation) {
//                case "Add": {
//                    operand = sc.nextLong();
//                    skipList.printSkiplist();
//                    break;
//                }
//                case "Ceiling": {
//                    operand = sc.nextLong();
//                    returnValue = skipList.ceiling(operand);
//                    if (returnValue != null) {
//                        result = (result + returnValue) % modValue;
//                    }
//                    break;
//                }
//                case "First": {
//                    System.out.println(skipList.first());
//                    break;
//                }
//                case "Get": {
//                    int intOperand = sc.nextInt();
//                    returnValue = skipList.get(intOperand);
//                    if (returnValue != null) {
//                        result = (result + returnValue) % modValue;
//                    }
//                    break;
//                }
//                case "Last": {
//                    System.out.println(skipList.last());
//                    break;
//                }
//                case "Floor": {
//                    operand = sc.nextLong();
//                    returnValue = skipList.floor(operand);
//                    if (returnValue != null) {
//                        result = (result + returnValue) % modValue;
//                    }
//                    break;
//                }
//                case "Remove": {
//                    operand = sc.nextLong();
//                    System.out.print(skipList.remove(operand) != null);
//                    skipList.printSkiplist();
//                    break;
//                }
//                case "Contains":{
//                    operand = sc.nextLong();
//                    System.out.print(skipList.contains(operand));
//                    skipList.printSkiplist();
//                    break;
//                }
//                case "Size": {
//                    skipList.printSkiplist();
////                    System.out.println("Size: " + skipList.size());
//                }
//            }
//        }
//
//        // End Time
//        timer.end();
//
//        System.out.println(result);
//        System.out.println(timer);
    }
}