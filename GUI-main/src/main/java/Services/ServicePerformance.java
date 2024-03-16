package Services;

import Models.Performance;
import java.sql.*;
import java.util.List;

public class ServicePerformance implements IService<Performance> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Performance performance) throws SQLException {
        double runsScored = performance.getRunsScored();
        double ballsFaced = performance.getBallsFaced();
        double fours = performance.getFours();
        double sixes = performance.getSixes();
        double wicketsTaken = performance.getWicketsTaken();
        double ballsBowled = performance.getBallsBowled();
        double runsGave = performance.getRunsGave();
        createPerformance(runsScored, ballsFaced, fours, sixes, wicketsTaken, ballsBowled, runsGave);
    }

    @Override
    public void delete(Performance performance) throws SQLException {
        // int userId = performance.getUserId();
        // deletePerformance(userId);
    }

    @Override
    public void update(Performance performance) throws SQLException {
        double newRunsScored = performance.getRunsScored();
        double newBallsFaced = performance.getBallsFaced();
        double newFours = performance.getFours();
        double newSixes = performance.getSixes();
        double newWicketsTaken = performance.getWicketsTaken();
        double newBallsBowled = performance.getBallsBowled();
        double newRunsGave = performance.getRunsGave();
        updatePerformance(newRunsScored, newBallsFaced, newFours, newSixes, newWicketsTaken, newBallsBowled, newRunsGave);
    }

    @Override
    public List<Performance> readAll() throws SQLException {
        readPerformances();
        return null; // Change this to return a list of Performances if needed
    }

    @Override
    public Performance findbyId(int id) throws SQLException {
        // Implement this method if needed
        return null;
    }

    private static void createPerformance(double runsScored, double ballsFaced, double fours, double sixes, double wicketsTaken, double ballsBowled, double runsGave) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Performance (RunsScored, BallsFaced, Fours, Sixes, WicketsTaken, BallsBowled, RunsGave) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setDouble(1, runsScored);
            pst.setDouble(2, ballsFaced);
            pst.setDouble(3, fours);
            pst.setDouble(4, sixes);
            pst.setDouble(5, wicketsTaken);
            pst.setDouble(6, ballsBowled);
            pst.setDouble(7, runsGave);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Performance added successfully");
            } else {
                System.out.println("Failed to add performance");
            }
        } catch (SQLException e) {
            System.err.println("Error adding performance: " + e.getMessage());
        }
    }

    private static void readPerformances() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Performance";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("Performance ID: " + rs.getInt("PerformanceID") + ", UserID: " + rs.getInt("UserID") + ", Runs Scored: " + rs.getDouble("RunsScored") + ", Balls Faced: " + rs.getDouble("BallsFaced") + ", Fours: " + rs.getDouble("Fours") + ", Sixes: " + rs.getDouble("Sixes") + ", Wickets Taken: " + rs.getDouble("WicketsTaken") + ", Balls Bowled: " + rs.getDouble("BallsBowled") + ", Runs Gave: " + rs.getDouble("RunsGave"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading performances: " + e.getMessage());
        }
    }

    private static void updatePerformance(double newRunsScored, double newBallsFaced, double newFours, double newSixes, double newWicketsTaken, double newBallsBowled, double newRunsGave) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Performance SET RunsScored = ?, BallsFaced = ?, Fours = ?, Sixes = ?, WicketsTaken = ?, BallsBowled = ?, RunsGave = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setDouble(1, newRunsScored);
            pst.setDouble(2, newBallsFaced);
            pst.setDouble(3, newFours);
            pst.setDouble(4, newSixes);
            pst.setDouble(5, newWicketsTaken);
            pst.setDouble(6, newBallsBowled);
            pst.setDouble(7, newRunsGave);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Performance updated successfully");
            } else {
                System.out.println("No performance found for the given user");
            }
        } catch (SQLException e) {
            System.err.println("Error updating performance: " + e.getMessage());
        }
    }

    private static void deletePerformance(int userId) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Performance WHERE UserID = ?";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setInt(1, userId);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Performance deleted successfully");
            } else {
                System.out.println("No performance found for the given user");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting performance: " + e.getMessage());
        }
    }
}
