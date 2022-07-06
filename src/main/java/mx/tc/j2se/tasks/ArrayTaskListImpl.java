package mx.tc.j2se.tasks;

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

}
