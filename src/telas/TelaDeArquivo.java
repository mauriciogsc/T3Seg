package telas;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import sistema.VerificaChavePrivada;

public class TelaDeArquivo extends JFrame implements ActionListener{

	private JLabel secretPhrase;
	private String phrase;

	private JTextField jtf_sp;
	private JFileChooser fc;
	JTextArea filePath;
	private JButton fc_button;

	private String currentUser;
	private ResultSet user_bd;

	public TelaDeArquivo(String currentUser)
	{
		super("Pegar Arquivo");

		this.currentUser = currentUser;

		setLayout(new GridLayout(0,2));

		secretPhrase = new JLabel("Frase Secreta:");
		jtf_sp = new JTextField(20);

		fc = new JFileChooser();

		filePath = new JTextArea();
		fc_button = new JButton("Pegar Arquivo");

		fc_button.addActionListener(this);

		this.add(secretPhrase);
		this.add(jtf_sp);
		this.add(new JLabel(" "));
		this.add(new JLabel(" "));
		this.add(filePath);
		this.add(fc_button);

	}

	public void start()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 300);
		this.setSize(500, 120);      // set dimensions of window
		this.setVisible(true);
		JFrame frame = new JFrame();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		String button = arg0.getActionCommand();
		phrase = jtf_sp.getText();

		if (button.equals("Pegar Arquivo"))
		{

			int returnVal = fc.showOpenDialog(TelaDeArquivo.this);
			String login_user;

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				// entrar no sistema
				Connection connection = null;
				try
				{
					// create a database connection
					connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

					Statement statement = connection.createStatement();
					statement.setQueryTimeout(30);  // set timeout to 30 sec.

					user_bd = statement.executeQuery("SELECT * from usuario where login = '"+currentUser+"'");
					if(user_bd.next())
					{
						login_user = user_bd.getString("login");
						if (VerificaChavePrivada.VerificaChave(file, phrase, login_user)){
							System.out.println("OK VERIFICACAO");

							String acessos = Integer.toString(user_bd.getInt("totalDeAcessos")+1);
							statement.executeUpdate("UPDATE usuario SET totalDeAcessos= '"+acessos+"',tentativas=0 where login='"+login_user+"'");
							this.setVisible(false);
							dispose();

							MenuSistema menu = new MenuSistema(login_user);
							menu.start();
							
							return;

						}
						else
						{
							System.out.println("NOT OK VERIFICACAO");
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				filePath.append(file.getName() + "." + "\n");
			} else {
				filePath.append("Open command cancelled by user." + "\n");
			}

		}
	}

}
