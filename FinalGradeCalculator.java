package model;

import dao.MarksDAO;

// -------------------------------------------------------
// FinalGradeCalculator – calculates grade from FINAL marks
// OOP: INHERITANCE (extends GradeCalculator)
//      POLYMORPHISM (overrides calculate())
// -------------------------------------------------------
public class FinalGradeCalculator extends GradeCalculator {

    public FinalGradeCalculator(String courseCode, double finalMarks) {
        super(courseCode, finalMarks);
    }

    @Override
    public String calculate() {
        return MarksDAO.calcGrade(marks);
    }
}
