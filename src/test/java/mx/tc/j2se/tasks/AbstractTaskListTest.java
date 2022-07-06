package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractTaskListTest {

    @Test
    void incoming() {
        TaskImpl task = new TaskImpl("Test1", 100);
        task.setActive(true);

        TaskImpl task2 = new TaskImpl("Test2", 100);

        TaskImpl task3 = new TaskImpl("Test3", 100, 200, 50);
        task3.setActive(true);

        TaskImpl task4 = new TaskImpl("Test4", 100, 300, 50);
        task4.setActive(true);

        TaskImpl task5 = new TaskImpl("Test5", 100);
        task5.setActive(true);

        AbstractTaskList taskList = new ArrayTaskListImpl();
        taskList.add(task);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);

        taskList.incoming(50, 300);

        assertEquals(4,taskList.incoming(100, 300).size());
    }
}