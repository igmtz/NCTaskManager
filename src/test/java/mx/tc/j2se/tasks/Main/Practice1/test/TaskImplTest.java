package mx.tc.j2se.tasks.Main.Practice1.test;

import mx.tc.j2se.tasks.TaskImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskImplTest {

    @Test
    void setTitleTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        task.setTitle("setTest");
        assertEquals("setTest", task.getTitle());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        task2.setTitle("setTest2");
        assertEquals("setTest2", task2.getTitle());
    }
    @Test
    void getTitleTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        assertEquals("Test1", task.getTitle());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        assertEquals("Test2", task2.getTitle());
    }

    @Test
    void setTimeTest() {
        TaskImpl task = new TaskImpl("Test1", 100, 400, 50);
        task.setTime(150);
        assertEquals(150, task.getTime());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        task2.setTime(150);
        assertEquals(150, task.getTime());
        assertEquals(150, task2.getStartTime());

        TaskImpl task3 = new TaskImpl("Test3", 100, 400, 50);
        task3.setTime(150);
        assertEquals(150, task3.getEndTime());

        TaskImpl task4 = new TaskImpl("Test4", 100, 400, 50);
        task4.setTime(150);
        assertEquals(0, task.getRepeatInterval());

        TaskImpl task5 = new TaskImpl("Test5", 100, 400, 50);
        task5.setTime(150);
        assertFalse(task5.isRepeated());

        TaskImpl task6 = new TaskImpl("Test6", 100);
        task6.setTime(150);
        assertEquals(150, task6.getTime());

        TaskImpl task7 = new TaskImpl("Test7", 100);
        task7.setTime(150);
        assertFalse(task7.isRepeated());
    }
    @Test
    void getTimeTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        assertEquals(100, task.getTime());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        assertEquals(100, task2.getTime());
    }

    @Test
    void setActiveTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        task.setActive(true);
        assertTrue(task.isActive());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        task2.setActive(true);
        assertTrue(task2.isActive());
    }
    @Test
    void isActiveTest() {
        TaskImpl task = new TaskImpl("Test1", 100);
        assertFalse(task.isActive());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        assertFalse(task2.isActive());
    }

    @Test
    void getStartTimeTest() {
        TaskImpl task = new TaskImpl("Test1", 200);
        assertEquals(200, task.getStartTime());

        TaskImpl task2 = new TaskImpl("Test2", 100, 400, 50);
        assertEquals(100, task2.getStartTime());
    }

    @Test
    void getEndTimeTest() {
        TaskImpl task = new TaskImpl("Test1", 100, 300, 50);
        assertEquals(300, task.getEndTime());

        TaskImpl task2 = new TaskImpl("Test 2", 100);
        assertEquals(100, task2.getEndTime());
    }

    @Test
    void getRepeatIntervalTest() {
        TaskImpl task = new TaskImpl("Test1", 100, 300, 50);
        assertEquals(50, task.getRepeatInterval());

        TaskImpl task2 = new TaskImpl("Test2", 100);
        assertEquals(0, task2.getRepeatInterval());
    }

    @Test
    void isRepeatedTest() {
        TaskImpl task = new TaskImpl("Test1", 100, 300, 50);
        assertTrue(task.isRepeated());

        TaskImpl task2 = new TaskImpl("Test2", 100);
        assertFalse(task2.isRepeated());
    }

    @Test
    void SetTimeTest() {
        TaskImpl task = new TaskImpl("Test1", 100, 400, 50);
        task.setTime(150, 450, 100);
        assertEquals(150, task.getStartTime());
        assertEquals(450, task.getEndTime());
        assertEquals(100, task.getRepeatInterval());

        TaskImpl task2 = new TaskImpl("Test2", 100);
        task2.setTime(150, 450, 100);
        assertTrue(task2.isRepeated());

        TaskImpl task3 = new TaskImpl("Test3", 100);
        task3.setTime(150, 450, 100);
        assertEquals(150, task3.getStartTime());

        TaskImpl task4 = new TaskImpl("Test4", 100);
        task4.setTime(150, 450, 100);
        assertEquals(450, task4.getEndTime());

        TaskImpl task5 = new TaskImpl("Test5", 100);
        task5.setTime(150, 450, 100);
        assertEquals(100, task5.getRepeatInterval());
    }

    @Test
    void nextTimeAfter() {
        TaskImpl task = new TaskImpl("Test1", 100);
        task.setActive(true);
        assertEquals(100, task.nextTimeAfter(80));

        TaskImpl task2 = new TaskImpl("Test2", 100);
        task2.setActive(true);
        assertEquals(-1, task2.nextTimeAfter(100));

        TaskImpl task3 = new TaskImpl("Test3", 100);
        task3.setActive(false);
        assertEquals(-1, task3.nextTimeAfter(100));

        TaskImpl task4 = new TaskImpl("Test4", 100, 200, 24);
        task4.setActive(true);
        assertEquals(-1, task4.nextTimeAfter(196));

        TaskImpl task5 = new TaskImpl("Test5", 100, 200, 24);
        task5.setActive(true);
        assertEquals(172, task5.nextTimeAfter(170));

        TaskImpl task6 = new TaskImpl("Test6", 100, 200, 24);
        task6.setActive(true);
        assertEquals(100, task6.nextTimeAfter(50));

        TaskImpl task7 = new TaskImpl("Test7", 100, 200, 24);
        task7.setActive(true);
        assertEquals(-1, task7.nextTimeAfter(199));

        TaskImpl task8 = new TaskImpl("Test8", 100, 200, 24);
        task8.setActive(false);
        assertEquals(-1, task8.nextTimeAfter(50));
    }
}