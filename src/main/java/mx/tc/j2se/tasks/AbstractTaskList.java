package mx.tc.j2se.tasks;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

abstract class AbstractTaskList implements Iterable<Task>{

    abstract void add(Task task);
    abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index) throws IndexOutOfBoundsException;

    /**
     * It is a method that allows to obtain
     * the active tasks to be executed in a certain time range.
     *
     * @param from is the start of the interval
     * @param to   is the end of the interval
     * @return the list of tasks
     * @throws IllegalArgumentException when the from value is greater
     * than to or the input is negative.
     */
    public AbstractTaskList incoming(int from, int to) throws NoSuchMethodException {

        AbstractTaskList tasksOnRange = new LinkedTaskListImpl();

        if (this.size() == 0){
            throw new IllegalStateException();
        } else {
            AbstractTaskList list = getClass().getDeclaredConstructor().newInstance();
            this.getStream().filter(Objects::nonNull).filter(i -> {
                if (i.nextTimeAfter(from)!=null)
                    return Objects.requireNonNull(i.nextTimeAfter(from)).isBefore(to) || Objects.requireNonNull(i.nextTimeAfter(from)).isEqual(to);
                return false;
            }).forEach(list::add);
            return list;
        }
        return tasksOnRange;
    }

    /**
     * Compares two objects and defines is they are equal
     * @param o is the object of comparison
     * @return boolean value
     */
    public abstract boolean equals (Object o);

    /**
     * Generates a unique value that represents the Object
     * @return a generated unique value
     */
    public abstract int hashCode();

    /**
     * Creates a stream that allows to iterate
     * collections
     * @return a stream object
     */
    public abstract Stream<Task> getStream();
}
