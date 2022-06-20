package mx.tc.j2se.tasks.Main.Practice1.main;

import mx.tc.j2se.tasks.Main.Practice1.main.Task;

public class TaskImpl implements Task {
    // Variables
    private String title;
    private int time;
    private boolean isActive;
    private int start;
    private int end;
    private int interval;
    private boolean isRepeated;

    // Constructors
    public TaskImpl() {
    }

    public TaskImpl(String title, int time) {
        this.title = title;
        this.time = time;
        this.isActive = false;
        this.isRepeated = false;
    }

    public TaskImpl(String title, int start, int end, int interval){
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.isActive = false;
        this.isRepeated = true;
    }

    // Methods
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override // need to fix
    public void setTime(int time) {
        if(!this.isRepeated){
            this.time = time;
        } else {
            this.start = time;
            this.title = title;
            this.isRepeated = false;
        }
    }
    @Override
    public int getTime() {
        if(!this.isRepeated) {
            return time;
        } else {
            return start;
        }
    }

    @Override
    public void setActive(boolean active) { this.isActive = active; }
    @Override
    public boolean isActive() { return isActive; }

    @Override
    public int getStartTime() {
        if(this.isRepeated){
            return start;
        } else {
            return time;
        }
    }

    @Override
    public int getEndTime() {
        if(this.isRepeated){
            return end;
        } else {
            return time;
        }
    }

    @Override
    public int getRepeatInterval() {
        if(this.isRepeated){
            return interval;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isRepeated() {
        return isRepeated;
    }

    @Override // need to fix
    public void setTime(int start, int end, int interval) {
        if(this.isRepeated){
            this.start = start;
            this.end = end;
            this.interval = interval;
        } else {
            this.isRepeated = true;
        }
    }

    @Override
    public int nextTimeAfter(int current) {
        if(this.isActive && !this.isRepeated && current < this.time){
            return time;
        } else if (this.isActive && this.isRepeated) {
            while(this.start < current) {
                start = start + this.interval;
            }
            if (this.start >= this.end) {
                return -1;
            } else {
                return start;
            }
        } else {
            return -1;
        }
    }
}
