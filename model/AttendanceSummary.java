package model;

// -------------------------------------------------------
// AttendanceSummary – holds attendance data for a student in a course
// OOP Concept:
// Encapsulation → data + methods are inside one class
// -------------------------------------------------------
public class AttendanceSummary {

    // Student details
    private String studentId;
    private String studentName;

    // Course details
    private String courseCode;

    // Attendance data
    private int totalSessions;    
    private int presentSessions; 
    private int medicalSessions;  

    // Calculated values
    private double percentage;    // Raw attendance %
    private double effectivePct;  // Attendance % including medical

    // Constructor → used to initialize all values
    public AttendanceSummary(String studentId, String studentName,
                              String courseCode,
                              int totalSessions, int presentSessions,
                              int medicalSessions) {

        // Assign values
        this.studentId       = studentId;
        this.studentName     = studentName;
        this.courseCode      = courseCode;
        this.totalSessions   = totalSessions;
        this.presentSessions = presentSessions;
        this.medicalSessions = medicalSessions;

        // -------------------------------
        // Calculate RAW percentage
        // Formula: (present / total) * 100
        // If total = 0 → avoid divide by zero → return 0
        // -------------------------------
        this.percentage = totalSessions == 0 ? 0
                         : (presentSessions * 100.0) / totalSessions;

        // -------------------------------
        // Calculate EFFECTIVE percentage
        // Formula: (present + medical) / total * 100
        // -------------------------------
        this.effectivePct = totalSessions == 0 ? 0
                           : ((presentSessions + medicalSessions) * 100.0) / totalSessions;
    }

    // == GETTER METHODS 

    // Return student ID
    public String getStudentId() {
        return studentId;
    }

    // Return student name
    public String getStudentName() {
        return studentName;
    }

    // Return course code
    public String getCourseCode() {
        return courseCode;
    }

    // Return total sessions
    public int getTotalSessions() {
        return totalSessions;
    }

    // Return present sessions
    public int getPresentSessions() {
        return presentSessions;
    }

    // Return medical sessions
    public int getMedicalSessions() {
        return medicalSessions;
    }

    // Return RAW percentage (rounded to 1 decimal place)
    public double getPercentage() {
        return Math.round(percentage * 10.0) / 10.0;
    }

    // Return EFFECTIVE percentage (rounded)
    public double getEffectivePct() {
        return Math.round(effectivePct * 10.0) / 10.0;
    }

    // -------------------------------
    // Check eligibility
    // Rule: effective % >= 80 → Eligible
    // -------------------------------
    public String getEligibility() {
        return effectivePct >= 80 ? "Eligible" : "Not Eligible";
    }
}
