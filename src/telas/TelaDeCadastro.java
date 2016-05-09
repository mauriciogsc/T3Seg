package telas;
import sistema.TestSqlite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.scene.control.ComboBox;

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

	private JLabel labelLogin;
	private JTextField jtf;

	private JLabel labelPassword;
	private JPasswordField jpf;

	private String password;
	private String login;

	private JButton btn;	

	private JLabel warning;

	private JLabel name;
	private JLabel loginLabel;
	private JLabel grupo;
	private JComboBox<String> list;
	private JLabel descricao;
	private JLabel totalAcessos;
	private JLabel cadastro;

	public TelaDeCadastro()
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

		name = new JLabel("Nome:");
		loginLabel = new JLabel("Login:");
		
		grupo = new JLabel("Grupo:");
		
		descricao = new JLabel("descricao tabajara");
		totalAcessos = new JLabel("Acessos: 4");

		cadastro = new JLabel("Formulário de Cadastro:");

		parte1.add(name);
		parte1.add(loginLabel);
		parte1.add(grupo);
		parte1.add(descricao);

		parte1.add(new JLabel(" "));
		parte1.add(new JLabel(" "));
		parte1.add(totalAcessos);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerSize(2);
		splitPane.setDividerLocation(150);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(parte1);
		splitPane.setBottomComponent(parte2);

		parte2.add(cadastro);
		parte2.add(new JLabel(" "));

		labelLogin = new JLabel("Login"); parte2.add(labelLogin);
		jtf = new JTextField(10);

		jtf.setEditable(true);
		parte2.add(jtf , BorderLayout.NORTH);

		labelPassword = new JLabel("Password"); parte2.add(labelPassword); 
		jpf = new JPasswordField(10);		
		jpf.setDocument(new JTextFieldLimit(10));

		jpf.setEditable(true);
		parte2.add(jpf , BorderLayout.NORTH);

		btn = new JButton("Cadastrar");
		btn.addActionListener(this); btn.setEnabled(true);
		parte2.add(btn);

		warning = new JLabel();
		warning.setForeground(Color.red);
		parte2.add(warning); warning.setVisible(false);


		this.add(splitPane);
	}

	public void start()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 300);
		this.setSize(400, 350);      // set dimensions of window
		this.setVisible(true);
		JFrame frame = new JFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String button = e.getActionCommand();
		password = jpf.getText();
		login = jtf.getText();

		if (button.equals("Cadastrar"))
		{
			// verificar repetição/sequencia/senha válida

			// checagem da senha
			int current, next;

			// realizo a checagem se nao for o primeiro digito
			if (password.length() < 8)
			{
				password = "";
				warning.setText("Sua senha deve ter pelo menos 8 digitos.");
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
						warning.setText("Sua senha possui uma sequencia ou uma repeticao. Insera outra senha.");
						warning.setVisible(true);
						return;
					}
				}

				TestSqlite teste = new TestSqlite();

				try {
					teste.createUser(login, password);
				} catch (NoSuchAlgorithmException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				login = "";
				password = "";
				warning.setText("Cadastro Concluído");
				warning.setVisible(true);

				jtf.setText(login);
				jpf.setText(password);

			}
		}

	}

}
