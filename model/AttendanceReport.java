package model;

import dao.AttendanceDAO;

// Import list classes
import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------
// AttendanceReport – generates text attendance report
// OOP Concepts used:
// 1. INHERITANCE  → extends Report class
// 2. POLYMORPHISM → overrides generate() method
// -------------------------------------------------------
public class AttendanceReport extends Report {

    // Store student ID and course code
    private String studentId;
    private String courseCode;

    // Constructor → initializes report with student & course
    public AttendanceReport(String studentId, String courseCode) {
        
        // Call parent (Report) constructor and set title
        super("Attendance Report – " + studentId + " / " + courseCode);

        // Assign values
        this.studentId  = studentId;
        this.courseCode = courseCode;
    }

    // Override generate() method from Report class
    @Override
    public List<String> generate() {

        // Create list to store report lines
        List<String> lines = new ArrayList<>();

        try {
            // Create DAO object to fetch data from database
            AttendanceDAO dao = new AttendanceDAO();

            // Get attendance summary for this student and course
            // "ALL" means include all session types
            AttendanceSummary s = dao.getSummary(studentId, courseCode, "ALL");

            // Add report title
            lines.add("=== " + title + " ===");

            // Add attendance details
            lines.add("Present: " + s.getPresentSessions() + " / " + s.getTotalSessions());

            // Raw attendance percentage
            lines.add("Raw %: " + s.getPercentage());

            // Effective percentage (after medicals etc.)
            lines.add("Effective %: " + s.getEffectivePct());

            // Eligibility status (e.g., Eligible / Not Eligible)
            lines.add("Status: " + s.getEligibility());

        } catch (Exception e) {

            // If error occurs, show error message
            lines.add("Error: " + e.getMessage());
        }

        return lines;
    }
}
