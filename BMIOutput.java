import javax.swing.*;
import java.awt.*;

public class BMIOutput extends JPanel {
    // displaying results
    private JLabel lblResWeight, lblResHeight, lblResBMI, lblResState;
    private JButton btnBack;  // Button to return to main menu

    // Colors
    private final Color PURPLE_TEXT = new Color(128, 0, 128);
    private final Color LIGHT_GRAY_BG = new Color(220, 220, 220);
    private final Color BLACK_BTN_BG = Color.BLACK;
    private final Color WHITE_TEXT = Color.BLUE;  //

    // Constructor
    public BMIOutput() {
        setLayout(null);
        setBackground(LIGHT_GRAY_BG);
        initializeComponents();    // Build and add all UI elements
    }

    // Create and all labels and buttons
    private void initializeComponents() {
        // Title label
        JLabel lblTitle = new JLabel("RESULT", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(100, 20, 280, 40);
        add(lblTitle);

        // Create result labels
        lblResWeight = createResultLabel("Weight :", 40, 100);
        lblResHeight = createResultLabel("Height :", 40, 150);
        lblResBMI = createResultLabel("Your BMI Value :", 40, 200);
        lblResState = createResultLabel("Your Body State :", 40, 250);

        add(lblResWeight);
        add(lblResHeight);
        add(lblResBMI);
        add(lblResState);

        // Back button
        btnBack = new JButton("Back to main menu");
        btnBack.setBackground(BLACK_BTN_BG);
        btnBack.setForeground(WHITE_TEXT);
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setBounds(150, 350, 180, 40);
        add(btnBack);
    }

    // creates label
    private JLabel createResultLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text + " ");
        lbl.setFont(new Font("Arial", Font.BOLD, 16));
        lbl.setForeground(PURPLE_TEXT);
        lbl.setBounds(x, y, 400, 30);
        return lbl;
    }

    // Update labels
    public void displayResults(BMICalculator bmi) {
        lblResWeight.setText("Weight : " + bmi.getWeight() + " (" + bmi.getUnitLabel() + ")");
        lblResHeight.setText("Height : " + bmi.getHeight());
        lblResBMI.setText("Your BMI Value : " + bmi.getFormattedBMI());
        lblResState.setText("Your Body State : " + bmi.getCategory());
    }

    // Reset all result labels to empty
    public void clearResults() {
        lblResWeight.setText("Weight : ");
        lblResHeight.setText("Height : ");
        lblResBMI.setText("Your BMI Value : ");
        lblResState.setText("Your Body State : ");
    }


    public JButton getBtnBack() {
        return btnBack;
    }

    // Getters for labels
    public JLabel getLblResWeight() { return lblResWeight; }
    public JLabel getLblResHeight() { return lblResHeight; }
    public JLabel getLblResBMI() { return lblResBMI; }
    public JLabel getLblResState() { return lblResState; }
}