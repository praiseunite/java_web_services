public class StudentCardDemo {
    static class Student {
        int id;
        String name;
    }

    public static void main(String[] args) {
        Student student = new Student();
        student.id = 101;
        student.name = "Amina";
        System.out.println(student.id + ": " + student.name);
    }
}
