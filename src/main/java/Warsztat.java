import org.apache.commons.lang3.ArrayUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Warsztat {

    private static File file = new File("tasks.csv");
    private static String[][] tasks;

    public static void main(String[] args) {

        loadTaskLists();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        do {
            optionList();
            String option = scanner.nextLine();

            switch (option) {
                case "add":
                    optionAdd();
                    break;
                case "remove":
                    optionRemove();
                    break;
                case "list":
                    taskList();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Please select a correct option" + "\n");
            }
        } while (!exit); {
            optionExit();
        }
    }

    private static void loadTaskLists() {

        String text = "";
        tasks = new String[0][3];

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                text = scanner.nextLine();
                tasks[tasks.length - 1] = text.split(", ");
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void optionList() {

        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
        System.out.println();
    }

    private static void taskList() {

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < tasks[i].length; j++) {
                str.append(tasks[i][j]).append(",").append(" ");
            }
            System.out.println("[" + (i + 1) + "]" + " " + str);
            str = new StringBuilder();
        }
        System.out.println();
    }

    private static void optionAdd() {


        Scanner scanner = new Scanner(System.in);
        String quit = "";

        do {
            String reading = "";
            System.out.println("name the task");
            reading = reading + scanner.nextLine() + ", ";
            System.out.println("state the planned date of completion of the task");
            reading = reading + scanner.nextLine() + ", ";
            System.out.println("is yout tank important? true / false");
            reading = reading + scanner.nextLine();

            tasks = Arrays.copyOf(tasks, tasks.length + 1);
            tasks[tasks.length - 1] = reading.split(", ");

            System.out.println("wanna quit? yes / no");
            quit = scanner.nextLine();

        } while (!quit.equals("yes"));
            System.out.println();
    }

    private static void optionRemove() {
        Scanner scanner = new Scanner(System.in);
        Integer reading = null;

        do {
            System.out.println("Select number to remove");
            try {
                reading = scanner.nextInt();
                tasks = ArrayUtils.remove(tasks, reading - 1);

            } catch (IndexOutOfBoundsException e) {
                System.err.println("Out of bound. You picked " + reading + " .Max value is a " + tasks.length );
            }
            System.out.println();
        }
        while (reading == null);
    }

    private static void optionExit() {

        try {
            FileWriter fileWriter = new FileWriter(file, false);
            StringBuilder str = new StringBuilder();

            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    str.append(tasks[i][j]).append(", ");
                }
                fileWriter.append(str).append("\n");
                str = new StringBuilder();
            }
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n" + ConsoleColors.RED + "Bye Bye" + ConsoleColors.RESET);
    }
}



