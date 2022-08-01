package mx.tc.j2se.tasks;

import java.util.*;
import java.util.stream.Stream;

/**
 * LinkedTaskListImpl class represents the creation of the
 * array tasks linked list with all its methods for the manipulation
 * of the tasks objects.
 *
 * @author Gonzalo Martínez
 * @version 1.10 30 June 2022
 */

public class LinkedTaskListImpl extends AbstractTaskList {
    Node head;

    /**
     * Empty constructor of the LinkedTaskListImpl class
     */
    public LinkedTaskListImpl() {
    }

    static class Node {
        Task task;
        Node next;

        Node(Task task) {
            this.task = task;
            this.next = null;
        }
    }

    /**
     * add is a method that adds a specific task to the tasks list.
     * The same task can be added to the list several times.
     *
     * @param task is the task to be added
     * @throws IllegalArgumentException when the task added is null
     */

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("The task cannot be null");
        }
        if (head == null) {
            head = new Node(task);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node(task);
        }
    }

    /**
     * It is a method that deletes a specific task from the tasks list.
     * If the list contains the same task several times, any of them is removed.
     *
     * @param task is the task to be deleted
     * @return a boolean value which indicates if the task is in the list.
     * @throws NullPointerException     when the list is empty
     * @throws IllegalArgumentException when the task parameter is null
     */
    @Override
    public boolean remove(Task task) throws NullPointerException {
        if (task == null) {
            throw new IllegalArgumentException("The task cannot be null");
        }

        int count = 0;
        Node countNode = head;

        Node current = head;
        Node prev = null;

        while (countNode != null) {
            if (countNode.task == task) {
                count++;
            }
            countNode = countNode.next;
        }
        System.out.println(count);

        if (head == null) {
            throw new NullPointerException("The linked list you are trying to access is empty");
        } else if (count == 1) {
            if (task == head.task && current.next != null) {
                head = current.next;
            } else {
                while (current.next != null && current.task != task) {
                    prev = current;
                    current = current.next;
                }
                if (prev != null) {
                    prev.next = current.next;
                }
            }
        } else return count != 0;
        return true;
    }

    /**
     * It is a method that specifies the number of tasks that are in the list.
     *
     * @return the size of the list
     */
    @Override
    public int size() {
        int count = 0;
        if (head == null) {
            return 0;
        } else {
            Node current = head;

            while (current != null) {
                current = current.next;
                count++;
            }
            return count;
        }
    }

    /**
     * It is a method that gets the task that is in a specific
     * position in the list.
     *
     * @param index is the specified index of the required task to obtain
     * @return the task in the specified index
     * @throws IndexOutOfBoundsException when the index is
     *                                   negative or the index is out of range
     * @throws IllegalArgumentException  when the list is empty
     */
    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (head == null) {
            throw new IllegalArgumentException("The linked list you are trying to access is empty");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("The index must be positive");
        } else {
            int count = 0;
            Node current = head;
            while (count != index) {
                if (current.next == null) {
                    throw new IndexOutOfBoundsException("The node you are trying to access is not in list");
                } else {
                    current = current.next;
                    count++;
                }
            }
            return current.task;
        }
    }

    /**
     * Allows to iterate on all elements in the list
     * @return iterator
     */
    @Override
    public Iterator<Task> iterator() {

        return new LinkedListIterator(this);
    }

    public class LinkedListIterator implements Iterator<Task> {
        private Node current;
        private Node last;

        public LinkedListIterator(LinkedTaskListImpl tasks) {
            this.current = tasks.head;
            this.last = null;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Task next() {
            if (current == null && head != null) {
                current = head;
                return head.task;
            } else if (current != null) {
                last = current;
                current = current.next;
                return last.task;
            }
            throw new NoSuchElementException();
        }
    }

    /**
     * Compares two objects and defines is they are equal
     * @param o is the object of comparison
     * @return boolean value
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedTaskListImpl)) return false;
        LinkedTaskListImpl tasks = (LinkedTaskListImpl) o;
        boolean isEqual = false;
        int thisNode = 0;
        if (this.size() != tasks.size()) {
            return false;
        } else {
            Node current = head;
            while (current != null) {
                if (current.task.equals(tasks.getTask(thisNode))) {
                    isEqual = true;
                } else {
                    isEqual = false;
                    break;
                }
                thisNode++;
                current = current.next;
            }
        }

        return isEqual;
    }

    /**
     * Generates a unique value that represents the Object
     * @return a generated unique value
     */
    @Override
    public int hashCode() {
        int hashCode = 1;

        Node current = head;
        while (current != null) {
            hashCode = 31 * hashCode + (current == null ? 0 : current.hashCode());
            current = current.next;
        }
        return hashCode;

    }

    /**
     * Generates a string with all the object information
     * @return string
     */
    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        Node current = head;
        while (current != null) {
            message.append(current);
            current = current.next;
        }
        return "This array has the following tasks: " + message;
    }

    /**
     * clone method allows to generate an array
     * equal the given one
     * @return cloned list
     */
    @Override
    public LinkedTaskListImpl clone() {
        try {
            return (LinkedTaskListImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    /**
     * Creates a stream that allows to iterate
     * collections
     *
     * @return a stream object
     */
    @Override
    public Stream<Task> getStream() {
        try {
            List<Task> tasks = new LinkedList<>();
            for (int i = 0; i < size(); i++) {
                tasks.add(getTask(i));
            }
            if (tasks.isEmpty()) {
                throw new IllegalStateException("The list is empty");
            } else {
                return tasks.stream();
            }
        } catch (IllegalStateException e) {
            System.out.println("The list is empty");
            throw new IllegalStateException("The list is empty");
        }
    }

}
