
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Sistema {

	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {

		TelaDeCadastro cadastro = new TelaDeCadastro();
		
		cadastro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cadastro.setLocation(300, 300);
		cadastro.setSize(500, 150);      // set dimensions of window
		cadastro.setVisible(true);
		JFrame frame = new JFrame();
		
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
