package Services;

import Models.Users;
import java.sql.*;
import java.util.List;

public class ServiceUsers implements IService<Users> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Users user) throws SQLException {
        String userName = user.getUserNameDB();
        String userEmail = user.getUserEmailDB();
        String role = user.getRole();
        createUser(userName, userEmail, role);
    }

    @Override
    public void delete(Users user) throws SQLException {
        String userName = user.getUserNameDB();
        deleteUser(userName);
    }

    @Override
    public void update(Users user) throws SQLException {
        String userName = user.getUserNameDB();
        String newEmail = user.getUserEmailDB();
        updateUserEmail(userName, newEmail);
    }

    @Override
    public List<Users> readAll() throws SQLException {
        readUsers();
        return null; // Change this to return a list of Users if needed
    }

    @Override
    public Users findbyId(int id) throws SQLException {
        // Implement this method if needed
        return null;
    }

    private static void createUser(String userName, String userEmail, String role) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Users (UserNameDB, UserEmailDB, role) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, userName);
            pst.setString(2, userEmail);
            pst.setString(3, role); // Set the role in the query
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User added successfully");
            } else {
                System.out.println("Failed to add user");
            }
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    private static void deleteUser(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Users WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted successfully");
            } else {
                System.out.println("No user found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    private static void readUsers() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Users";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("UserID") + ", UserName: " + rs.getString("UserNameDB") + ", Email: " + rs.getString("UserEmailDB"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading users: " + e.getMessage());
        }
    }

    private static void updateUserEmail(String userName, String newEmail) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Users SET UserEmailDB = ? WHERE UserNameDB = ?";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setString(1, newEmail);
            pst.setString(2, userName);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User email updated successfully");
            } else {
                System.out.println("No user found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
    }
}
