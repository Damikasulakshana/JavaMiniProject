public class BMICalculator {

    private double weight;        // User's weight
    private double height;        // User's height
    private String unitType;      // English or Metric
    private double bmiValue;      // Calculated BMI result
    private String category;      // BMI category

    // Constructor
    public BMICalculator(double weight, double height, String unitType) {
        this.weight = weight;
        this.height = height;
        this.unitType = unitType;
        calculateBMI();        // Compute BMI
        determineCategory();
    }

    // Calculate BMI based on unit system (English or Metric)
    private void calculateBMI() {
        if (unitType.equalsIgnoreCase("English")) {
            // Formula for pounds and inches
            bmiValue = (weight * 703) / (height * height);
        } else {
            // Metric: convert cm to meters
            if (height > 3) {
                height = height / 100.0;  // Convert cm to meters
            }
            // Formula for kg and meters
            bmiValue = weight / (height * height);
        }
        System.out.println("Calculated BMI: " + bmiValue);
    }

    // Determine weight category based on standard BMI ranges
    private void determineCategory() {
        System.out.println("BMI Value for categorization: " + bmiValue);

        if (bmiValue < 18.5) {
            category = "Underweight";
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            category = "Normal";
        } else if (bmiValue >= 25 && bmiValue <= 29.9) {
            category = "Overweight";
        } else {
            category = "Obese";
        }
        System.out.println("Category: " + category);
    }

    // Getter methods to access private fields
    public double getBMIValue() {
        return bmiValue;
    }

    public String getCategory() {
        return category;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public String getUnitType() {
        return unitType;
    }

    // Return BMI rounded to 2 decimal places for display
    public String getFormattedBMI() {
        return String.format("%.2f", bmiValue);
    }

    // Return appropriate unit label based on system used
    public String getUnitLabel() {
        return unitType.equalsIgnoreCase("English") ? "lbs/inches" : "kg/meters";
    }
}