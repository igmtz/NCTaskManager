package mx.tc.j2se.tasks;

import java.util.*;
import java.util.stream.Stream;

/**
 * ArrayTaskListImpl class represents the creation of the
 * array tasks list with all its methods for the manipulation
 * of the tasks objects.
 *
 * @version 1.10 28 June 2022
 * @author Gonzalo Martínez
 */

public class ArrayTaskListImpl extends AbstractTaskList {

    /**
     * tasks is the initial variable used on the ArrayTaskListImpl class
     */
    private Task[] tasks = {};

    /**
     * Empty constructor of the ArrayTaskListImpl class
     */
    public ArrayTaskListImpl() {
    }

    /**
     *add is a method that adds a specific task to the tasks list.
     * The same task can be added to the list several times.
     * @param task is the task to be added
     * @throws  IllegalArgumentException when the task parameter is null
     */
    @Override
    public void add(Task task) {
        if( task == null ){
            throw new IllegalArgumentException("The task cannot be null");
        }
        Task[] tasksAdded = new Task[this.tasks.length + 1];

        System.arraycopy(this.tasks, 0, tasksAdded, 0, this.tasks.length);
        tasksAdded[tasksAdded.length - 1] = task;

        this.tasks = tasksAdded;

    }

    /**
     * It is a method that deletes a specific task from the tasks list.
     * If the list contains the same task several times, any of them is removed.
     * @param task is the task to be deleted
     * @return a boolean value which indicates if the task is in the list.
     * @throws  IllegalArgumentException when the task parameter is null
     * or the array list is empty
     */

    @Override
    public boolean remove(Task task) {
        if( task == null ){
            throw new IllegalArgumentException("The task cannot be null");
        } else if (this.tasks.length == 0) {
            throw new IllegalArgumentException("The array list you are trying to access is empty");
        }

        boolean isOnList = false;
        int aux = 0;
        int taskNumber = 0;

        for (Task i : this.tasks) {
            if (i == task) {
                taskNumber++;
                isOnList = true;
            }
        }

        if (taskNumber == 1) {
            Task[] tasksDeleted = new Task[this.tasks.length - 1];
            for (Task j : this.tasks) {
                if (j != task) {
                    tasksDeleted[aux] = j;
                    aux++;
                }
            }
            this.tasks = tasksDeleted;
        }
        return isOnList;
    }

    /**
     * It is a method that specifies the number of tasks that are in the list.
     * @return the size of the list
     */
    @Override
    public int size() {
        return tasks.length;
    }

    /**
     * It is a method that gets the task that is in a specific
     * position in the list.
     * @param index is the specified index of the required task to obtain
     * @return the task in the specified index
     * @throws IllegalArgumentException when the array list is empty
     * @throws IndexOutOfBoundsException when the index is negative or is out of range
     */
    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException{
        if ( this.tasks.length == 0){
            throw new IllegalArgumentException("The array list you are trying to access is empty");
        } else {
            try {
                return tasks[index];
            } catch (IndexOutOfBoundsException e){
                throw new IndexOutOfBoundsException("The index is negative or out of range");
            }
        }
    }

    /**
     * Allows to iterate on all elements in the list
     * @return iterator
     */
    @Override
    public Iterator<Task> iterator() {
        return new ArrayListIterator(this);
    }

    public static class ArrayListIterator implements Iterator<Task> {
        private final ArrayTaskListImpl arrayTaskList;
        private int index;
        private int last;

        @Override
        public boolean hasNext() {
            return arrayTaskList.size() > index;
        }

        @Override
        public Task next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            last = index;
            return arrayTaskList.getTask(index++);
        }

        public ArrayListIterator(ArrayTaskListImpl tasks) {
            this.arrayTaskList = tasks;
            this.index = 0;
            this.last = -1;
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
        if (!(o instanceof ArrayTaskListImpl)) return false;
        ArrayTaskListImpl objectTasks = (ArrayTaskListImpl) o;
        boolean isEqual = false;
        if (this.size() != objectTasks.size()) {
            return false;
        } else {
            for (int i = 0; i < objectTasks.size(); i++) {
                if (objectTasks.getTask(i).equals(this.getTask(i))) {
                    isEqual = true;
                } else {
                    isEqual = false;
                    break;
                }
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
        for (Task e : this.tasks)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;

    }

    /**
     * Generates a string with all the object information
     * @return string
     */
    @Override
    public String toString() {
        return "This array has a size of " +
                this.size() + "with the following tasks: " +
                Arrays.toString(tasks);
    }

    /**
     * clone method allows to generate an array
     * equal the given one
     * @return cloned list
     */
    @Override
    public ArrayTaskListImpl clone() {
        try {
            return (ArrayTaskListImpl) super.clone();
        } catch (CloneNotSupportedException e){
            return null;
        }
    }

    /**
     * Creates a stream that allows to iterate
     * collections
     * @return a stream object
     */
    @Override
    public Stream<Task> getStream() throws IllegalStateException {
        try {
            List<Task> tasks = new ArrayList<>();
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
