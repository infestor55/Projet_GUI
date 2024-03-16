package Services;

import Models.Coach;
import java.sql.*;
import java.util.List;

public class ServiceCoach implements IService<Coach> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Coach coach) throws SQLException {
        String userName = coach.getUserNameDB();
        String userEmail = coach.getUserEmailDB();
        double salary = coach.getSalary();
        String sport = coach.getSport();
        Date schedule = (Date) coach.getSchedule();
        createCoach(userName, userEmail, salary, sport, schedule);
    }

    @Override
    public void delete(Coach coach) throws SQLException {
        String userName = coach.getUserNameDB();
        deleteCoach(userName);
    }

    @Override
    public void update(Coach coach) throws SQLException {
        String userName = coach.getUserNameDB();
        double newSalary = coach.getSalary();
        String newSport = coach.getSport();
        Date newSchedule = (Date) coach.getSchedule();
        updateCoachInfo(userName, newSalary, newSport, newSchedule);
    }

    @Override
    public List<Coach> readAll() throws SQLException {
        readCoaches();
        return null; // Change this to return a list of Coaches if needed
    }

    @Override
    public Coach findbyId(int id) throws SQLException {
        // Implement this method if needed
        return null;
    }

    private static void createCoach(String userName, String userEmail, double salary, String sport, Date schedule) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            // Get UserID from Users table based on UserNameDB
            String getUserIdQuery = "SELECT UserID FROM Users WHERE UserNameDB = ?";
            PreparedStatement getUserIdStatement = con.prepareStatement(getUserIdQuery);
            getUserIdStatement.setString(1, userName);
            ResultSet userIdResult = getUserIdStatement.executeQuery();

            int userId = -1; // Default value
            if (userIdResult.next()) {
                userId = userIdResult.getInt("UserID");
            } else {
                System.err.println("User with username " + userName + " does not exist.");
                return;
            }

            // Insert coach with obtained UserID
            String insertQuery = "INSERT INTO Coach (UserID, Salary, Sport, Schedule) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setInt(1, userId);
            pst.setDouble(2, salary);
            pst.setString(3, sport);
            pst.setTimestamp(4, new Timestamp(schedule.getTime()));
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Coach added successfully");
            } else {
                System.out.println("Failed to add coach");
            }
        } catch (SQLException e) {
            System.err.println("Error adding coach: " + e.getMessage());
        }
    }


    private static void readCoaches() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Coach";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("Coach ID: " + rs.getInt("UserID") + ", Salary: " + rs.getDouble("Salary") + ", Sport: " + rs.getString("Sport") + ", Schedule: " + rs.getTimestamp("Schedule"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading coaches: " + e.getMessage());
        }
    }

    private static void updateCoachInfo(String userName, double newSalary, String newSport, Date newSchedule) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Coach SET Salary = ?, Sport = ?, Schedule = ? WHERE UserID = (SELECT UserID FROM Users WHERE UserNameDB = ?)";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setDouble(1, newSalary);
            pst.setString(2, newSport);
            pst.setTimestamp(3, new Timestamp(newSchedule.getTime()));
            pst.setString(4, userName);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Coach information updated successfully");
            } else {
                System.out.println("No coach found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating coach information: " + e.getMessage());
        }
    }

    private static void deleteCoach(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Coach WHERE UserID = (SELECT UserID FROM Users WHERE UserNameDB = ?)";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Coach deleted successfully");
            } else {
                System.out.println("No coach found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting coach: " + e.getMessage());
        }
    }
}
