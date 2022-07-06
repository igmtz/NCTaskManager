package mx.tc.j2se.tasks;

abstract class AbstractTaskList {

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
    public AbstractTaskList incoming(int from, int to) {
        if(from > to ){
            throw new IllegalArgumentException("The from value must be less than to");
        } else if (from < 0) {
            throw new IllegalArgumentException("The input values cannot be negative");
        }

        AbstractTaskList tasksOnRange = new LinkedTaskListImpl();

        for(int i = 0; i < size(); i++){
            if(getTask(i).isRepeated() && getTask(i).isActive()){
                int add = getTask(i).getStartTime();
                while (add < from) {
                    add += getTask(i).getRepeatInterval();
                }
                if (add < to && add < getTask(i).getEndTime()) {
                    tasksOnRange.add(getTask(i));
                }
            } else if(!getTask(i).isRepeated() && getTask(i).isActive() && getTask(i).getTime() >= from && getTask(i).getTime() <= to){
                tasksOnRange.add(getTask(i));
            }
        }
            return tasksOnRange;
    }
}
