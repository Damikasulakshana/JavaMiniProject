import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMICalculatorApp extends JFrame {
    private BMIInput inputPanel;
    private BMIOutput outputPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public BMICalculatorApp() {
        setTitle("BMI Calculator App");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(220, 220, 220));

        initializeUI();
        addEventListeners();
    }

    private void initializeUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        inputPanel = new BMIInput();
        outputPanel = new BMIOutput();

        mainPanel.add(inputPanel, "INPUT");
        mainPanel.add(outputPanel, "OUTPUT");

        add(mainPanel);
        cardLayout.show(mainPanel, "INPUT");
    }

    private void addEventListeners() {
        // Calculate button
        inputPanel.getBtnCalculate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        // Clear button
        inputPanel.getBtnClear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputPanel.clearInputs();
            }
        });

        // Back button
        outputPanel.getBtnBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "INPUT");
            }
        });
    }

    private void calculateBMI() {
        if (!inputPanel.isValidInput()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for weight and height.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // ✅ Use the RENAMED methods
            double weight = inputPanel.getUserWeight();
            double height = inputPanel.getUserHeight();
            String unitType = inputPanel.getUnitType();

            // Create calculator object
            BMICalculator bmi = new BMICalculator(weight, height, unitType);

            // Display results
            outputPanel.displayResults(bmi);

            // Switch to output panel
            cardLayout.show(mainPanel, "OUTPUT");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter numbers only.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BMICalculatorApp app = new BMICalculatorApp();
                app.setVisible(true);
            }
        });
    }
}