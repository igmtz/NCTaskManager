package mx.tc.j2se.tasks;

public class TaskListFactory {
    static AbstractTaskList createTaskList(ListTypes.types type){
        AbstractTaskList list = null;
        switch (type){
            case ARRAY:
                list = new ArrayTaskListImpl();
                break;

            case LINKED:
                list = new LinkedTaskListImpl();
                break;

            default:
                break;
        }
        return list;
    }
}
