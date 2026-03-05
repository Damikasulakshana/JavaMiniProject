import javax.swing.*;
import java.awt.*;

public class BMIInput extends JPanel {
    // UI components for user input
    private JRadioButton radioEnglish, radioMetrics;  // Unit  selection
    private JTextField txtWeight, txtHeight;           // Input fields for weight/height
    private JButton btnCalculate, btnClear;            // Action buttons

    // Color constants for consistent styling
    private final Color PURPLE_TEXT = new Color(128, 0, 128);
    private final Color LIGHT_GRAY_BG = new Color(255, 255, 255);
    private final Color BLACK_BTN_BG = Color.BLACK;
    private final Color WHITE_TEXT = Color.BLUE;

    // Constructor
    public BMIInput() {
        setLayout(null);
        setBackground(LIGHT_GRAY_BG);
        initializeComponents();
    }


    private void initializeComponents() {

        JLabel lblTitle = new JLabel("BMI CALCULATOR", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(100, 20, 280, 40);
        add(lblTitle);


        JLabel lblUnit = new JLabel("Enter Unit type");
        lblUnit.setFont(new Font("Arial", Font.BOLD, 16));
        lblUnit.setForeground(PURPLE_TEXT);
        lblUnit.setBounds(40, 80, 150, 30);
        add(lblUnit);

        ButtonGroup unitGroup = new ButtonGroup();
        radioEnglish = new JRadioButton("English");
        radioEnglish.setBackground(LIGHT_GRAY_BG);
        radioEnglish.setBounds(250, 80, 100, 30);
        radioEnglish.setSelected(true);  // Default selection

        radioMetrics = new JRadioButton("Metrics");
        radioMetrics.setBackground(LIGHT_GRAY_BG);
        radioMetrics.setBounds(250, 110, 100, 30);

        unitGroup.add(radioEnglish);
        unitGroup.add(radioMetrics);
        add(radioEnglish);
        add(radioMetrics);

        // Weight input field with label
        JLabel lblWeight = new JLabel("Weight");
        lblWeight.setFont(new Font("Arial", Font.BOLD, 16));
        lblWeight.setForeground(PURPLE_TEXT);
        lblWeight.setBounds(40, 160, 100, 30);
        add(lblWeight);
        txtWeight = createTextField("Enter your Weight", 200, 160);
        add(txtWeight);

        // Height input field with label
        JLabel lblHeight = new JLabel("Height");
        lblHeight.setFont(new Font("Arial", Font.BOLD, 16));
        lblHeight.setForeground(PURPLE_TEXT);
        lblHeight.setBounds(40, 220, 100, 30);
        add(lblHeight);
        txtHeight = createTextField("Enter your Height", 200, 220);
        add(txtHeight);

        // Create and add Calculate/Clear buttons
        btnCalculate = createButton("Calculate", 80, 320);
        btnClear = createButton("Clear", 280, 320);
        add(btnCalculate);
        add(btnClear);
    }

    // Helper: creates a text field with placeholder text and focus behavior
    private JTextField createTextField(String placeholder, int x, int y) {
        JTextField textField = new JTextField(placeholder);
        textField.setForeground(Color.GRAY);   // Placeholder color
        textField.setBackground(BLACK_BTN_BG);
        textField.setForeground(Color.WHITE);  // Input text color
        textField.setBounds(x, y, 200, 40);

        // Handle placeholder show/hide on focus
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        return textField;
    }

    // Helper: creates a styled button with consistent appearance
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBackground(BLACK_BTN_BG);
        button.setForeground(WHITE_TEXT);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBounds(x, y, 120, 40);
        return button;
    }

    // Get user input values (call after validation)
    public double getUserWeight() {
        return Double.parseDouble(txtWeight.getText());
    }

    public double getUserHeight() {
        return Double.parseDouble(txtHeight.getText());
    }

    // Return selected unit system
    public String getUnitType() {
        return radioEnglish.isSelected() ? "English" : "Metrics";
    }

    // Quick check: is English unit selected?
    public boolean isEnglishUnit() {
        return radioEnglish.isSelected();
    }

    // Getters for buttons (to attach action listeners externally)
    public JButton getBtnCalculate() { return btnCalculate; }
    public JButton getBtnClear() { return btnClear; }

    // Getters for text fields (for external validation or styling if needed)
    public JTextField getTxtWeight() { return txtWeight; }
    public JTextField getTxtHeight() { return txtHeight; }

    // Reset all inputs to default/placeholder state
    public void clearInputs() {
        txtWeight.setText("Enter your Weight");
        txtWeight.setForeground(Color.GRAY);
        txtHeight.setText("Enter your Height");
        txtHeight.setForeground(Color.GRAY);
        radioEnglish.setSelected(true);  // Reset to default unit
    }

    // Validate that inputs are numeric and not placeholder text
    public boolean isValidInput() {
        try {
            String wText = txtWeight.getText();
            String hText = txtHeight.getText();

            // Check if user left placeholder text
            if (wText.equals("Enter your Weight") || hText.equals("Enter your Height")) {
                return false;
            }

            // Try parsing to catch non-numeric input
            Double.parseDouble(wText);
            Double.parseDouble(hText);
            return true;
        } catch (NumberFormatException e) {
            return false;  // Invalid number format
        }
    }
}