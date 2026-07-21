import java.util.Scanner;

public class Marks {

        static final String[] SUBJECT_NAMES = {"Mathematics", "Chemistry", "Physics"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of students (n): ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        int[][] marks = new int[n][3];
        boolean[] hasMarks = new boolean[n]; // tracks if a student's marks were added

        printMenu();

        while (true) {
            System.out.print("\nEnter command: ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "add":
                        handleAdd(parts, marks, hasMarks, n, scanner);
                        break;
                    case "update":
                        handleUpdate(parts, marks, hasMarks, n, scanner);
                        break;
                    case "average_s":
                        handleAverageSubject(parts, marks, hasMarks, n);
                        break;
                    case "average":
                        handleAverageStudent(parts, marks, hasMarks, n);
                        break;
                    case "total":
                        handleTotal(parts, marks, hasMarks, n);
                        break;
                    case "menu":
                        printMenu();
                        break;
                    case "exit":
                        System.out.println("Exiting program. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Unknown command. Type 'menu' to see available commands.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void printMenu() {
        
        System.out.println(" Student Marks Manager");
        
        System.out.println(" add [studentID] - add marks for a student");
        System.out.println(" update [studentID] [subjectID] - update one subject mark");
        System.out.println(" average_s [subjectID] - average mark for a subject");
        System.out.println(" average [studentID] - average mark for a student");
        System.out.println(" total [studentID] - total mark for a student");
        System.out.println(" menu - show this menu again");
        System.out.println(" exit - quit the program");
      
    }

    static int parseStudentID(String raw, int n) {
        int id = Integer.parseInt(raw);
        if (id < 1 || id > n) {
            throw new IllegalArgumentException("studentID must be between 1 and " + n);
        }
        return id;
    }

    static int parseSubjectID(String raw) {
        int id = Integer.parseInt(raw);
        if (id < 1 || id > 3) {
            throw new IllegalArgumentException("subjectID must be between 1 and 3");
        }
        return id;
    }

    static void handleAdd(String[] parts, int[][] marks, boolean[] hasMarks, int n, Scanner scanner) {
        if (parts.length != 2) {
            System.out.println("Usage: add [studentID]");
            return;
        }
        int studentID = parseStudentID(parts[1], n);

        for (int subject = 0; subject < 3; subject++) {
            System.out.print("Enter mark for " + SUBJECT_NAMES[subject] + ": ");
            int mark = Integer.parseInt(scanner.nextLine().trim());
            marks[studentID - 1][subject] = mark;
        }
        hasMarks[studentID - 1] = true;
        System.out.println("Marks added for student " + studentID + ".");
    }

    static void handleUpdate(String[] parts, int[][] marks, boolean[] hasMarks, int n, Scanner scanner) {
        if (parts.length != 3) {
            System.out.println("Usage: update [studentID] [subjectID]");
            return;
        }
        int studentID = parseStudentID(parts[1], n);
        int subjectID = parseSubjectID(parts[2]);

        System.out.print("Enter new mark for " + SUBJECT_NAMES[subjectID - 1]
                + " (student " + studentID + "): ");
        int mark = Integer.parseInt(scanner.nextLine().trim());
        marks[studentID - 1][subjectID - 1] = mark;
        hasMarks[studentID - 1] = true;
        System.out.println("Mark updated.");
    }

    static void handleAverageSubject(String[] parts, int[][] marks, boolean[] hasMarks, int n) {
        if (parts.length != 2) {
            System.out.println("Usage: average_s [subjectID]");
            return;
        }
        int subjectID = parseSubjectID(parts[1]);

        double sum = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (hasMarks[i]) {
                sum += marks[i][subjectID - 1];
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No marks recorded yet for " + SUBJECT_NAMES[subjectID - 1] + ".");
            return;
        }
        System.out.printf("Average mark for %s: %.2f%n", SUBJECT_NAMES[subjectID - 1], sum / count);
    }

    static void handleAverageStudent(String[] parts, int[][] marks, boolean[] hasMarks, int n) {
        if (parts.length != 2) {
            System.out.println("Usage: average [studentID]");
            return;
        }
        int studentID = parseStudentID(parts[1], n);
        if (!hasMarks[studentID - 1]) {
            System.out.println("No marks recorded yet for student " + studentID + ".");
            return;
        }
        int sum = marks[studentID - 1][0] + marks[studentID - 1][1] + marks[studentID - 1][2];
        System.out.printf("Average mark for student %d: %.2f%n", studentID, sum / 3.0);
    }

    static void handleTotal(String[] parts, int[][] marks, boolean[] hasMarks, int n) {
        if (parts.length != 2) {
            System.out.println("Usage: total [studentID]");
            return;
        }
        int studentID = parseStudentID(parts[1], n);
        if (!hasMarks[studentID - 1]) {
            System.out.println("No marks recorded yet for student " + studentID + ".");
            return;
        }
        int total = marks[studentID - 1][0] + marks[studentID - 1][1] + marks[studentID - 1][2];
        System.out.println("Total mark for student " + studentID + ": " + total);
    }
}