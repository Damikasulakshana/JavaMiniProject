package controller;

// Import required classes
import dao.TimetableDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Timetable;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

// Controller class for managing timetables
public class ManageTimetablesController implements Initializable {

    // Table and columns in UI
    @FXML private TableView<Timetable> timetableTable;
    @FXML private TableColumn<Timetable,String> colDept;
    @FXML private TableColumn<Timetable,String> colCourse;
    @FXML private TableColumn<Timetable,String> colDay;
    @FXML private TableColumn<Timetable,String> colStart;
    @FXML private TableColumn<Timetable,String> colEnd;
    @FXML private TableColumn<Timetable,String> colVenue;
    @FXML private TableColumn<Timetable,String> colNote;

    // Input fields in UI
    @FXML private TextField deptField;
    @FXML private TextField courseField;
    @FXML private ComboBox<String> dayCombo;
    @FXML private TextField startField;
    @FXML private TextField endField;
    @FXML private TextField venueField;
    @FXML private TextField noteField;
    @FXML private Label statusLabel;

    // DAO object for database operations
    private final TimetableDAO timetableDAO = new TimetableDAO();

    // Store selected row
    private Timetable selected;

    // This method runs automatically when UI loads
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Link table columns with Timetable class variables
        colDept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colDay.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Add values to ComboBox (days)
        dayCombo.setItems(FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        ));

        // When a row is selected, fill form fields with that data
        timetableTable.getSelectionModel().selectedItemProperty().addListener((o, old, t) -> {
            if (t == null) return;

            selected = t; // store selected object

            // Set values to fields
            deptField.setText(t.getDept());
            courseField.setText(t.getCourseCode());
            dayCombo.setValue(t.getDayOfWeek());
            startField.setText(t.getStartTime());
            endField.setText(t.getEndTime());
            venueField.setText(t.getVenue());
            noteField.setText(t.getNote() == null ? "" : t.getNote());
        });

        // Load data into table
        loadRows();
    }

    // Load all timetable records from database
    private void loadRows() {
        try {
            timetableTable.setItems(
                FXCollections.observableArrayList(timetableDAO.getAll())
            );
        } catch (SQLException e) {
            showStatus("Failed to load timetables: " + e.getMessage(), true);
        }
    }

    // Clear form for new entry
    @FXML
    public void handleNew() {
        selected = null; // no selected item

        // Clear all fields
        deptField.clear();
        courseField.clear();
        dayCombo.getSelectionModel().clearSelection();
        startField.clear();
        endField.clear();
        venueField.clear();
        noteField.clear();

        statusLabel.setText("");
    }

    // Save or update timetable
    @FXML
    public void handleSave() {

        // Get values from fields
        String dept = deptField.getText().trim();
        String course = courseField.getText().trim().toUpperCase();
        String day = dayCombo.getValue();
        String start = startField.getText().trim();
        String end = endField.getText().trim();
        String venue = venueField.getText().trim();
        String note = noteField.getText().trim();

        // Validate required fields
        if (dept.isEmpty() || course.isEmpty() || day == null
                || start.isEmpty() || end.isEmpty() || venue.isEmpty()) {

            showStatus("Department, course, day, time and venue are required.", true);
            return;
        }

        // Validate time format
        if (!isTime(start) || !isTime(end)) {
            showStatus("Use HH:mm format for start/end time.", true);
            return;
        }

        try {
            if (selected == null) {
                // Add new record
                timetableDAO.add(dept, course, day, start, end, venue, note);
                showStatus("Timetable entry added.", false);
            } else {
                // Update existing record
                timetableDAO.update(selected.getId(), dept, course, day, start, end, venue, note);
                showStatus("Timetable entry updated.", false);
            }

            loadRows();   // reload table
            handleNew();  // clear form

        } catch (SQLException e) {
            showStatus("Error: " + e.getMessage(), true);
        }
    }

    // Delete selected timetable
    @FXML
    public void handleDelete() {

        // Check if something selected
        if (selected == null) {
            showStatus("Select a timetable entry first.", true);
            return;
        }

        // Confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete selected timetable entry?", ButtonType.YES, ButtonType.NO);

        alert.showAndWait().ifPresent(btn -> {
            if (btn != ButtonType.YES) return;

            try {
                // Delete record
                timetableDAO.delete(selected.getId());
                showStatus("Timetable entry deleted.", false);

                loadRows();
                handleNew();

            } catch (SQLException e) {
                showStatus("Error: " + e.getMessage(), true);
            }
        });
    }

    // Check time format HH:mm using regex
    private boolean isTime(String value) {
        return value.matches("^([01]\\d|2[0-3]):[0-5]\\d$");
    }

    // Show message in label (red for error, green for success)
    private void showStatus(String message, boolean error) {
        statusLabel.setText(message);
        statusLabel.setStyle(error ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
    }
}