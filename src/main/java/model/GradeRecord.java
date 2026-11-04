package model;

public class GradeRecord {

    private final String studentName;
    private final String subject;
    private final int grade;

    public GradeRecord(String studentName, String subject, int grade) {
        this.studentName = studentName;
        this.subject = subject;
        this.grade = grade;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getSubject() {
        return subject;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return studentName + ";" + subject + ";" + grade;
    }
}