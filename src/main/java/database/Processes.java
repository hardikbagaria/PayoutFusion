package database;

import java.sql.*;
import java.util.ArrayList;

public class Processes {

    // Utility method to establish a connection
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/PayoutFusion", "root", "root");
    }

    public static ArrayList<String> Names() throws ClassNotFoundException, SQLException {
        ArrayList<String> stringList = new ArrayList<>();
        Connection c = getConnection();
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
        Connection c = getConnection();
        String sql = "SELECT BillNo FROM billstable;";
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
        Connection c = getConnection();
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

    // Generalized method to fetch a detail by name
    public static String getDetail(String detail, String Name) throws ClassNotFoundException, SQLException {
        String result = null;
        Connection c = getConnection();
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
        return getDetailByBillNo("VehicleDetails", BillNo);
    }

    public static String getDate(int BillNo) throws ClassNotFoundException, SQLException {
        return getDetailByBillNo("Date", BillNo);
    }

    public static String getName(int BillNo) throws ClassNotFoundException, SQLException {
        return getDetailByBillNo("PartyName", BillNo);
    }
    public static String getTDetails(int BillNo) throws ClassNotFoundException, SQLException {
        return getDetailByBillNo("VehicleDetails", BillNo);
    }
    public static String getTotalValue(int BillNo) throws ClassNotFoundException, SQLException {
    	return getDetailByBillNo("TotalValue", BillNo);
    }
    public static String getGSTValue(int BillNo) throws ClassNotFoundException, SQLException {
    	return getDetailByBillNo("gst", BillNo);
    }
    public static String getTValue(int BillNo) throws ClassNotFoundException, SQLException {
    	return getDetailByBillNo("Transportation", BillNo);
    }
    public static String getGrandTotalValue(int BillNo) throws ClassNotFoundException, SQLException {
    	return getDetailByBillNo("grandTotal", BillNo);
    }

    private static String getDetailByBillNo(String detail, int BillNo) throws ClassNotFoundException, SQLException {
        String result = null;
        Connection c = getConnection();
        String sql = "SELECT " + detail + " FROM billstable WHERE BillNo=?";
        PreparedStatement preparedStatement = c.prepareStatement(sql);
        preparedStatement.setInt(1, BillNo);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            result = rs.getString(detail);
        }
        rs.close();
        preparedStatement.close();
        c.close();
        return result;
    }

    public static int getBillNo() throws ClassNotFoundException, SQLException {
        int result = 0;
        Connection c = getConnection();
        String sql = "SELECT MAX(BillNo) FROM billstable;";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        if (rs.next()) {
            result = rs.getInt("MAX(BillNo)");
        }
        rs.close();
        s.close();
        c.close();
        return result + 1;
    }

    // Methods for creating bills and items
    public static void createBill(int billNo, int srNo, String itemName, Double quantity, Double rate, Double amount) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        String sql = "INSERT INTO itemstable(BillNo, SrNo, ItemName, Quantity, Rate, Amount) VALUES(?,?,?,?,?,?);";
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
        Connection c = getConnection();
        String sql = "INSERT INTO billstable(BillNo, PartyName, Date, VehicleDetails, TotalValue, gst, Transportation, grandTotal) VALUES(?,?,?,?,?,?,?,?);";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, billNo);
        ps.setString(2, pName);
        ps.setString(3, date);
        ps.setString(4, vehicleDetails);
        ps.setDouble(5, TotalValue);
        ps.setDouble(6, GST);
        ps.setDouble(7, Transportation);
        ps.setDouble(8, GrandTotal);
        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public static ResultSet resultSet(int billNo) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        String sql = "SELECT SrNo, ItemName, Quantity, Rate, Amount FROM itemstable WHERE BillNo=?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, billNo);
        return ps.executeQuery();
    }
    // Additional helper methods using getDetail
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
    public static void updateParty(String name, String address1FieldData, String address2FieldData,String address3FieldData, String gstFieldData, String cntPersonFieldData, String phoneNoFieldData,String emailFieldData, String destinationFieldData) throws ClassNotFoundException, SQLException {
    	Connection c = getConnection();
    	String sql = "UPDATE partydetails SET Address1 = '"+ address1FieldData +"', Address2= '"+ address2FieldData +"', Address3= '"+ address3FieldData +"', GST= '"+ gstFieldData +"', CntPerson= '"+ cntPersonFieldData +"', PhoneNo= '"+ phoneNoFieldData +"', Email= '"+ emailFieldData +"', Destination= '"+ destinationFieldData +"' WHERE Name= '"+ name +"';";
        Statement s = c.createStatement();
        s.executeUpdate(sql);
    }
    public static void delateParty(String Name) throws ClassNotFoundException, SQLException {
    	Connection c = getConnection();
    	String sql = "DELETE FROM  partydetails WHERE Name= '"+ Name +"';";
        Statement s = c.createStatement();
        s.executeUpdate(sql);
    }
    public static void addParty(String Name, String Address1, String Address2, String Address3, String GST, String CntPerson, String phoneNoFieldData, String Email, String Destination) throws ClassNotFoundException, SQLException {
    	Connection c = getConnection();
    	String sql = "INSERT INTO partydetails VALUES('"+ Name +"','"+Address1+"','"+Address2+"','"+Address3+"','"+GST+"','"+CntPerson+"','"+phoneNoFieldData+"','"+Email+"','"+Destination+"');";
        Statement s = c.createStatement();
        s.executeUpdate(sql);
    }
	public static void removeBill(int billNo) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		String sql = "DELETE FROM itemstable WHERE BillNo=?";
		PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, billNo);
        ps.executeUpdate();
        String sql1 = "DELETE FROM billstable WHERE BillNo=?";
        PreparedStatement ps1 = c.prepareStatement(sql1);
        ps1.setInt(1, billNo);
        ps1.executeUpdate();
	}
	
}
