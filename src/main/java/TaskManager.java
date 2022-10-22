import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    static String[][] tasks;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner optionChoice = new Scanner(System.in);
        String read = null;

        do {
            loadTasksFile("tasks.csv");
            choiceList();
            read = optionChoice.nextLine();

            if (!read.equals("exit")) {

                if (read.equals("list")) {
                    optionList();
                    System.out.println();
                } else if (read.equals("add")) {
                    optionAdd("tasks.csv");
                }


            } else {
                optionExit();
            }
        } while (!read.equals("exit"));
    }

    public static void optionAdd(String fileName) {
        Scanner scanner = new Scanner(System.in);
        Path path = Paths.get(fileName);

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Insert next record. Use format: task name / deadline / important? true or false ");
        System.out.println(ConsoleColors.RED + "use quit if data entry is complete");
        String input = "";
        StringBuilder str = new StringBuilder();

        while (!input.equals("quit")) {

            input = scanner.nextLine();
            str.append(input);
            if (!input.equals("quit")) {
                try {
                    Files.writeString(path, str + " \n", StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println();
    }


    public static void optionList() {

        for (int i = 0; i < tasks.length; i++) {
            String[] task = tasks[i];
            System.out.print("[" + i + "]: ");
            for (String taskCell : task) {
                System.out.print(taskCell + "   ");
            }
            System.out.println();
        }
    }

    public static void optionExit() {
        System.out.println(ConsoleColors.RED + "bye bye");
        System.exit(0);
    }

    public static void choiceList() {

        String[] optionList = {ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET, "add", "remove", "list", "exit "};

        for (String s : optionList) {
            System.out.println(s);
        }
        System.out.println();
    }


    private static void loadTasksFile(String fileName) {

        tasks = new String[0][3];
        try (Scanner reader = new Scanner(new FileReader(fileName))) {
            int i = 0;
            while (reader.hasNextLine()) {
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                String line = reader.nextLine();
                tasks[i] = line.split(", ");
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to load file. Check it" + e.getMessage());
        }
    }

    public static String[] addNewItem(String[] arr, String newElement) {
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = newElement;
        return arr;
    }

}
