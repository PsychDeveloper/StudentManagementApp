import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int age;
    private String course;

    public Student(int id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Student ID: " + id +
               "\nName: " + name +
               "\nAge: " + age +
               "\nCourse: " + course;
    }
}

public class StudentManagement {
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        loadDataFromFile(); // ✅ Load existing students from file at startup

        while (true) {
            int choice = printMenu();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewAllStudents();
                    break;
                case 3:
                    searchStudentById();
                    break;
                case 4:
                    deleteStudentById();
                    break;
                case 5:
                    deleteAllData();
                    break;
                case 6:
                    System.out.println("Exiting program... Goodbye!");
                    saveDataToFile(); // ✅ Save before exiting
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static int printMenu() {
        System.out.println("\n==== Student Management System ====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student by ID");
        System.out.println("4. Delete Student by ID");
        System.out.println("5. Delete All Student Data");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline
        return choice;
    }

    private static void addStudent() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Student s : studentList) {
            if (s.getId() == id) {
                System.out.println("Student with this ID already exists!");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        Student student = new Student(id, name, age, course);
        studentList.add(student);
        saveDataToFile(); // ✅ Save after adding
        System.out.println("Student added successfully!");
    }

    private static void viewAllStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students to show.");
            return;
        }

        System.out.println("\n-- All Students --");
        for (Student s : studentList) {
            System.out.println(s);
            System.out.println("----------------------");
        }
    }

    private static void searchStudentById() {
        System.out.print("Enter Student ID to search: ");
        int id = sc.nextInt();

        for (Student s : studentList) {
            if (s.getId() == id) {
                System.out.println("Student found:\n" + s);
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    private static void deleteStudentById() {
        System.out.print("Enter Student ID to delete: ");
        int id = sc.nextInt();

        for (Student s : studentList) {
            if (s.getId() == id) {
                studentList.remove(s);
                saveDataToFile(); // ✅ Save after deletion
                System.out.println("Student deleted successfully.");
                return;
            }
        }
        System.out.println("Student with ID " + id + " not found.");
    }

    private static void deleteAllData() {
        System.out.print("Are you sure you want to delete all student data? (yes/no): ");
        String confirmation = sc.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            studentList.clear();
            saveDataToFile(); // ✅ Save after clearing
            System.out.println("All student data deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private static void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(studentList);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            studentList = (ArrayList<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}
// This code implements a simple student management system with functionalities to add, view, search, delete students, and save/load data to/from a file. 