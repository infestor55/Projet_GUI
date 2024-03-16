package Services;

import Models.Responsible;
import java.sql.*;
import java.util.List;

public class ServiceResponsible implements IService<Responsible> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Responsible responsible) throws SQLException {
        String userName = responsible.getUserNameDB();
        String userEmail = responsible.getUserEmailDB();
        double finance = responsible.getFinance();
        createResponsible(userName, userEmail, finance);
    }

    @Override
    public void delete(Responsible responsible) throws SQLException {
        String userName = responsible.getUserNameDB();
        deleteResponsible(userName);
    }

    @Override
    public void update(Responsible responsible) throws SQLException {
        String userName = responsible.getUserNameDB();
        double newFinance = responsible.getFinance();
        updateResponsibleFinance(userName, newFinance);
    }

    @Override
    public List<Responsible> readAll() throws SQLException {
        readResponsibles();
        return null; // Change this to return a list of Responsibles if needed
    }

    @Override
    public Responsible findbyId(int id) throws SQLException {
        // Implement this method if needed
        return null;
    }

    private static void createResponsible(String userName, String userEmail, double finance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Responsible (UserID, Finance) VALUES ((SELECT UserID FROM Users WHERE UserNameDB = ?), ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, userName);
            pst.setDouble(2, finance);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Responsible added successfully");
            } else {
                System.out.println("Failed to add responsible");
            }
        } catch (SQLException e) {
            System.err.println("Error adding responsible: " + e.getMessage());
        }
    }

    private static void readResponsibles() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Responsible";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("Responsible ID: " + rs.getInt("UserID") + ", Finance: " + rs.getDouble("Finance"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading responsibles: " + e.getMessage());
        }
    }

    private static void updateResponsibleFinance(String userName, double newFinance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Responsible SET Finance = ? WHERE UserID = (SELECT UserID FROM Users WHERE UserNameDB = ?)";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setDouble(1, newFinance);
            pst.setString(2, userName);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Responsible finance updated successfully");
            } else {
                System.out.println("No responsible found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating responsible: " + e.getMessage());
        }
    }

    private static void deleteResponsible(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Responsible WHERE UserID = (SELECT UserID FROM Users WHERE UserNameDB = ?)";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Responsible deleted successfully");
            } else {
                System.out.println("No responsible found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting responsible: " + e.getMessage());
        }
    }
}
