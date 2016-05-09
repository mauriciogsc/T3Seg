package sistema;

import telas.*;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Sistema {

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
		/*
		MenuSistema menu = new MenuSistema();
		
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setLocation(300, 300);
		menu.setSize(900, 350);      // set dimensions of window
		menu.setVisible(true);
		JFrame frame = new JFrame();
		*/
		TelaDeCadastro cadastro = new TelaDeCadastro();
		cadastro.start();
		
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
