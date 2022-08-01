package mx.tc.j2se.tasks;

import java.time.LocalDateTime;

public interface Task {
    String getTitle();
    void setTitle(String title);
    boolean isActive();
    void setActive(boolean active);
    LocalDateTime getTime();
    void setTime(LocalDateTime time);
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    long getRepeatInterval();
    void setTime(LocalDateTime start, LocalDateTime end, long interval);
    boolean isRepeated();
    LocalDateTime nextTimeAfter(LocalDateTime current);
    boolean equals(Object o);
    int hashCode();
    String toString();
    Task clone() throws CloneNotSupportedException;
}
