/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javvproject;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private int rollNumber;
  
    private String name;
    private String grade;

    public Student(int rollNumber, String name, String grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.grade = grade;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private String dataFile = "students.dat";

    public StudentManagementSystem() {
        loadStudents();
    }

    public void addStudent(int rollNumber, String name, String grade) {
        Student student = new Student(rollNumber, name, grade);
        students.add(student);
        saveStudents();
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
        saveStudents();
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    private void loadStudents() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(dataFile)) ) {
            students = (List<Student>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            students = new ArrayList<>();
        }
    }

    private void saveStudents() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            outputStream.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem system = new StudentManagementSystem();

        while (true) {
            System.out.println("Student Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Select an option (1/2/3/4/5): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll Number: ");
                    int rollNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = scanner.nextLine();
                    system.addStudent(rollNumber, name, grade);
                    System.out.println("Student added.");
                    break;
                case 2:
                    System.out.print("Enter Roll Number to Remove: ");
                    int removeRollNumber = scanner.nextInt();
                    system.removeStudent(removeRollNumber);
                    System.out.println("Student removed.");
                    break;
                case 3:
                    System.out.print("Enter Roll Number to Search: ");
                    int searchRollNumber = scanner.nextInt();
                    Student student = system.searchStudent(searchRollNumber);
                    if (student != null) {
                        System.out.println("Student found: " + student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    List<Student> students = system.getAllStudents();
                    System.out.println("All Students:");
                    for (Student s : students) {
                        System.out.println(s);
                    }
                    break;
                case 5:
                    System.out.println("Exiting Student Management System.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}

