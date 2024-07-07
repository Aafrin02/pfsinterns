import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentRecordSystem {

    private static final String FILE_NAME = "students.txt";
    private static ArrayList<Student> studentList = new ArrayList<>();

    public static void main(String[] args) {
        loadStudents();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nStudent Record Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    updateStudent(scanner);
                    break;
                case 4:
                    deleteStudent(scanner);
                    break;
                case 5:
                    saveStudents();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter roll number: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid roll number. Enter again: ");
            scanner.next();
        }
        int rollNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        studentList.add(new Student(name, rollNumber, grade));
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student student : studentList) {
                System.out.println(student);
            }
        }
    }

    private static void updateStudent(Scanner scanner) {
        System.out.print("Enter roll number of the student to update: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid roll number. Enter again: ");
            scanner.next();
        }
        int rollNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        Student studentToUpdate = null;

        for (Student student : studentList) {
            if (student.getRollNumber() == rollNumber) {
                studentToUpdate = student;
                break;
            }
        }

        if (studentToUpdate != null) {
            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new grade: ");
            String grade = scanner.nextLine();
            studentToUpdate.setName(name);
            studentToUpdate.setGrade(grade);
            System.out.println("Student record updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter roll number of the student to delete: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid roll number. Enter again: ");
            scanner.next();
        }
        int rollNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        Student studentToDelete = null;

        for (Student student : studentList) {
            if (student.getRollNumber() == rollNumber) {
                studentToDelete = student;
                break;
            }
        }

        if (studentToDelete != null) {
            studentList.remove(studentToDelete);
            System.out.println("Student record deleted.");
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int rollNumber = Integer.parseInt(parts[1]);
                String grade = parts[2];
                studentList.add(new Student(name, rollNumber, grade));
            }
        } catch (IOException e) {
            System.out.println("Error loading student records.");
        }
    }

    private static void saveStudents() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : studentList) {
                writer.write(student.getName() + "," + student.getRollNumber() + "," + student.getGrade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving student records.");
        }
    }
}

