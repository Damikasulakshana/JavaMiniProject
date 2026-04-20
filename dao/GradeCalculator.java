package dao;


// GradeCalculator – calculate grade and grade point

public class GradeCalculator {

    public static final double QUIZ_MAX = 5.0;
    public static final double MID_MAX = 30.0;
    public static final double CA_MAX = 40.0;
    public static final double END_EXAM_MAX = 60.0;
    public static final double TOTAL_MAX = 100.0;
    public static final double CA_PASS_MARK = 16.0; 

    private GradeCalculator() {
        
    }

   
    public static String calculateGrade(double marks) {
        if (marks >= 85) return "A+";
        if (marks >= 75) return "A";
        if (marks >= 70) return "A-";
        if (marks >= 65) return "B+";
        if (marks >= 60) return "B";
        if (marks >= 55) return "B-";
        if (marks >= 50) return "C+";
        if (marks >= 45) return "C";
        if (marks >= 40) return "C-";
        if (marks >= 35) return "D";
        return "E";
    }

 
    public static double calculateGradePoint(double marks) {
        if (marks >= 85) return 4.0;
        if (marks >= 75) return 4.0;
        if (marks >= 70) return 3.7;
        if (marks >= 65) return 3.3;
        if (marks >= 60) return 3.0;
        if (marks >= 55) return 2.7;
        if (marks >= 50) return 2.3;
        if (marks >= 45) return 2.0;
        if (marks >= 40) return 1.7;
        if (marks >= 35) return 1.3;
        return 0.0;
    }

    // calculate CA marks
    public static double calculateCaMarks(double quiz1, double quiz2, double quiz3, double midMarks) {
        validateCaComponents(quiz1, quiz2, quiz3, midMarks);

        double lowestQuiz = Math.min(quiz1, Math.min(quiz2, quiz3));
        double bestTwoQuizTotal = quiz1 + quiz2 + quiz3 - lowestQuiz;
        return bestTwoQuizTotal + midMarks;
    }

    public static double calculateTotalMarks(double caMarks, double endExamMarks) {
        validateStoredMarks(caMarks, endExamMarks);
        return caMarks + endExamMarks;
    }

    public static boolean isCaEligible(double caMarks) {
        return caMarks >= CA_PASS_MARK;
    }

    //Check CA eligibility
    public static String determineGrade(double caMarks, double endExamMarks) {
        if (!isCaEligible(caMarks)) {
            return "E(CA)";
        }
        return calculateGrade(calculateTotalMarks(caMarks, endExamMarks));
    }

    //Determine grade point
    public static double determineGradePoint(double caMarks, double endExamMarks) {
        if (!isCaEligible(caMarks)) {
            return 0.0;
        }
        return calculateGradePoint(calculateTotalMarks(caMarks, endExamMarks));
    }

    public static void validateCaComponents(double quiz1, double quiz2, double quiz3, double midMarks) {
        validateRange("Quiz 1", quiz1, 0, QUIZ_MAX);
        validateRange("Quiz 2", quiz2, 0, QUIZ_MAX);
        validateRange("Quiz 3", quiz3, 0, QUIZ_MAX);
        validateRange("Mid exam", midMarks, 0, MID_MAX);
    }

    //Validate marks and prevent invalid totals 
    public static void validateStoredMarks(double caMarks, double endExamMarks) {
        validateRange("CA marks", caMarks, 0, CA_MAX);
        validateRange("End exam marks", endExamMarks, 0, END_EXAM_MAX);
    }
	
	//checks value is within the allowed range
    private static void validateRange(String label, double value, double min, double max) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(label + " must be between " + min + " and " + max + ".");
        }
    }
}