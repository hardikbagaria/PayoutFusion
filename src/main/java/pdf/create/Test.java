package pdf.create;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Test {

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, SQLException {
		BillGenGST.createBill("451");
		
	}

}
