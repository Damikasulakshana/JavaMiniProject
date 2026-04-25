package controller;

// Import DAO classes (used to interact with database)
import dao.AttendanceDAO;
import dao.CourseDAO;
import dao.TimetableDAO;
import dao.UserDAO;

// Import model classes (data objects)
import model.AttendanceSummary;
import model.Course;
import model.Medical;
import model.Timetable;
import model.User;

// Session class to get logged-in user
import util.Session;

// JavaFX UI components
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

// Other utilities
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// Controller class for Technical Officer Dashboard
public class TechDashboardController {

    // -------------------- Attendance UI --------------------
    @FXML private ComboBox<String> courseCombo;   
    @FXML private ComboBox<String> studentCombo;  
    @FXML private ComboBox<String> typeCombo;     
    @FXML private ComboBox<String> sessionCombo;  
    @FXML private DatePicker sessionDatePicker;  
    @FXML private CheckBox presentCheck;         
    @FXML private Label attStatusLabel;        

    // Table for attendance summary
    @FXML private TableView<AttendanceSummary> summaryTable;
    @FXML private TableColumn<AttendanceSummary,String> colStudent;
    @FXML private TableColumn<AttendanceSummary,String> colCourse;
    @FXML private TableColumn<AttendanceSummary,Integer> colPresent;
    @FXML private TableColumn<AttendanceSummary,Double> colPct;
    @FXML private TableColumn<AttendanceSummary,Double> colEffPct;
    @FXML private TableColumn<AttendanceSummary,String> colEligible;

    // -------------------- Medical UI --------------------
    @FXML private TextField medStudentField;
    @FXML private DatePicker medFromPicker;
    @FXML private DatePicker medToPicker;
    @FXML private TextField medReasonField;
    @FXML private Label medStatusLabel;

    // Medical table
    @FXML private TableView<Medical> medTable;
    @FXML private TableColumn<Medical,String> colMedStudent;
    @FXML private TableColumn<Medical,String> colMedFrom;
    @FXML private TableColumn<Medical,String> colMedTo;
    @FXML private TableColumn<Medical,String> colMedReason;
    @FXML private TableColumn<Medical,String> colMedStatus;

    // Welcome label
    @FXML private Label welcomeLabel;

    // -------------------- Timetable UI --------------------
    @FXML private Label timetableStatusLabel;
    @FXML private TableView<Timetable> timetableTable;
    @FXML private TableColumn<Timetable,String> colTDay;
    @FXML private TableColumn<Timetable,String> colTStart;
    @FXML private TableColumn<Timetable,String> colTEnd;
    @FXML private TableColumn<Timetable,String> colTCourse;
    @FXML private TableColumn<Timetable,String> colTVenue;
    @FXML private TableColumn<Timetable,String> colTNote;

    // -------------------- Profile UI --------------------
    @FXML private TextField profNameField;
    @FXML private TextField profEmailField;
    @FXML private TextField profPhoneField;
    @FXML private TextField profDeptField;
    @FXML private Label profileStatusLabel;

    // DAO objects (database operations)
    private final AttendanceDAO attDAO = new AttendanceDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final UserDAO userDAO = new UserDAO();
    private final TimetableDAO timetableDAO = new TimetableDAO();

    // Lists to store data
    private List<String> courseCodes = new ArrayList<>();
    private List<String> studentIds = new ArrayList<>();


    // -------------------- Initialize method --------------------
    @FXML
    public void initialize() {

        // Show logged user name
        welcomeLabel.setText("Welcome, " + Session.get().getFullName());

        // Load courses and students
        loadLookups();

        // Set attendance type options
        typeCombo.setItems(FXCollections.observableArrayList("THEORY","PRACTICAL","ALL"));
        typeCombo.setValue("THEORY");

        // Create session numbers (1–15)
        String[] sessions = new String[15];
        for (int i = 0; i < 15; i++) sessions[i] = String.valueOf(i+1);
        sessionCombo.setItems(FXCollections.observableArrayList(sessions));

        // Set today's date
        sessionDatePicker.setValue(LocalDate.now());

        // Link table columns with model properties
        colStudent.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colPresent.setCellValueFactory(new PropertyValueFactory<>("presentSessions"));
        colPct.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        colEffPct.setCellValueFactory(new PropertyValueFactory<>("effectivePct"));
        colEligible.setCellValueFactory(new PropertyValueFactory<>("eligibility"));

        // Medical table columns
        colMedStudent.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colMedFrom.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        colMedTo.setCellValueFactory(new PropertyValueFactory<>("toDate"));
        colMedReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colMedStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Timetable table columns
        colTDay.setCellValueFactory(new PropertyValueFactory<>("dayOfWeek"));
        colTStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colTEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colTCourse.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        colTVenue.setCellValueFactory(new PropertyValueFactory<>("venue"));
        colTNote.setCellValueFactory(new PropertyValueFactory<>("note"));

        // Load profile data
        loadProfile();

        // Load timetable
        handleViewDeptTimetable();
    }


    // -------------------- Load courses & students --------------------
    private void loadLookups() {
        try {
            // Get all courses
            List<Course> courses = courseDAO.getAll();

            // Extract course codes
            courseCodes = courses.stream()
                    .map(Course::getCourseCode)
                    .distinct()
                    .collect(Collectors.toList());

            courseCombo.setItems(FXCollections.observableArrayList(courseCodes));

            // Get all students
            List<User> students = userDAO.getByRole("STUDENT");

            studentIds = students.stream()
                    .map(User::getUsername)
                    .collect(Collectors.toList());

            studentCombo.setItems(FXCollections.observableArrayList(studentIds));

        } catch (Exception e) {
            setStatus(attStatusLabel, "Failed to load: " + e.getMessage(), false);
        }
    }


    // -------------------- Save attendance --------------------
    @FXML
    public void handleSaveAttendance() {
        try {
            // Get user input
            String s = studentCombo.getValue();
            String c = courseCombo.getValue();
            String t = typeCombo.getValue();
            String sn = sessionCombo.getValue();
            LocalDate date = sessionDatePicker.getValue();

            // Validate input
            if (s==null||c==null||sn==null||date==null) {
                setStatus(attStatusLabel,"Fill all fields.",false);
                return;
            }

            // Save to database
            attDAO.saveAttendance(s, c, Integer.parseInt(sn), t, presentCheck.isSelected(), date);

            setStatus(attStatusLabel,"Attendance saved!",true);

        } catch (Exception e) {
            setStatus(attStatusLabel,"Error: "+e.getMessage(),false);
        }
    }


    // -------------------- Save medical --------------------
    @FXML
    public void handleSaveMedical() {
        try {
            String sid = medStudentField.getText().trim();
            String reason = medReasonField.getText().trim();
            LocalDate from = medFromPicker.getValue();
            LocalDate to = medToPicker.getValue();

            // Validation
            if (sid.isEmpty()||from==null||to==null||reason.isEmpty()) {
                setStatus(medStatusLabel,"Fill all fields.",false);
                return;
            }

            // Save to DB
            attDAO.saveMedical(sid, from, to, reason);

            setStatus(medStatusLabel,"Medical saved!",true);

        } catch (Exception e) {
            setStatus(medStatusLabel,"Error: "+e.getMessage(),false);
        }
    }


    // -------------------- Load timetable --------------------
    @FXML
    public void handleViewDeptTimetable() {
        try {
            String dept = Session.get().getDept();

            // Get timetable by department
            timetableTable.setItems(FXCollections.observableArrayList(
                    timetableDAO.getByDept(dept)
            ));

            setStatus(timetableStatusLabel, "Loaded!", true);

        } catch (Exception e) {
            setStatus(timetableStatusLabel, "Error: " + e.getMessage(), false);
        }
    }


    // -------------------- Load profile --------------------
    private void loadProfile() {
        profNameField.setText(Session.get().getFullName());
        profEmailField.setText(Session.get().getEmail());
        profPhoneField.setText(Session.get().getPhone());
        profDeptField.setText(Session.get().getDept());
    }


    // -------------------- Update profile --------------------
    @FXML
    public void handleUpdateProfile() {
        try {
            boolean ok = userDAO.updateStaffProfile(
                    Session.get().getId(),
                    profNameField.getText(),
                    profEmailField.getText(),
                    profPhoneField.getText(),
                    profDeptField.getText()
            );

            if (ok) {
                setStatus(profileStatusLabel, "Updated!", true);
            }

        } catch (Exception e) {
            setStatus(profileStatusLabel, "Error: " + e.getMessage(), false);
        }
    }


    // -------------------- Common status method --------------------
    private void setStatus(Label lbl, String msg, boolean ok) {
        lbl.setText(msg);
        lbl.setStyle(ok ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
    }


    // -------------------- Logout --------------------
    @FXML
    public void handleLogout() {
        try {
            Session.clear();

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();

            // Load login screen
            stage.setScene(new Scene(
                    new FXMLLoader(getClass().getResource("/view/Login.fxml")).load(),
                    480, 360
            ));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}