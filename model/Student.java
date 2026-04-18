package model;

public class Student extends User {

    public Student(int id, String username, String password,
                   String fullName, String email, String phone, String dept) {
        super(id, username, password, fullName, email, phone, "STUDENT", dept);
    }

    @Override
    public String getDashboardTitle() { return "Student Portal"; }

    //take username as index number for students
    public String getIndexNumber() { return getUsername(); }
}
