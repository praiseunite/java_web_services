package com.aptech.persistence;

import java.util.List;
import jakarta.persistence.EntityManagerFactory;

public class PersistenceLab {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || !validCommand(args)) {
            System.err.println("Use: list | add \"name\" | find id | search \"name\" | rename id \"name\" | rollback id \"name\" | delete id");
            System.exit(2);
        }
        EntityManagerFactory factory = Database.open();
        int exitCode = 0;
        try {
            StudentStore store = new StudentStore(factory);
            String command = args[0];
            switch (command) {
                case "list" -> print(store.list());
                case "add" -> {
                    Student saved = store.add(args[1]);
                    System.out.println("Saved " + saved.getId() + ": " + saved.getName());
                }
                case "find" -> {
                    Student found = store.find(id(args[1]));
                    System.out.println(found == null ? "Student not found." : found.getId() + ": " + found.getName());
                }
                case "search" -> print(store.search(args[1]));
                case "rename", "rollback" -> {
                    boolean cancel = "rollback".equals(command);
                    boolean found = store.rename(id(args[1]), args[2], cancel);
                    System.out.println(!found ? "Student not found." : cancel ? "Change rolled back." : "Change committed.");
                }
                case "delete" -> System.out.println(store.delete(id(args[1])) ? "Student deleted." : "Student not found.");
                default -> throw new IllegalArgumentException("Unknown command.");
            }
        } catch (IllegalArgumentException problem) {
            System.err.println("Rejected: " + problem.getMessage());
            exitCode = 2;
        } finally {
            factory.close();
        }
        if (exitCode != 0) System.exit(exitCode);
    }

    private static boolean validCommand(String[] args) {
        return switch (args[0]) {
            case "list" -> args.length == 1;
            case "add", "find", "search", "delete" -> args.length == 2;
            case "rename", "rollback" -> args.length == 3;
            default -> false;
        };
    }

    private static long id(String text) {
        long value = Long.parseLong(text);
        if (value < 1) throw new IllegalArgumentException("Id must be positive.");
        return value;
    }

    private static void print(List<Student> students) {
        if (students.isEmpty()) System.out.println("No students.");
        for (Student student : students) {
            System.out.println(student.getId() + ": " + student.getName());
        }
        System.out.println("Rows: " + students.size());
    }
}
