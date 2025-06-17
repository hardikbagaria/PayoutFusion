package extras;

import java.io.File;
import java.sql.SQLException;

import database.Processes;

public class FileDeleter {
    
    // Method to delete the file based on the provided file path
    public static void deleteFile(int BillNo) throws ClassNotFoundException, SQLException {
    	String name = Processes.getName(BillNo);
    	String dest = "C:\\Users\\hardik\\Desktop\\bills\\" + BillNo + " " + name + ".pdf";
    	String dest2 = "C:\\Users\\hardik\\Desktop\\bills\\" + BillNo + " " + name + " (duplicate).pdf";
        File file = new File(dest);
        File file2 = new File(dest2);
        if (file.exists() && file2.exists()) {
            file.delete();
            file2.delete();
        }
    }
}
