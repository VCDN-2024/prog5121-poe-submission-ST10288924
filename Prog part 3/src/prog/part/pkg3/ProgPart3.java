/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package prog.part.pkg3;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class ProgPart3 {

    private static int taskCounter = 0;
    private static ArrayList<String> developers = new ArrayList<>();
    private static ArrayList<String> taskNames = new ArrayList<>();
    private static ArrayList<String> taskIDs = new ArrayList<>();
    private static ArrayList<Integer> taskDurations = new ArrayList<>();
    private static ArrayList<String> taskStatuses = new ArrayList<>();

    public static void main(String[] args) {

        String logUsername = JOptionPane.showInputDialog(null, "Enter your Username:");
        String logPassword = JOptionPane.showInputDialog(null, "Enter your password:");
        String firstName = JOptionPane.showInputDialog(null, "Enter your firstname:");
        String lastName = JOptionPane.showInputDialog(null, "Enter your lastname:");

        Register user = new Register(logUsername, logPassword, firstName, lastName);

        user.checkPasswordComplexity();

        String enteredUsername = JOptionPane.showInputDialog(null, "Please enter your username to login:");
        String enteredPassword = JOptionPane.showInputDialog(null, "Please enter your password to login:");

        boolean loginUser = user.loginUser(enteredUsername, enteredPassword);

        if (loginUser) {
            JOptionPane.showMessageDialog(null, "Login successful. Welcome " + firstName + "!");
        } else {
            JOptionPane.showMessageDialog(null, "Login failed. Incorrect username or password.");
            System.exit(0);
        }

        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban:");

        while (true) {
            String[] options = {
                "1. Add tasks",
                "2. Display tasks with status 'done'",
                "3. Display task with longest duration",
                "4. Search task by name",
                "5. Search tasks by developer",
                "6. Delete task",
                "7. Display full report",
                "8. Quit"
            };

            String menu = String.join("\n", options);
            String choiceStr = JOptionPane.showInputDialog(
                    null,
                    "-Main Menu-\nChoose an option:\n" + menu,
                    "Main Menu",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (choiceStr == null) {
                JOptionPane.showMessageDialog(null, "Exiting the program. Goodbye!");
                System.exit(0);
            }

            int choice = -1;
            try {
                choice = Integer.parseInt(choiceStr.split("\\.")[0].trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid option from the menu.");
                continue;
            }

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    displayTasksWithStatusDone();
                    break;
                case 3:
                    displayTaskWithLongestDuration();
                    break;
                case 4:
                    searchTaskByName();
                    break;
                case 5:
                    searchTasksByDeveloper();
                    break;
                case 6:
                    deleteTaskByName();
                    break;
                case 7:
                    displayFullReport();
                    break;
                case 8:
                    JOptionPane.showMessageDialog(null, "Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please select an option from the menu.");
            }
        }
    }

    public static void addTask() {
        String taskName = JOptionPane.showInputDialog(null, "Enter the name of the task to be performed:");
        int taskNumber = taskCounter++;
        String taskDescription = JOptionPane.showInputDialog(null, "Enter a short description of the task:");

        if (taskDescription.length() > 50) {
            JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
            return;
        }

        String developerDetails = JOptionPane.showInputDialog(null, "Enter the first and last name of the developer assigned to the task:");
        String taskDurationStr = JOptionPane.showInputDialog(null, "Enter the estimated duration of the task in hours:");
        int taskDuration;

        try {
            taskDuration = Integer.parseInt(taskDurationStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number for the task duration.");
            return;
        }

        String[] statusOptions = {"To Do", "Done", "Doing"};
        String taskStatus = (String) JOptionPane.showInputDialog(
                null,
                "Select the task status:",
                "Task Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statusOptions,
                statusOptions[0]
        );

        String taskID = generateTaskID(taskName, taskNumber, developerDetails);
        String taskInfo = String.format("Task ID: %s\nTask Name: %s\nTask Description: %s\nDeveloper: %s\nTask Duration: %d\nTask Status: %s",
                taskID, taskName, taskDescription, developerDetails, taskDuration, taskStatus);

        JOptionPane.showMessageDialog(null, taskInfo);

        developers.add(developerDetails);
        taskNames.add(taskName);
        taskIDs.add(taskID);
        taskDurations.add(taskDuration);
        taskStatuses.add(taskStatus);

        JOptionPane.showMessageDialog(null, "Task successfully captured");
    }

    public static String generateTaskID(String taskName, int taskNumber, String developerDetails) {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerDetails.substring(developerDetails.length() - 3).toUpperCase();
    }

    public static void displayTasksWithStatusDone() {
        StringBuilder result = new StringBuilder("Tasks with status 'Done':\n");
        for (int i = 0; i < taskStatuses.size(); i++) {
            if (taskStatuses.get(i).equalsIgnoreCase("done")) {
                result.append(String.format("Developer: %s, Task Name: %s, Task Duration: %d hours\n",
                        developers.get(i), taskNames.get(i), taskDurations.get(i)));
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    public static void displayTaskWithLongestDuration() {
        if (taskDurations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tasks available.");
            return;
        }

        int maxDuration = taskDurations.get(0);
        int maxIndex = 0;
        for (int i = 1; i < taskDurations.size(); i++) {
            if (taskDurations.get(i) > maxDuration) {
                maxDuration = taskDurations.get(i);
                maxIndex = i;
            }
        }
        JOptionPane.showMessageDialog(null, String.format("Developer: %s, Task Duration: %d hours",
                developers.get(maxIndex), maxDuration));
    }

    public static void searchTaskByName() {
        String taskName = JOptionPane.showInputDialog(null, "Enter the task name to search for:");
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskNames.get(i).equalsIgnoreCase(taskName)) {
                JOptionPane.showMessageDialog(null, String.format("Task Name: %s, Developer: %s, Task Status: %s",
                        taskNames.get(i), developers.get(i), taskStatuses.get(i)));
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Task not found.");
    }

    public static void searchTasksByDeveloper() {
        String developerName = JOptionPane.showInputDialog(null, "Enter the developer's name to search for tasks:");
        StringBuilder result = new StringBuilder(String.format("Tasks assigned to %s:\n", developerName));
        for (int i = 0; i < developers.size(); i++) {
            if (developers.get(i).equalsIgnoreCase(developerName)) {
                result.append(String.format("Task Name: %s, Task Status: %s\n", taskNames.get(i), taskStatuses.get(i)));
            }
        }
        JOptionPane.showMessageDialog(null, result.toString());
    }

    public static void deleteTaskByName() {
        String taskName = JOptionPane.showInputDialog(null, "Enter the task name to delete:");
        for (int i = 0; i < taskNames.size(); i++) {
            if (taskNames.get(i).equalsIgnoreCase(taskName)) {
                developers.remove(i);
                taskNames.remove(i);
                taskIDs.remove(i);
                taskDurations.remove(i);
                taskStatuses.remove(i);
                JOptionPane.showMessageDialog(null, "Task successfully deleted.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Task not found.");
    }

    public static void displayFullReport() {
        StringBuilder report = new StringBuilder("Full Report of All Tasks:\n");
        for (int i = 0; i < taskNames.size(); i++) {
            report.append(String.format("Task ID: %s\nTask Name: %s\nDeveloper: %s\nTask Duration: %d hours\nTask Status: %s\n\n",
                    taskIDs.get(i), taskNames.get(i), developers.get(i), taskDurations.get(i), taskStatuses.get(i)));
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }
}

class Register {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public Register(String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void checkPasswordComplexity() {
       
    }

    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
    }
}


   
