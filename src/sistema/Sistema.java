package sistema;

import telas.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class Sistema {

	public static void main(String[] args) throws Exception {
		
		Banco test = new Banco();		
		
		try {
			test.createDatabase();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginSistema system = new LoginSistema();
		system.systemInit();
		
	}
}
