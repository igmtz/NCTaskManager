package mx.tc.j2se.tasks;

/**
 * LinkedTaskListImpl class represents the creation of the
 * array tasks linked list with all its methods for the manipulation
 * of the tasks objects.
 *
 * @version 1.10 30 June 2022
 * @author Gonzalo Martínez
 */

public class LinkedTaskListImpl implements LinkedTaskList {
    Node head;

    /**
     * Empty constructor of the LinkedTaskListImpl class
     */
    public LinkedTaskListImpl(){
    }

    static class Node{
        Task task;
        Node next;

        Node(Task task){
            this.task = task;
            this.next = null;
        }
    }

    /**
     *add is a method that adds a specific task to the tasks list.
     * The same task can be added to the list several times.
     * @param task is the task to be added
     * @throws IllegalArgumentException when the task added is null
     */

    @Override
    public void add(Task task) {
        if( task == null ){
            throw new IllegalArgumentException("The task cannot be null");
        }
        if (head == null){
            head = new Node(task);
        } else {
            Node current = head;
            while (current.next != null){
                current = current.next;
            }
            current.next = new Node(task);
        }
    }

    /**
     * It is a method that deletes a specific task from the tasks list.
     * If the list contains the same task several times, any of them is removed.
     * @param task is the task to be deleted
     * @return a boolean value which indicates if the task is in the list.
     * @throws NullPointerException when the list is empty
     * @throws IllegalArgumentException when the task parameter is null
     */
    @Override
    public boolean remove(Task task) throws NullPointerException{
        if (task == null){
            throw new IllegalArgumentException("The task cannot be null");
        }

        int count = 0;
        Node countNode = head;

        Node current = head;
        Node prev = null;

        while (countNode != null){
            if (countNode.task == task){
                count++;
            }
            countNode = countNode.next;
        }
        System.out.println(count);

        if(head == null){
            throw new NullPointerException("The linked list you are trying to access is empty");
        } else if (count == 1) {
            if (task == head.task && current.next != null){
                head = current.next;
            } else {
                while (current.next != null && current.task != task){
                    prev = current;
                    current = current.next;
                }
                if (prev != null) {
                    prev.next = current.next;
                }
            }
        } else if (count == 0){
            return  false;
        }
        return true;
    }

    /**
     * It is a method that specifies the number of tasks that are in the list.
     * @return the size of the list
     */
    @Override
    public int size() {
        int count = 0;
        if(head == null){
            return 0;
        } else {
            Node current = head;

            while(current != null){
                current = current.next;
                count++;
            }
            return count;
        }
    }

    /**
     * It is a method that gets the task that is in a specific
     * position in the list.
     * @param index is the specified index of the required task to obtain
     * @return the task in the specified index
     * @throws IndexOutOfBoundsException when the index is
     * negative or the index is out of range
     * @throws IllegalArgumentException when the list is empty
     */
    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException{
        if (head == null) {
            throw new IllegalArgumentException("The linked list you are trying to access is empty");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("The index must be positive");
        } else {
            int count = 0;
            Node current = head;
                while (count != index) {
                    if(current.next == null){
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
     * It is a method that allows to obtain
     * the active tasks to be executed in a certain time range.
     *
     * @param from is the start of the interval
     * @param to   is the end of the interval
     * @return the list of tasks
     * @throws IllegalArgumentException when the from value is greater
     *                                  than to or the input is negative.
     */
    @Override
    public LinkedTaskList incoming(int from, int to) {
        if(from > to ){
            throw new IllegalArgumentException("The from value must be less than to");
        } else if (from < 0){
            throw new IllegalArgumentException("The input values cannot be negative");
        }

        LinkedTaskList taskOnRange = new LinkedTaskListImpl();

        Node current = head;

        while (current != null){
            if (current.task.isRepeated() && current.task.isActive()){
                int add = current.task.getStartTime();
                while (add < from){
                    add += current.task.getRepeatInterval();
                }
                if (add < to && add < current.task.getEndTime()){
                    taskOnRange.add(current.task);
                }
            } else if (!current.task.isRepeated() && current.task.isActive() && current.task.getTime() >= from && current.task.getTime() <= to) {
                taskOnRange.add(current.task);
            }
            current = current.next;
        }
        return taskOnRange;
    }
}
