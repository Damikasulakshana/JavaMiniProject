package model;
public class Course {

    // === VARIABLES ===
    private int id;              
    private String courseCode;  
    private String courseName;  
    private int credits;        
    private int lecturerId;     
    private String lecturerName;

    // === CONSTRUCTOR ===
    // Used to create a Course object with all details
    public Course(int id, String courseCode, String courseName,
                  int credits, int lecturerId, String lecturerName) {

        // Assign values to variables
        this.id           = id;
        this.courseCode   = courseCode;
        this.courseName   = courseName;
        this.credits      = credits;
        this.lecturerId   = lecturerId;
        this.lecturerName = lecturerName;
    }

    // === GETTER METHODS ===

    // Return course ID
    public int getId() {
        return id;
    }

    // Return course code
    public String getCourseCode() {
        return courseCode;
    }

    // Return course name
    public String getCourseName() {
        return courseName;
    }

    // Return credit value
    public int getCredits() {
        return credits;
    }

    // Return lecturer ID
    public int getLecturerId() {
        return lecturerId;
    }

    // Return lecturer name
    public String getLecturerName() {
        return lecturerName;
    }

    // === SETTER METHODS ===

    // Update course code
    public void setCourseCode(String v) {
        courseCode = v;
    }

    // Update course name
    public void setCourseName(String v) {
        courseName = v;
    }

    // Update credits
    public void setCredits(int v) {
        credits = v;
    }

    // Update lecturer ID
    public void setLecturerId(int v) {
        lecturerId = v;
    }

    // Update lecturer name
    public void setLecturerName(String v) {
        lecturerName = v;
    }
}
