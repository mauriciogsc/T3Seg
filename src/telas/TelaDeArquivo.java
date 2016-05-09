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

	private ResultSet currentUser;

	public TelaDeArquivo(ResultSet currentUser)
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

			System.out.println("aqr");
			int returnVal = fc.showOpenDialog(TelaDeArquivo.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					if (VerificaChavePrivada.VerificaChave(file, phrase, currentUser.getString("login"))){

						// entrar no sistema
						Connection connection = null;
						try
						{
							// create a database connection
							connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

							Statement statement = connection.createStatement();
							statement.setQueryTimeout(30);  // set timeout to 30 sec.

							if(currentUser.next())
							{
								String acessos = Integer.toString(currentUser.getInt("totalDeAcessos")+1);
								statement.executeUpdate("UPDATE usuario SET tentativas= '"+acessos+"' where login='"+currentUser.getString("login")+"'");
								this.setVisible(false);
								dispose();

								MenuSistema menu = new MenuSistema(currentUser);
								menu.start();
							}
						}
						catch(Exception ex)
						{

						}
					}
					else
					{
						System.out.println("NOT OK VERIFICACAO");
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
