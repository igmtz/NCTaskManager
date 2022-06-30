package mx.tc.j2se.tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedTaskListImplTest {

    @Test
    void addTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        LinkedTaskListImpl tasks = new LinkedTaskListImpl();

        tasks.add(task);
        assertEquals(1, tasks.size());

        TaskImpl task1 = new TaskImpl("Test2", 100, 200, 50);
        tasks.add(task1);
        assertEquals(2, tasks.size());

        TaskImpl task2 = new TaskImpl("Test3", 100, 200, 50);
        tasks.add(task2);
        assertEquals(3, tasks.size());

        TaskImpl task3 = new TaskImpl("Test4", 100);
        tasks.add(task2);
        assertEquals(4, tasks.size());

        tasks.add(task2);
        assertEquals(5, tasks.size());

        tasks.add(task);
        assertEquals(6, tasks.size());
    }

    @Test
    void removeTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        TaskImpl task2 = new TaskImpl("Test2", 100);
        TaskImpl task3 = new TaskImpl("Test3", 100);
        TaskImpl task4 = new TaskImpl("Test4", 100);
        TaskImpl task5= new TaskImpl("Test4", 100, 200, 50);

        LinkedTaskListImpl tasks = new LinkedTaskListImpl();
        tasks.add(task);
        tasks.add(task);
        tasks.add(task2);
//        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task5);

//        tasks.remove(task3);
        tasks.remove(task3);
        tasks.remove(task2);
        tasks.remove(task);
        tasks.remove(task5);

        assertEquals(5, tasks.size());
//        assertTrue(tasks.remove(task5));
    }

    @Test
    void sizeTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        TaskImpl task2 = new TaskImpl("Test2", 100);
        TaskImpl task3 = new TaskImpl("Test3", 100, 200, 50);
        TaskImpl task4 = new TaskImpl("Test4", 100, 200, 50);
        TaskImpl task5 = new TaskImpl("Test5", 100);

        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        taskList.add(task);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);
//        taskList.remove(task5);
        assertEquals(4, taskList.size());
    }

    @Test
    void getTaskTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        TaskImpl task2 = new TaskImpl("Test2", 100);
        TaskImpl task3 = new TaskImpl("Test3", 100, 200, 50);
        TaskImpl task4 = new TaskImpl("Test4", 100, 200, 50);
        TaskImpl task5 = new TaskImpl("Test5", 100);

        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        taskList.add(task);
        taskList.add(task3);
        taskList.add(task4);
        taskList.add(task5);

        assertEquals(task5, taskList.getTask(8));
    }

    @Test
    void incomingTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        task.setActive(true);

        TaskImpl task2 = new TaskImpl("Test2", 100);

        TaskImpl task3 = new TaskImpl("Test3", 100, 200, 50);
        task3.setActive(true);

        TaskImpl task4 = new TaskImpl("Test4", 100, 300, 50);
        task4.setActive(true);

        TaskImpl task5 = new TaskImpl("Test5", 100);
        task5.setActive(true);

        LinkedTaskListImpl taskList = new LinkedTaskListImpl();
        taskList.add(task);
        taskList.add(task2);
        taskList.add(task3);
//        taskList.add(task4);
        taskList.add(task4);
        taskList.add(task5);

//        taskList.incoming(50, 300);

        assertEquals(4,taskList.incoming(50, 320));
    }
}