package prog.part.pkg3;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class ProgPart3Test {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        setupTestData();
    }

    private void setupTestData() {
        
        String input1 = "Create Login\nDescription\nMike Smith\n5\nTo Do\n";
        System.setIn(new ByteArrayInputStream(input1.getBytes()));
        ProgPart3.addTask();

        
        String input2 = "Create Add Features\nDescription\nEdward Harrison\n8\nDoing\n";
        System.setIn(new ByteArrayInputStream(input2.getBytes()));
        ProgPart3.addTask();

        
        String input3 = "Create Reports\nDescription\nSamantha Paulson\n2\nDone\n";
        System.setIn(new ByteArrayInputStream(input3.getBytes()));
        ProgPart3.addTask();

        
        String input4 = "Add Arrays\nDescription\nGlenda Oberholzer\n11\nTo Do\n";
        System.setIn(new ByteArrayInputStream(input4.getBytes()));
        ProgPart3.addTask();
    }

    @Test
    public void testAddTask() {
        String input = "TestTask\nTestTaskDescription\nTest Developer\n5\nDone\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ProgPart3.addTask();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Task successfully captured"));
    }

    @Test
    public void testDisplayTasksWithStatusDone() {
       
        outputStreamCaptor.reset();
        ProgPart3.displayTasksWithStatusDone();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Tasks with status 'Done'"));
        assertTrue(output.contains("Create Reports"));
        assertTrue(output.contains("Samantha Paulson"));
    }

    @Test
    public void testDisplayTaskWithLongestDuration() {
        
        outputStreamCaptor.reset();
        ProgPart3.displayTaskWithLongestDuration();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Developer: Glenda Oberholzer"));
        assertTrue(output.contains("Task Duration: 11 hours"));
    }

    @Test
    public void testSearchTaskByName() {
        
        outputStreamCaptor.reset();
        String searchInput = "Create Add Features\n";
        System.setIn(new ByteArrayInputStream(searchInput.getBytes()));
        ProgPart3.searchTaskByName();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Task Name: Create Add Features"));
        assertTrue(output.contains("Edward Harrison"));
    }

    @Test
    public void testSearchTasksByDeveloper() {
        
        outputStreamCaptor.reset();
        String searchInput = "Mike Smith\n";
        System.setIn(new ByteArrayInputStream(searchInput.getBytes()));
        ProgPart3.searchTasksByDeveloper();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Tasks assigned to Mike Smith:"));
        assertTrue(output.contains("Create Login"));
    }

    @Test
    public void testDeleteTaskByName() {
       
        outputStreamCaptor.reset();
        String deleteInput = "Create Login\n";
        System.setIn(new ByteArrayInputStream(deleteInput.getBytes()));
        ProgPart3.deleteTaskByName();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Task successfully deleted."));
    }

    @Test
    public void testDisplayFullReport() {
        
        outputStreamCaptor.reset();
        ProgPart3.displayFullReport();
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains("Full Report of All Tasks:"));
        assertTrue(output.contains("Task Name: Create Login"));
        assertTrue(output.contains("Task Name: Create Add Features"));
        assertTrue(output.contains("Task Name: Create Reports"));
        assertTrue(output.contains("Task Name: Add Arrays"));
    }
}



