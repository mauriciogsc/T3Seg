import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
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

	public TelaDeCadastro()
	{
		super("Cadastro");
		setLayout( new FlowLayout() );

		labelLogin = new JLabel("Login"); add(labelLogin);
		jtf = new JTextField(10);

		jtf.setEditable(true);
		add(jtf , BorderLayout.NORTH);

		labelPassword = new JLabel("Password"); add(labelPassword); 
		jpf = new JPasswordField(10);		
		jpf.setDocument(new JTextFieldLimit(10));

		jpf.setEditable(true);
		add(jpf , BorderLayout.NORTH);

		btn = new JButton("Cadastrar");
		btn.addActionListener(this); btn.setEnabled(true);
		add(btn);

		warning = new JLabel();
		warning.setForeground(Color.red);
		add(warning); warning.setVisible(false);
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
