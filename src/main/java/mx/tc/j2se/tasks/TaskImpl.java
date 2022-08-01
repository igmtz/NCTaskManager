package mx.tc.j2se.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The TaskImpl class represents the template of the main objects
 * of the application which will be the task. Tasks contain text
 * strings representing the details of the activity, a specific
 * time to be performed and an active status. Tasks can be created
 * in a repetitive and non-repetitive way.
 *
 * @version 1.10 22 June 2022
 * @author Gonzalo Martínez
 */
public class TaskImpl implements Task {

    private String title;
    private LocalDateTime time;
    private boolean isActive;
    private LocalDateTime start;
    private LocalDateTime end;
    private long interval;
    private boolean isRepeated;

    /**
     * Empty constructor of task
     */
    public TaskImpl() {
    }

    /**
     * Creates a non-repetitive inactive task that is
     * executed at a specified time with a given name.
     * @param title description of the task
     * @param time execution time
     */
    public TaskImpl(String title, LocalDateTime time) {
        if( title == null || time == null ){
            throw new IllegalArgumentException("Title or time is null");
        }
        this.title = title;
        this.time = time;
        this.isActive = false;
        this.isRepeated = false;
    }

    /**
     * Creates a repetitive task and inactive task
     * to run within the specified time range with
     * a repetition interval and a given name.
     * Start time must be less than end time.
     * @param title description of the task
     * @param start start time
     * @param end end time
     * @param interval interval time
     * @throws IllegalArgumentException when end value is less than start
     * or the interval is less or equals zero.
     */
    public TaskImpl(String title, LocalDateTime start, LocalDateTime end, int interval){
        if( title == null ){
            throw new IllegalArgumentException("Title cannot be null");
        } else if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be positive and more than zero");
        } else if (start == null || end == null) {
            throw new IllegalArgumentException("Start time or end time is less than zero");
        } else{
            this.title = title;
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.isActive = false;
            this.isRepeated = true;
        }
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Allows the user to reassign the values
     * of variables in a non-repetitive task or to make the task
     * non-repetitive.
     * @param time is the new execution time
     * @throws IllegalArgumentException when the time value is negative
     */
    @Override
    public void setTime(LocalDateTime time) {
        if(time == null ){
            throw new IllegalArgumentException("Time value cannot be negative");
        }
        if(!this.isRepeated){
            this.time = time;
        } else {
            this.isRepeated = false;
            this.time = time;
            this.start = null;
            this.end = null;
            this.interval = 0;
        }
    }

    /**
     * getTime method checks on the execution time of a non-repetitive task.
     * Repetitive tasks return the start value.
     * @return start time of the task
     */
    @Override
    public LocalDateTime getTime() {
        if(!this.isRepeated) {
            return time;
        } else {
            return start;
        }
    }

    @Override
    public void setActive(boolean active) {
        this.isActive = active;
    }
    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * getStartTime method checks on the start time in repetitive tasks.
     * Non-repetitive tasks returns time.
     * @return start time
     */
    @Override
    public LocalDateTime getStartTime() {
        if(this.isRepeated){
            return start;
        } else {
            return time;
        }
    }

    /**
     * getEndTime method checks on the end time in repetitive tasks.
     * Non-repetitive task returns time.
     * @return end time
     */
    @Override
    public LocalDateTime getEndTime() {
        if(this.isRepeated){
            return end;
        } else {
            return time;
        }
    }

    /**
     * getRepeatInterval method checks on the repetition time interval.
     * Non-repetitive tasks returns zero.
     *
     * @return interval time
     */
    @Override
    public long getRepeatInterval() {
        if(this.isRepeated){
            return interval;
        } else {
            return 0;
        }
    }

    /**
     * isRepeated method checks on the repeatability of the
     * task.
     * @return the repetitive condition
     */
    @Override
    public boolean isRepeated() {
        return isRepeated;
    }

    /**
     * Allows the user to reassign the values
     * of variables in a repetitive task or to make the task
     * repetitive.
     *
     * @param start is the start time of the task
     * @param end is the end time of the task
     * @param interval is the time repetition interval
     * @throws IllegalArgumentException when the end time is less than
     * start time and the interval is negative or equals to zero.
     */
    @Override
    public void setTime(LocalDateTime start, LocalDateTime end, long interval) {
        if( start == null ){
            throw new IllegalArgumentException("The start value must be less than end");
        } else if (interval <= 0) {
            throw new IllegalArgumentException("The interval must be positive and more than zero");
        }

        if(this.isRepeated){
            this.start = start;
        } else {
            this.isRepeated = true;
            this.start = start;
            this.time = null;
        }
        this.end = end;
        this.interval = interval;


    }

    /**
     * nextTimeAfter method checks task activeness and repeatability
     * in order to return the upcoming time of the current task. If
     * the task is non-repetitive, it only checks the execution time.
     * If it is repetitive, it checks the execution time and the
     * intervals defined in relation to the current time. If the current
     * time equals to execution time returns -1. Inactive tasks returns
     * -1.
     *
     * @param current represents the actual time
     * @return upcoming time
     * @throws IllegalArgumentException when the current time is negative
     */
    @Override
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if (current == null){
            throw new IllegalArgumentException("The current time cannot be nagative");
        }
        if(this.isActive && !this.isRepeated && current.isBefore(this.time)){
            return time;
        } else if (this.isActive && this.isRepeated) {
            LocalDateTime prevTime = this.start;
            while(current.isAfter(prevTime)) {
                prevTime = prevTime.plusMinutes(this.interval);
            }
            if (prevTime.isAfter(this.end)) {
                return LocalDateTime.MIN;
            } else {
                return prevTime;
            }
        } else {
            return LocalDateTime.MIN;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskImpl)) return false;
        Task task = (Task) o;
        return getTime() == task.getTime()
                && this.isActive == task.isActive()
                && this.start == task.getStartTime()
                && this.end == task.getEndTime()
                && this.interval == task.getRepeatInterval()
                && this.isRepeated == task.isRepeated()
                && Objects.equals(this.title, task.getTitle())
                && this.time == task.getTime();}

    @Override
    public int hashCode() {
        return Objects.hash(this.title, this.time, this.isActive, this.start, this.end, this.interval, this.isRepeated);
    }

    @Override
    public String toString() {
        if (isRepeated()) {
            return this.title + " is a repetitive task with the next properties: " +
                    "isActive=" + this.isActive +
                    ", start=" + this.start +
                    ", end=" + this.end +
                    ", interval=" + this.interval;
        } else {
            return this.title + " is a non-repetitive task with the next properties: " +
                    "isActive=" + this.isActive +
                    ", time=" + this.time;
        }
    }

    @Override
    public Task clone() throws CloneNotSupportedException {
        return (Task) super.clone();
    }

}