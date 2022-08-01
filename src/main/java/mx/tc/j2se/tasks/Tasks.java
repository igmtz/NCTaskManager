package mx.tc.j2se.tasks;

import java.time.LocalDateTime;
import java.util.*;

public class Tasks {

    /**
     * It is a method that allows to obtain
     * the active tasks to be executed in a certain time range.
     * @param tasks is the iterator of the collection
     * @param start is the start of the interval
     * @param end is the end of the interval
     * @return list with the elements on range
     */
    public static Iterator<Task> incoming(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end) {

        ArrayList<Task> tasksOnRange = new ArrayList<>();

        while (tasks.hasNext()) {
            Task task = tasks.next();
            if (task.isActive() && task.nextTimeAfter(start).isAfter(start) && task.nextTimeAfter(start).isBefore(end)) {
                tasksOnRange.add(task);
            }
        }

        return tasksOnRange.iterator();
    }

    /**
     * calendar method creates a sorted map of the tasks
     * to be executed in a certain time range
     * @param tasks is the iterator of the collection
     * @param start is the start of the interval
     * @param end is the end of the interval
     * @return sorted map of the tasks on range
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterator<Task> tasks, LocalDateTime start, LocalDateTime end) {

        SortedMap<LocalDateTime, Set<Task>> map = new TreeMap<>();
        Iterator<Task> tasksOnRange = incoming(tasks, start, end);

        while (tasksOnRange.hasNext()) {
            Task task = tasksOnRange.next();
            Set <Task> set = new HashSet<>();
            if(task.isRepeated()){

                LocalDateTime myDate = task.nextTimeAfter(start);
                while(!myDate.isAfter(task.getEndTime()) && !myDate.isAfter(end)){
                    if(map.containsKey(myDate)){
                        map.get(myDate).add(task);
                    }
                    else{
                        map.put(myDate, set);
                        map.get(myDate).add(task);
                    }
                    myDate = myDate.plusHours(task.getRepeatInterval());
                }
            }
            else{
                if(map.containsKey(task.getTime())){
                    map.get(task.getTime()).add(task);
                }
                else{
                    map.put(task.getTime(), set);
                    map.get(task.getTime()).add(task);
                }
            }
        }
        return map;
    }
}
