
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

// VirtualKeyboard.java

public class VirtualKeyboard extends JFrame implements ActionListener
{

	private JButton b0,b1,b2,b3,b4,Backspace,go;
	private JPasswordField passwordField;
	private JLabel label;
	private String password = new String();
	private String fullPassword = new String();
	private boolean isFreeToType = true;
	private JLabel warning;

	private ResultSet currentUser;

	public VirtualKeyboard(ResultSet user)
	{
		super("Virtual Keyboard");

		setLayout( new FlowLayout() );

		label = new JLabel("Digite sua senha usando o teclado virtual");
		add(label);

		passwordField = new JPasswordField(10);

		passwordField.setEditable(true);
		add(passwordField , BorderLayout.NORTH);


		List<String> lst = this.generateKeys();

		b0 = new JButton( lst.get(0) ); add(b0); b0.addActionListener(this);
		b1 = new JButton( lst.get(1) ); add(b1); b1.addActionListener(this);
		b2 = new JButton( lst.get(2) ); add(b2); b2.addActionListener(this);
		b3 = new JButton( lst.get(3) ); add(b3); b3.addActionListener(this);
		b4 = new JButton( lst.get(4) ); add(b4); b4.addActionListener(this);

		Backspace = new JButton("Backspace"); add(Backspace); Backspace.addActionListener(this);

		go = new JButton("Entrar"); add(go); go.addActionListener(this);
		go.setEnabled(false); // nao entra sem senha

		warning = new JLabel("Sua senha possui uma sequência ou uma repetição. Favor entrar com uma nova senha");
		warning.setForeground(Color.red);
		add(warning); warning.setVisible(false);

		// pegando o usuario
		this.currentUser = user;
	}

	public List<String> generateKeys()
	{
		List<String> keys = new ArrayList<String>();

		List<Integer> numbers = new ArrayList<Integer>();
		List<Integer> randoms = new ArrayList<Integer>();
		for(int i=0; i <10; i++)
			numbers.add(i);

		Random randomGenerator = new Random();

		for(int j=0; j<10; j++)
		{
			int ran = randomGenerator.nextInt(numbers.size());                                                                        
			randoms.add(numbers.get(ran));
			if(j%2!=0)
			{
				keys.add(randoms.get(0) + " ou "+ randoms.get(1));

				System.out.println(randoms.get(0) + " ou "+ randoms.get(1));
				randoms.clear();
			}

			numbers.remove(ran);

		}
		return keys;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String button = e.getActionCommand();	
		boolean isFreeToAdd = true;

		if(button.equals("Backspace"))
		{
			if (!( password.equals("") || password == null))   
			{
				password = (String) password.subSequence(0, (password.length()-1));
				passwordField.setText(password);

				fullPassword = (String) fullPassword.substring(0, (fullPassword.length()-6));
				isFreeToType = true;
			}
		}
		else if (button.equals("Entrar"))
		{		
			// entrar no sistema
			Connection connection = null;
			try
			{
				// create a database connection
				connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

				Statement statement = connection.createStatement();
				statement.setQueryTimeout(30);  // set timeout to 30 sec.

				// pegar salt do usu�rio corrente
				String salt = currentUser.getString("salt");
				String currentPsw = currentUser.getString("senha");

				int size = password.length();
				String currentpsw = new String();
				generateCombinations(0, 0, size, currentpsw, fullPassword, salt, currentpsw);
			}
			catch(SQLException | NoSuchAlgorithmException ex){  System.err.println(ex.getMessage()); }       
			finally {         
				try {
					if(connection != null)
						connection.close();
				}
				catch(SQLException ex) {  // Use SQLException class instead.          
					System.err.println(ex); 
				}
			}

		}
		else if (isFreeToType) // senha menor que 10, eh possivle add mais digitos
		{
			// pegar ultimo e novo digito
			int temp, newDigit;

			// realizo a checagem se nao for o primeiro digito
			if(!password.equals(""))
			{
				temp = Integer.parseInt(password.substring(password.length()-1));
				newDigit = Integer.parseInt(button.substring(5));

				// verificar novo digito - eh diferente do anterior e nao eh sequecia 
				if(newDigit != temp	&& newDigit != (temp - 1) && newDigit != (temp + 1)) isFreeToAdd = true;
				else
				{
					isFreeToAdd = false;
					password = "";
					warning.setVisible(true);
				}
			}
			if (isFreeToAdd == true)
			{
				warning.setVisible(false);

				password+=button.substring(5);
				fullPassword+=button; // pegar o texto todo

				if (password.length() == 8) go.setEnabled(true);

				if (password.length() == 10) isFreeToType = false;
			}
			passwordField.setText(password);
		}
	}

	public void generateCombinations(int i,
			int index,
			int len,
			String senha,
			String s,
			String salt,
			String dbPassword) throws NoSuchAlgorithmException
	{				 
		senha+=s.charAt(5*index+ 6*i);

		if(i==len)
		{
			// senha aqui eh uma combinacao
			senha.concat(salt);

			String hex_senha = "";
			byte[] texto = new byte[senha.length()];

			//transformando o conteudo do arquivo em digest do tipo informado
			MessageDigest mDigest = MessageDigest.getInstance("MD5");

			//criando o digest e salvando
			mDigest.update(texto);
			StringBuffer buf_digest = new StringBuffer();
			byte[] digest = mDigest.digest();

			//transformando o digest em uma string Hexa
			for(int j = 0; j < digest.length; j++) {
				hex_senha = Integer.toHexString(0x0100 + (digest[j] & 0x00FF)).substring(1);
				buf_digest.append((hex_senha.length() < 2 ? "0" : "") + hex_senha);
			}

			if (dbPassword.compareToIgnoreCase(hex_senha) == 0)
			{
				
			}

		} 
		else
		{
			generateCombinations(i+1,0,len,senha,s, salt, dbPassword);
			generateCombinations(i+1,1,len,senha,s, salt, dbPassword);
		}
	}
}
