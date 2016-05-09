package telas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.swing.*;

import sistema.Banco;

public class LoginSistema {

	private String login;
	private String password;

	private JLabel labelLogin = new JLabel("Digite seu login");
	private JTextField jtf = new JTextField();

	private JLabel labelPsw = new JLabel("Digite sua senha:");
	private JPasswordField jpf = new JPasswordField();

	public void systemInit()
	{
		JOptionPane.showConfirmDialog(null,
				new Object[]{labelLogin, jtf}, "Login:",
				JOptionPane.OK_CANCEL_OPTION); 
		login = jtf.getText();

		if (login == null || login.equals(""))
		{
			JOptionPane.showMessageDialog(null, "Você não inseriu seu login.");
		}
		else {

			// create a database connection
			Connection connection = null;
			try{
				connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);  // set timeout to 30 sec.

				ResultSet resultSet = statement.executeQuery("SELECT * from usuario where login = '"+login+"'");
				if(resultSet.next()) // false se o login n existir na tabela
				{
					String user_login = resultSet.getString("login");
					int user_id = resultSet.getInt("id");
					int bloqueado = resultSet.getInt("bloqueado");
					if(bloqueado==1)
					{
						JOptionPane.showMessageDialog(null, "Usuário bloqueado.");
					}
					else
					{
						// iterate & read the result set
						System.out.println("name = " + user_login);
						System.out.println("id = " + user_id);
	
						VirtualKeyboard typingTutor = new VirtualKeyboard(user_login);   
	
						typingTutor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						typingTutor.setLocation(300, 300);
						typingTutor.setSize(400, 200);    
						typingTutor.setVisible(true);
						JFrame frame = new JFrame();
					}
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Usuário não cadastrado.");
				}
			}
			catch(SQLException e){  System.err.println(e.getMessage()); }       
			finally {  
				try {
					if(connection != null)
						connection.close();
				}
				catch(SQLException e) {  // Use SQLException class instead.          
					System.err.println(e); 
				}
			}

		}
	}

}
