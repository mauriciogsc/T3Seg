
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JOptionPane;


public class Sistema {

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {

		LoginSistema system = new LoginSistema();
		system.systemInit();
		TestSqlite test = new TestSqlite();
		
		try {
			test.createDatabase();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
