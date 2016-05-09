package sistema;

import telas.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


public class Sistema {

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
		
		//MenuSistema menu = new MenuSistema();
		//menu.start();
		
		//TelaDeCadastro cd = new TelaDeCadastro();
		//cd.start();
		
		TelaDeArquivo arq = new TelaDeArquivo();
		arq.start();
		
		/*
		TestSqlite test = new TestSqlite();		
		
		try {
			test.createDatabase();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LoginSistema system = new LoginSistema();
		system.systemInit();
		*/
	}
}
