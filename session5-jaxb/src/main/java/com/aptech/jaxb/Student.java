package com.aptech.jaxb;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {"fullName", "email", "course", "gpa"})
public class Student {
    private int id;
    private String fullName;
    private String email;
    private String course;
    private double gpa;
    private String password; // Fictional demo value only.

    public Student() { }
    public Student(int id, String fullName, String email, String course, double gpa, String password) {
        this.id = id; this.fullName = fullName; this.email = email;
        this.course = course; this.gpa = gpa; this.password = password;
    }
    @XmlAttribute(name = "id", required = true)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    @XmlElement(name = "full_name", required = true)
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    @XmlElement(name = "email", required = true)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    @XmlElement(name = "course", required = true)
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    @XmlElement(name = "gpa", required = true)
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    @XmlTransient
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    @Override
    public String toString() {
        return "Student [id=" + id + ", fullName=" + fullName + ", email=" + email
            + ", course=" + course + ", gpa=" + gpa + "]";
    }
}
