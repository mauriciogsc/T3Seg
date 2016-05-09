package telas;
import sistema.Banco;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;


public class TelaDeCadastro extends JFrame implements ActionListener{

	private String password;
	private String login;	
	
	private String user_login;// para o recebimento
	private int grupoId;
	private String pathCert;
	private String nome;

	private JLabel warning;

	// parte 1
	private JLabel loginLabel_pt1;
	private JLabel grupo_pt1;
	private JLabel descricao;

	private JLabel totalUsuarios;

	// parte 2
	private JLabel cadastro;

	private JLabel name;
	private JTextField jtf_name;

	private JLabel loginLabel_pt2;
	private JTextField jtf_login;

	private JLabel grupo_pt2;
	private JComboBox<String> grupoList;

	private JLabel labelPassword;
	private JPasswordField jpf_password;

	private JLabel cofirmPassword;
	private JPasswordField jpf_confirmation;

	private JLabel caminhoArq;
	private JTextField jtf_caminho;

	private JButton btn_cadastro;
	private JButton btn_voltar;

	public TelaDeCadastro(String currentUser) throws SQLException
	{
		super("Cadastro");

		JPanel parte1 = new JPanel();
		parte1.setSize(200, 200);
		parte1.setVisible(true);

		parte1.setLayout(new BoxLayout(parte1, BoxLayout.PAGE_AXIS));

		JPanel parte2 = new JPanel();
		parte2.setSize(500, 200);
		parte2.setVisible(true);    

		parte2.setLayout(new GridLayout(0,2));

		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet resultSet = statement.executeQuery("SELECT * from usuario where login = '"+currentUser+"'");
			if(resultSet.next()) // false se o login n existir na tabela
			{
				user_login = resultSet.getString("login");

				loginLabel_pt1 = new JLabel(user_login);
				grupo_pt1 = new JLabel("grupo");
				descricao = new JLabel("descricao q tava no banco");
				totalUsuarios = new JLabel("Usuarios: ");

				parte1.add(loginLabel_pt1);
				parte1.add(grupo_pt1);
				parte1.add(descricao);

				parte1.add(new JLabel(" "));
				parte1.add(new JLabel(" "));
				parte1.add(totalUsuarios);

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
	
		cadastro = new JLabel("Formulário de Cadastro");

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(150);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(parte1);
		splitPane.setBottomComponent(parte2);

		name = new JLabel("Nome:");
		jtf_name = new JTextField(50); jtf_name.setDocument(new JTextFieldLimit(50));

		loginLabel_pt2 = new JLabel("Login:");
		jtf_login = new JTextField(20); jtf_name.setDocument(new JTextFieldLimit(20));

		grupo_pt2 = new JLabel("Grupo:");		
		String[] options = {"Administrador","Usuario"};
		grupoList = new JComboBox<>(options);

		labelPassword = new JLabel("Senha:"); 
		jpf_password = new JPasswordField(10); jpf_password.setDocument(new JTextFieldLimit(10));

		cofirmPassword = new JLabel("Confirma senha:"); 
		jpf_confirmation = new JPasswordField(10); jpf_confirmation.setDocument(new JTextFieldLimit(10));

		caminhoArq = new JLabel("Caminho do Arquivo:");
		jtf_caminho = new JTextField(255); jtf_caminho.setDocument(new JTextFieldLimit(255));

		btn_cadastro = new JButton("Cadastrar");
		btn_cadastro.addActionListener(this); btn_cadastro.setEnabled(true);
		
		btn_voltar = new JButton("Voltar");
		btn_voltar.addActionListener(this); btn_voltar.setEnabled(true);

		warning = new JLabel();
		warning.setForeground(Color.red);

		parte2.add(cadastro);
		parte2.add(new JLabel(" "));

		parte2.add(name);
		parte2.add(jtf_name);

		parte2.add(loginLabel_pt2);
		parte2.add(jtf_login);

		parte2.add(grupo_pt2);		
		parte2.add(grupoList);

		parte2.add(labelPassword);	
		parte2.add(jpf_password);

		parte2.add(cofirmPassword);
		parte2.add(jpf_confirmation);

		parte2.add(caminhoArq);
		parte2.add(jtf_caminho);

		parte2.add(btn_cadastro);
		parte2.add(btn_voltar);
		
		parte2.add(warning); warning.setVisible(false);

		this.add(splitPane);
	}

	public void start()
	{

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 300);
		this.setSize(450, 450);      // set dimensions of window
		this.setVisible(true);
		JFrame frame = new JFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String button = e.getActionCommand();
		password = jpf_password.getText();
		login = jtf_login.getText();
		pathCert = jtf_caminho.getText();
		nome = jtf_name.getText();
		if(grupoList.getSelectedItem().toString().compareTo("Administrador")==0)
		{
			grupoId = 1;
		}
		else
			grupoId=2;
		if (button.equals("Cadastrar"))
		{
			// verificar repetição/sequencia/senha válida

			// checagem da senha
			int current, next;

			if(!password.equals(jpf_confirmation.getText()))
			{
				password = "";
				warning.setText("O campo de confirmação difere.");
				warning.setVisible(true);
				return;
			}
			// realizo a checagem se nao for o primeiro digito
			if (password.length() < 8)
			{
				password = "";
				warning.setText("Senha deve ter pelo menos 8 digitos.");
				warning.setVisible(true);
				return;
			}

			else if(!password.equals(""))
			{
				for(int i=0; i<password.length()-1; i++)
				{

					current = Character.getNumericValue(password.charAt(i));
					next = Character.getNumericValue(password.charAt(i+1));


					System.out.println(login + " " + current + " + " + next);

					// verificar novo digito - eh diferente do anterior e nao eh sequecia 
					if(next == current || next == current+1 || next == current-1)
					{
						password = "";
						warning.setText("Senha inválida.");
						warning.setVisible(true);
						return;
					}
				}

				Banco teste = new Banco();

				try {
					teste.createUser(login, password,grupoId,pathCert,nome);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

				login = "";
				password = "";
				warning.setText("Cadastro Concluído");
				warning.setVisible(true);

				jtf_login.setText(login);
				jpf_password.setText(password);

			}
		}
		else if (button.equals("Voltar"))
		{
			MenuSistema ms;
			try {
				this.setVisible(false);
				dispose();
				
				ms = new MenuSistema(user_login);
				ms.start();
				
				return;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
