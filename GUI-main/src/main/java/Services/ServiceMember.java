package Services;

import Models.Member;
import Models.Performance;
import java.sql.*;
import java.util.List;

public class ServiceMember implements IService<Member> {
    static String url = "jdbc:mysql://localhost:3306/esprit";
    static String login = "root";
    static String pwd = "";

    @Override
    public void add(Member member) throws SQLException {
        String userName = member.getUserNameDB();
        String userEmail = member.getUserEmailDB();
        String membership = member.getMembership();
        int age = member.getAge();
        double height = member.getHeight();
        double weight = member.getWeight();
        Date schedule = (Date) member.getSchedule();
        Performance performance = member.getPerformance();
        createMember(userName, userEmail, membership, age, height, weight, schedule, performance);
    }

    @Override
    public void delete(Member member) throws SQLException {
        String userName = member.getUserNameDB();
        deleteMember(userName);
    }

    @Override
    public void update(Member member) throws SQLException {
        String userName = member.getUserNameDB();
        String newMembership = member.getMembership();
        int newAge = member.getAge();
        double newHeight = member.getHeight();
        double newWeight = member.getWeight();
        Date newSchedule = (Date) member.getSchedule();
        Performance newPerformance = member.getPerformance();
        updateMemberInfo(userName, newMembership, newAge, newHeight, newWeight, newSchedule, newPerformance);
    }

    @Override
    public List<Member> readAll() throws SQLException {
        readMembers();
        return null; // Change this to return a list of Members if needed
    }

    @Override
    public Member findbyId(int id) throws SQLException {
        // Implement this method if needed
        return null;
    }

    private static void createMember(String userName, String userEmail, String membership, int age, double height, double weight, Date schedule, Performance performance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String insertQuery = "INSERT INTO Member (UserID, Membership, Age, Height, Weight, Schedule) VALUES ((SELECT UserID FROM Users WHERE UserNameDB = ?), ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(insertQuery);
            pst.setString(1, userName);
            pst.setString(2, membership);
            pst.setInt(3, age);
            pst.setDouble(4, height);
            pst.setDouble(5, weight);
            pst.setTimestamp(6, new Timestamp(schedule.getTime()));
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Member added successfully");
            } else {
                System.out.println("Failed to add member");
            }
        } catch (SQLException e) {
            System.err.println("Error adding member: " + e.getMessage());
        }
    }

    private static void readMembers() {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            Statement ste = con.createStatement();
            String selectQuery = "SELECT * FROM Member";
            ResultSet rs = ste.executeQuery(selectQuery);
            while (rs.next()) {
                System.out.println("Member ID: " + rs.getInt("UserID") + ", Membership: " + rs.getString("Membership") + ", Age: " + rs.getInt("Age") + ", Height: " + rs.getDouble("Height") + ", Weight: " + rs.getDouble("Weight") + ", Schedule: " + rs.getTimestamp("Schedule"));
            }
        } catch (SQLException e) {
            System.err.println("Error reading members: " + e.getMessage());
        }
    }

    private static void updateMemberInfo(String userName, String newMembership, int newAge, double newHeight, double newWeight, Date newSchedule, Performance newPerformance) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String updateQuery = "UPDATE Member SET Membership = ?, Age = ?, Height = ?, Weight = ?, Schedule = ? WHERE UserID = (SELECT UserID FROM Users WHERE UserNameDB = ?)";
            PreparedStatement pst = con.prepareStatement(updateQuery);
            pst.setString(1, newMembership);
            pst.setInt(2, newAge);
            pst.setDouble(3, newHeight);
            pst.setDouble(4, newWeight);
            pst.setTimestamp(5, new Timestamp(newSchedule.getTime()));
            pst.setString(6, userName);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Member information updated successfully");
            } else {
                System.out.println("No member found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error updating member information: " + e.getMessage());
        }
    }

    private static void deleteMember(String userName) {
        try (Connection con = DriverManager.getConnection(url, login, pwd)) {
            String deleteQuery = "DELETE FROM Member WHERE UserID = (SELECT UserID FROM Users WHERE UserNameDB = ?)";
            PreparedStatement pst = con.prepareStatement(deleteQuery);
            pst.setString(1, userName);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Member deleted successfully");
            } else {
                System.out.println("No member found with the given name");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
        }
    }
}
