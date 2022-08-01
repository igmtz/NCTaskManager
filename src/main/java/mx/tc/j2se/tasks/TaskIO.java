package mx.tc.j2se.tasks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Objects;
import java.util.TimeZone;

public class TaskIO {

    /**
     * writeBinary method write the tasks' data on a file
     * @param tasks is the list of elements
     * @param out is the storage stream
     * @throws IOException
     */
    void writeBinary(AbstractTaskList tasks, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);
        dataOutputStream.writeInt(tasks.size());
        Iterator<Task> it = tasks.iterator();
        while (it.hasNext()) {
            Task i = it.next();
            dataOutputStream.writeInt(i.getTitle().length());
            dataOutputStream.writeUTF(i.getTitle());
            dataOutputStream.writeBoolean(i.isActive());
            dataOutputStream.writeLong(i.getRepeatInterval());
            if (i.isRepeated()) {
                dataOutputStream.writeLong(i.getStartTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                dataOutputStream.writeLong(i.getEndTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            } else {
                dataOutputStream.writeLong(i.getTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
        dataOutputStream.close();

    }

    /**
     * readBinary collection reads a collection data
     * and put them on a tasks list
     * @param tasks is the data collection
     * @param in is the reading value
     * @throws IOException
     */
    void readBinary(AbstractTaskList tasks, InputStream in) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(in);
        int count = dataInputStream.readInt();

        for (int i = 0; i < count; i++) {
            Task task = new TaskImpl();

            int sizeTitle = dataInputStream.readInt();
            String title = dataInputStream.readUTF();
            boolean isActive = dataInputStream.readBoolean();
            Long interval = dataInputStream.readLong();

            task.setTitle(title);
            if (interval != 0) {
                LocalDateTime startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dataInputStream.readLong()),
                        TimeZone.getDefault().toZoneId());
                LocalDateTime endTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dataInputStream.readLong()),
                        TimeZone.getDefault().toZoneId());

                task.setTime(startTime, endTime, interval);
                task.setActive(isActive);
            } else {
                LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochMilli(dataInputStream.readLong()),
                        TimeZone.getDefault().toZoneId());
                task.setTime(time);
                task.setActive(isActive);
            }
            tasks.add(task);
        }
        dataInputStream.close();
    }

    /**
     * Streaming writing method
     * @param tasks is the list of data
     * @param file is the storing file
     * @throws IOException
     */
    void write(AbstractTaskList tasks, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStream outputStream = new BufferedOutputStream(fileOutputStream);
        writeBinary(tasks, outputStream);
        outputStream.close();
        fileOutputStream.close();

    }

    /**
     * Streaming reading method
     * @param tasks is the list of data
     * @param file is the storing file
     * @throws IOException
     */
    void read(AbstractTaskList tasks, File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream inputStream = new BufferedInputStream(fileInputStream);
        readBinary(tasks, inputStream);
        inputStream.close();
        fileInputStream.close();
    }

    /**
     * Writes tasks from the list to the stream in JSON format
     * @param tasks is the data
     * @param out is the writing location
     * @throws IOException
     */
    public static void write(AbstractTaskList tasks, Writer out) throws IOException {
        Gson gson = new Gson();
        ArrayTaskListImpl tasksArray = new ArrayTaskListImpl();
        tasks.getStream().filter(Objects::nonNull).forEach(tasksArray::add);
        gson.toJson(tasksArray, out);
        out.close();
    }

    /**
     * Reads tasks from the stream to the list
     * @param tasks is th data
     * @param in is the storing location
     */
    public static void read(AbstractTaskList tasks, Reader in) {
        Gson gson = new Gson();
        ArrayTaskListImpl list = gson.fromJson(in, ArrayTaskListImpl.class);
        for (Task task : list) {
            if (task != null) {
                tasks.add(task);
            }
        }
    }

    /**
     * Writes tasks to a file in JSON format
     * @param tasks is the data
     * @param file is the writing location
     * @throws IOException
     */
    public static void writeText(AbstractTaskList tasks, File file) throws IOException {
        Gson gson = new Gson();
        ArrayTaskListImpl list = new ArrayTaskListImpl();
        tasks.getStream().filter(Objects::nonNull).forEach(list::add);
        gson.toJson(list, new FileWriter(file));
    }

    /**
     * Reads tasks from a file
     * @param tasks is the data
     * @param file is the storing location
     * @throws FileNotFoundException
     */
    public static void readText(AbstractTaskList tasks, File file) throws FileNotFoundException {
        Gson gson = new Gson();
        ArrayTaskListImpl list = gson.fromJson(new FileReader(file), ArrayTaskListImpl.class);
        for (Task task : list) {
            if (task != null) {
                list.add(task);
            }
        }
    }
}
