package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.github.lgooddatepicker.components.DatePicker;

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
    public static ArrayList<String> BillParty() throws ClassNotFoundException, SQLException {
        ArrayList<String> stringList = new ArrayList<>();
        Connection c = getConnection();
        String sql = "SELECT BillNo,PartyName FROM billstable;";
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery(sql);
        
        while (rs.next()) {
            stringList.add(rs.getString("BillNo") + " " + rs.getString("PartyName"));
        }
        
        rs.close();
        s.close();
        c.close();
        
        // Sort the stringList based on the numeric BillNo part (descending)
        Collections.sort(stringList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                // Extract BillNo part (assumes the BillNo is the first part of the string)
                int billNo1 = Integer.parseInt(s1.split(" ")[0]);
                int billNo2 = Integer.parseInt(s2.split(" ")[0]);
                
                // Sort in descending order based on BillNo
                return Integer.compare(billNo2, billNo1);  // Reverse order for descending
            }
        });
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
    public static String getRoundOff(int BillNo) throws ClassNotFoundException, SQLException {
    	return getDetailByBillNo("roundoff", BillNo);
    }
    public static String getTotalQuantity(int BillNo) throws ClassNotFoundException, SQLException {
    	return getDetailByBillNo("totalQuantity", BillNo);
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

    public static void cBill(int billNo, String pName, String date, String vehicleDetails, Double TotalValue, Double GST, Double Transportation, Double GrandTotal,Double totalQuantity, Double roundoff) throws ClassNotFoundException, SQLException {
        Connection c = getConnection();
        String sql = "INSERT INTO billstable(BillNo, PartyName, Date, VehicleDetails, TotalValue, gst, Transportation, grandTotal,totalQuantity,roundoff) VALUES(?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, billNo);
        ps.setString(2, pName);
        ps.setString(3, date);
        ps.setString(4, vehicleDetails);
        ps.setDouble(5, TotalValue);
        ps.setDouble(6, GST);
        ps.setDouble(7, Transportation);
        ps.setDouble(8, GrandTotal);
        ps.setDouble(9, totalQuantity);
        ps.setDouble(10, roundoff);
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
        ps.close();
        String sql1 = "DELETE FROM billstable WHERE BillNo=?";
        PreparedStatement ps1 = c.prepareStatement(sql1);
        ps1.setInt(1, billNo);
        ps1.executeUpdate();
        ps1.close();
        c.close();
	}
	public static void markPayment(String partyName, String date, int amount, String remarks) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		String sql = "INSERT INTO mark_payment VALUES(?,?,?,?);";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, partyName);
		ps.setString(2, date);
		ps.setInt(3, amount);
		ps.setString(4, remarks);
		ps.executeUpdate();
		ps.close();
		c.close();
	}
	public static ResultSet viewLedger(String name, String fromdate, String todate) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    String sql = "SELECT BillNo, Date, grandTotal FROM payoutfusion.billstable WHERE STR_TO_DATE(Date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') AND PartyName = ?;";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, fromdate);
	    ps.setString(2, todate);
	    ps.setString(3, name);
	    return ps.executeQuery();
	}
	public static ResultSet viewPayments(String name, String fromdate, String todate) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    String sql = "SELECT date, amountPaid, remarks FROM payoutfusion.mark_payment WHERE STR_TO_DATE(date, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y') AND partyName = ?;";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, fromdate);
	    ps.setString(2, todate);
	    ps.setString(3, name);
	    return ps.executeQuery();
	}
	public int getHSN(String ItemName) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    String sql = "SELECT HSN FROM payoutfusion.items WHERE Item = ?;";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, ItemName);
	    ResultSet rs = ps.executeQuery();
	    
	    int HSN = 0;
	    if(rs.next()) {
	        HSN = rs.getInt("HSN"); 
	    }
	    rs.close();
	    ps.close();
	    c.close();
	    
	    return HSN;
	}
	public static void addItem(String itemName, int HSNCode, String Per) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		String sql = "INSERT INTO `items`(`Item`,`HSN`,`per`) VALUES (?,?,?);";
		PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, itemName);
        ps.setInt(2, HSNCode);
        ps.setString(3, Per);
        ps.executeUpdate();
	}

	public String getPer(String ItemName) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    String sql = "SELECT per FROM payoutfusion.items WHERE Item = ?;";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, ItemName);
	    ResultSet rs = ps.executeQuery();
	    
	    String per = "";
	    if(rs.next()) {
	        per = rs.getString("per");
	    }
	    rs.close();
	    ps.close();
	    c.close();
	    
	    return per;
	}

	public static String getOAmt(String name) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    String sql = "SELECT SUM(grandTotal) FROM payoutfusion.billstable WHERE PartyName = ?";
	    String sql1 = "SELECT SUM(amountPaid) FROM payoutfusion.mark_payment WHERE partyName = ?";

	    double totalBill = 0, totalPaid = 0;

	    try (PreparedStatement ps = c.prepareStatement(sql)) {
	        ps.setString(1, name);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            totalBill = rs.getDouble(1);
	        }
	    }

	    try (PreparedStatement ps1 = c.prepareStatement(sql1)) {
	        ps1.setString(1, name);
	        ResultSet rs1 = ps1.executeQuery();
	        if (rs1.next()) {
	            totalPaid = rs1.getDouble(1);
	        }
	    }

	    double outstandingAmount = totalBill - totalPaid;
	    return (outstandingAmount % 1 == 0) ? String.valueOf((int) outstandingAmount) : String.valueOf(outstandingAmount);
	}
	
	public static String getPrevAmt(String name, String fromDate) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    
	    // Query to get total billed amount before the given date
	    String sqlBills = "SELECT COALESCE(SUM(grandTotal), 0) FROM payoutfusion.billstable " +
	                      "WHERE STR_TO_DATE(date, '%d/%m/%Y') < STR_TO_DATE(?, '%d/%m/%Y') " +
	                      "AND partyName = ?;";
	    
	    // Query to get total amount paid before the given date
	    String sqlPayments = "SELECT COALESCE(SUM(amountPaid), 0) FROM payoutfusion.mark_payment " +
	                         "WHERE STR_TO_DATE(date, '%d/%m/%Y') < STR_TO_DATE(?, '%d/%m/%Y') " +
	                         "AND partyName = ?;";
	    
	    PreparedStatement psBills = c.prepareStatement(sqlBills);
	    psBills.setString(1, fromDate);
	    psBills.setString(2, name);
	    
	    PreparedStatement psPayments = c.prepareStatement(sqlPayments);
	    psPayments.setString(1, fromDate);
	    psPayments.setString(2, name);
	    
	    ResultSet rsBills = psBills.executeQuery();
	    ResultSet rsPayments = psPayments.executeQuery();
	    
	    double totalBills = 0, totalPayments = 0;
	    
	    if (rsBills.next()) {
	        totalBills = rsBills.getDouble(1);
	    }
	    if (rsPayments.next()) {
	        totalPayments = rsPayments.getDouble(1);
	    }
	    
	    double pendingAmount = totalBills - totalPayments;
	    
	    // Closing resources
	    rsBills.close();
	    rsPayments.close();
	    psBills.close();
	    psPayments.close();
	    c.close();
	    
	    return String.valueOf(pendingAmount);
	}

	public static boolean addPParty(String nameFieldData) throws ClassNotFoundException, SQLException {
	    Connection c = getConnection();
	    
	    // Check if the party already exists
	    String checkSql = "SELECT COUNT(*) FROM `purchaseparty` WHERE `name` = ?";
	    PreparedStatement checkPs = c.prepareStatement(checkSql);
	    checkPs.setString(1, nameFieldData);
	    ResultSet rs = checkPs.executeQuery();
	    rs.next(); // Move cursor to the first row
	    boolean exists = rs.getInt(1) > 0;
	    rs.close();
	    checkPs.close();
	    if (exists) {
	        c.close();
	        return false; // Party already exists
	    }
	    // If not exists, insert new party
	    String sql = "INSERT INTO `purchaseparty`(`name`) VALUES(?);";
	    PreparedStatement ps = c.prepareStatement(sql);
	    ps.setString(1, nameFieldData);
	    ps.executeUpdate();
	    // Cleanup resources
	    ps.close();
	    c.close();
	    return true; // Successfully added
	}
	public static ArrayList<String> getPParty() throws ClassNotFoundException, SQLException {
	    ArrayList<String> partyNames = new ArrayList<>();
	    
	    // Establish database connection
	    Connection c = getConnection();
	    
	    // Define SQL query
	    String query = "SELECT name FROM payoutfusion.purchaseparty";
	    
	    // Execute query
	    try (PreparedStatement ps = c.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {
	        
	        // Iterate through results and add names to the list
	        while (rs.next()) {
	            partyNames.add(rs.getString("name"));
	        }
	    }   
	    return partyNames;
	}
}
