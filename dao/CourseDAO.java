package dao;

import model.Course;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // Get all courses from database
    public List<Course> getAll() throws SQLException {
        List<Course> list = new ArrayList<>();

        // SQL query to join courses with users (lecturers)
        String sql = "SELECT c.id, c.course_code, c.course_name, " +
                     "c.credits, c.lecturer_id, u.full_name " +
                     "FROM courses c " +
                     "LEFT JOIN users u ON c.lecturer_id = u.id " +
                     "ORDER BY c.course_code";

        // Open connection, create statement, execute query
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            // Loop through results
            while (rs.next()) {
                String lname = rs.getString("full_name");

                // Add each course to list
                list.add(new Course(
                    rs.getInt("id"),              
                    rs.getString("course_code"), 
                    rs.getString("course_name"), 
                    rs.getInt("credits"),       
                    rs.getInt("lecturer_id"),   
                    lname != null ? lname : "Not Assigned" 
                ));
            }
        }
        return list; // return all courses
    }

    // Add a new course to database
    public void add(String code, String name, int credits,
                    int lecturerId) throws SQLException {

        String sql = "INSERT INTO courses " +
                     "(course_code,course_name,credits,lecturer_id) " +
                     "VALUES (?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);   
            ps.setString(2, name);   
            ps.setInt(3, credits);   

            // If no lecturer selected, set NULL
            if (lecturerId == 0) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, lecturerId);

            ps.executeUpdate(); // execute insert
        }
    }

    // Update existing course
    public void update(int id, String code, String name, int credits,
                       int lecturerId) throws SQLException {

        String sql = "UPDATE courses SET course_code=?,course_name=?," +
                     "credits=?,lecturer_id=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);   
            ps.setString(2, name);  
            ps.setInt(3, credits);   

            // Handle lecturer (NULL if not assigned)
            if (lecturerId == 0) ps.setNull(4, Types.INTEGER);
            else ps.setInt(4, lecturerId);

            ps.setInt(5, id);        // course id to update

            ps.executeUpdate(); // execute update
        }
    }

    // Delete a course by id
    public void delete(int id) throws SQLException {

        String sql = "DELETE FROM courses WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id); // set course id
            ps.executeUpdate(); // execute delete
        }
    }

    // Get all lecturers (used for dropdown list)
    public List<User> getLecturers() throws SQLException {
        return new UserDAO().getByRole("LECTURER");
    }
}