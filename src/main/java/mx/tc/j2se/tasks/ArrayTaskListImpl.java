package mx.tc.j2se.tasks;

/**
 * ArrayTaskListImpl class represents the creation of the
 * array tasks list with all its methods for the manipulation
 * of the tasks objects.
 *
 * @version 1.10 28 June 2022
 * @author Gonzalo Martínez
 */

public class ArrayTaskListImpl implements ArrayTaskList {

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
     */
    @Override
    public void add(Task task) {
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
     */

    @Override
    public boolean remove(Task task) {
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
     */
    @Override
    public Task getTask(int index) {
        return tasks[index];
    }

    /**
     * It is a method that allows to obtain
     * the active tasks to be executed in a certain time range.
     * The from time must be less than to.
     *
     * @param from is the start of the interval
     * @param to   is the end of the interval
     * @return the list of tasks
     */
    @Override
    public ArrayTaskList incoming(int from, int to) {
        ArrayTaskList tasksOnRange = new ArrayTaskListImpl();

        for (Task i : this.tasks) {
            if (i.isRepeated() && i.isActive()) {
                int add = i.getStartTime();
                while (add < from) {
                    add += i.getRepeatInterval();
                }
                if (add < to && add < i.getEndTime()) {
                    tasksOnRange.add(i);
                }

            } else if (!i.isRepeated() && i.isActive() && i.getTime() >= from && i.getTime() <= to) {
                tasksOnRange.add(i);
            }
        }
        return tasksOnRange;
    }

}
