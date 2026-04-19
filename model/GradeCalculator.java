package model;

// -------------------------------------------------------
// GradeCalculator – Abstract class for grading
// -------------------------------------------------------
public abstract class GradeCalculator {

    protected String courseCode;
    protected double marks;

    public GradeCalculator(String courseCode, double marks) {
        this.courseCode = courseCode;
        this.marks      = marks;
    }

    
    public abstract String calculate();

    public double getMarks() { return marks; }
}
