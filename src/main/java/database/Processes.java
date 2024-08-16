package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class Processes {
	
    public static ArrayList<String> Names() throws ClassNotFoundException, SQLException {
        ArrayList<String> stringList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT Name FROM partydetails";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            stringList.add(rs.getString("Name"));
        }
        rs.close();
        s.close();
        c.close();
        return stringList;
    }
    public static ArrayList<String> BillNo() throws ClassNotFoundException, SQLException {
        ArrayList<String> stringList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "Select BillNo from billstable;";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            stringList.add(rs.getString("BillNo"));
        }
        rs.close();
        s.close();
        c.close();
        return stringList;
    }
    public static ArrayList<String> Items() throws ClassNotFoundException, SQLException {
        ArrayList<String> stringList = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT Item FROM items";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        while (rs.next()) {
            stringList.add(rs.getString("Item"));
        }
        rs.close();
        s.close();
        c.close();
        return stringList;
    }
    public static String getDetail(String detail, String Name) throws ClassNotFoundException, SQLException {
        String result = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT " + detail + " FROM partydetails WHERE Name=?";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setString(1, Name);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            result = rs.getString(detail);
        }
        rs.close();
        preparedStatement.close();
        c.close();
        return result;
    }
    public static String getVDetails(int BillNo) throws ClassNotFoundException, SQLException {
        String result = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT VehicleDetails FROM billstable WHERE BillNo=?";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setInt(1, BillNo);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            result = rs.getString("VehicleDetails");
        }
        rs.close();
        preparedStatement.close();
        c.close();
        return result;
    }
    public static String getDate(int Number) throws ClassNotFoundException, SQLException {
        String result = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT Date FROM billstable WHERE BillNo=?";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setInt(1, Number);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            result = rs.getString("Date");
        }
        rs.close();
        preparedStatement.close();
        c.close();
        return result;
    }
    public static String getName(int Number) throws ClassNotFoundException, SQLException {
        String result = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT PartyName FROM billstable WHERE BillNo=?";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setInt(1, Number);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            result = rs.getString("PartyName");
        }
        rs.close();
        preparedStatement.close();
        c.close();
        return result;
    }
    public static int getBillNo() throws ClassNotFoundException, SQLException {
        int result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "SELECT MAX(BillNo) FROM billstable;";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
            result = rs.getInt("MAX(BillNo)");
        }
        rs.close();
        s.close();
        c.close();
        return result+1;
    }
    public static String getAddress1(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("Address1", Name);
    }
    public static String getAddress2(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("Address2", Name);
    }
    public static String getAddress3(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("Address3", Name);
    }
    public static String getGST(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("GST", Name);
    }

    public static String getCntPerson(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("CntPerson", Name);
    }

    public static String getPhoneNo(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("PhoneNo", Name);
    }

    public static String getEmail(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("Email", Name);
    }

    public static String getDestination(String Name) throws ClassNotFoundException, SQLException {
        return getDetail("Destination", Name);
    }
    public static void createBill(int billNo, int srNo, String itemName, Double quantity, Double rate, Double amount) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
        String sql = "INSERT INTO itemstable(`BillNo`,`SrNo`,`ItemName`,`Quantity`,`Rate`,`Amount`) VALUES(?,?,?,?,?,?);";
        PreparedStatement ps = c.prepareStatement(sql);
    	ps.setInt(1, billNo);
    	ps.setInt(2, srNo);
    	ps.setString(3, itemName);
    	ps.setDouble(4, quantity);
    	ps.setDouble(5, rate);
    	ps.setDouble(6, amount);
    	ps.executeUpdate();
    	ps.close();
    	c.close();
    }
    public static void cBill(int billNo, String pName, String date, String vehicleDetails, Double TotalValue, Double GST, Double Transportation, Double GrandTotal) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/payoutfusion", "root", "root");
    	String sql = "INSERT INTO billstable(`BillNo`,`PartyName`,`Date`,`VehicleDetails`,`TotalValue`,`gst`,`Transportation`,`grandTotal`) VALUES(?,?,?,?,?,?,?,?);";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, billNo);
        ps.setString(2, pName);
        ps.setString(3,date);
        ps.setString(4, vehicleDetails);
        ps.setDouble(5, TotalValue);
        ps.setDouble(6, GST);
        ps.setDouble(7, Transportation);
        ps.setDouble(8, GrandTotal);
        ps.executeUpdate();
        ps.close();
        c.close();
    }
}
  