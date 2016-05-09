package telas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sistema.Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuSistema extends JFrame implements ActionListener{

	private JButton Cadastrar;
	private JButton Listar;
	private JButton Consultar;
	private JButton Sair;

	private JLabel login;
	private JLabel grupo;
	private JLabel descricao;
	private JLabel totalAcessos;
	private JLabel menu;

	private String currentUser;

	public MenuSistema(String currentuSet) throws SQLException {

		super("Menu Principal");	 

		this.currentUser = currentuSet;

		if (this.currentUser.equals("admin")) createAdminPanel();
		else createUserPanel();

	}

	public void start()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 300);
		this.setSize(400, 350);      // set dimensions of window
		this.setVisible(true);
		JFrame frame = new JFrame();
	}

	private void createUserPanel() throws SQLException
	{
		JPanel parte1 = new JPanel();
		parte1.setSize(200, 200);
		parte1.setVisible(true);

		JPanel parte2 = new JPanel();
		parte2.setSize(500, 200);
		parte2.setVisible(true);       

		parte1.setLayout(new BoxLayout(parte1, BoxLayout.PAGE_AXIS));
		parte2.setLayout(new BoxLayout(parte2, BoxLayout.PAGE_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(150);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(parte1);
		splitPane.setBottomComponent(parte2);

		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet resultSet = statement.executeQuery("SELECT * from usuario where login = '"+currentUser+"'");
			if(resultSet.next()) // false se o login n existir na tabela
			{
				String user_login = resultSet.getString("login");

				login = new JLabel("Login: "+ user_login);

				grupo = new JLabel("Grupo: Usuario");
				descricao = new JLabel("Descricao: Este eh um usuario do sistema");

				totalAcessos = new JLabel("Acessos: "+ resultSet.getString("totalDeAcessos"));

				parte1.add(login);
				parte1.add(grupo);
				parte1.add(descricao);

				parte1.add(new JLabel(" "));
				parte1.add(new JLabel(" "));
				parte1.add(totalAcessos);

				currentUser = user_login;

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

		// criando itens do menu 
		Listar = new JButton("Listar chave privada e certificado digital");
		Consultar = new JButton("Consultar pasta de arquivos secretos do usu�rio");
		Sair = new JButton("Sair do Sistema");             

		Dimension d = new Dimension(350,30);
		Listar.setMaximumSize(d);
		Consultar.setMaximumSize(d);
		Sair.setMaximumSize(d);

		menu = new JLabel("Menu Principal:");

		parte2.add(menu);
		parte2.add(new JLabel(" "));
		parte2.add(Listar);
		parte2.add(Consultar);
		parte2.add(Sair);

		Listar.addActionListener(this);
		Consultar.addActionListener(this);
		Sair.addActionListener(this);       

		this.add(splitPane);
	}

	private void createAdminPanel()
	{
		JPanel parte1 = new JPanel();
		parte1.setSize(200, 200);
		parte1.setVisible(true);

		JPanel parte2 = new JPanel();
		parte2.setSize(500, 200);
		parte2.setVisible(true);       

		parte1.setLayout(new BoxLayout(parte1, BoxLayout.PAGE_AXIS));
		parte2.setLayout(new BoxLayout(parte2, BoxLayout.PAGE_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(150);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(parte1);
		splitPane.setBottomComponent(parte2);

		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			ResultSet resultSet = statement.executeQuery("SELECT * from usuario where login = '"+currentUser+"'");
			if(resultSet.next()) // false se o login n existir na tabela
			{
				String user_login = resultSet.getString("login");

				login = new JLabel("Login: "+ user_login);

				grupo = new JLabel("Grupo: Administrador");
				descricao = new JLabel("Descricao: Este eh o administrador do sistema");

				totalAcessos = new JLabel("Acessos: "+ resultSet.getString("totalDeAcessos"));

				parte1.add(login);
				parte1.add(grupo);
				parte1.add(descricao);

				parte1.add(new JLabel(" "));
				parte1.add(new JLabel(" "));
				parte1.add(totalAcessos);

				currentUser = user_login;

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


		// criando itens do menu 
		Cadastrar = new JButton("Cadastrar um novo usu�rio");
		Listar = new JButton("Listar chave privada e certificado digital");
		Consultar = new JButton("Consultar pasta de arquivos secretos do usu�rio");
		Sair = new JButton("Sair do Sistema");             

		menu = new JLabel("Menu Principal:");

		parte2.add(menu);
		parte2.add(new JLabel(" "));
		parte2.add(Cadastrar);
		parte2.add(Listar);
		parte2.add(Consultar);
		parte2.add(Sair);

		Cadastrar.addActionListener(this);
		Listar.addActionListener(this);
		Consultar.addActionListener(this);
		Sair.addActionListener(this);       

		this.add(splitPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String menuItem = e.getActionCommand();

		if (menuItem.equals("Cadastrar um novo usu�rio"))
		{
			try {

				this.setVisible(false);
				dispose();

				TelaDeCadastro tc = new TelaDeCadastro(currentUser);
				tc.start();

				return;

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (menuItem.equals("Listar chave privada e certificado digital"))
		{

			this.setVisible(false);
			dispose();

			TelaDeListagem tl = new TelaDeListagem(currentUser);
			tl.start();

			return;
		}
		else if(menuItem.equals("Consultar pasta de arquivos secretos do usu�rio"))
		{
		}
		else if(menuItem.equals("Sair do Sistema"))
		{

			try {
				this.setVisible(false);
				dispose();

				TelaDeSaida ts;
				ts = new TelaDeSaida(currentUser);
				ts.start();				

				return;

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}


}
