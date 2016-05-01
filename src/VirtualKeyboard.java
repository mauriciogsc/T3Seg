
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private boolean isFreeToType = true;
	private JLabel warning;

	public VirtualKeyboard()
	{
		super("Virtual Keyboard");

		setLayout( new FlowLayout() );

		label = new JLabel("Digite sua senha usando o teclado virtual");
		add(label);

		passwordField = new JPasswordField(10);

		passwordField.setEditable(true);
		add(passwordField , BorderLayout.NORTH);

		b0 = new JButton( "0 ou 1" ); add(b0); b0.addActionListener(this);
		b1 = new JButton( "2 ou 3" ); add(b1); b1.addActionListener(this);
		b2 = new JButton( "4 ou 5" ); add(b2); b2.addActionListener(this);
		b3 = new JButton( "6 ou 7" ); add(b3); b3.addActionListener(this);
		b4 = new JButton( "8 ou 9" ); add(b4); b4.addActionListener(this);

		Backspace = new JButton("Backspace"); add(Backspace); Backspace.addActionListener(this);

		go = new JButton("Entrar"); add(go); go.addActionListener(this);
		go.setEnabled(false); // nao entra sem senha

		warning = new JLabel("Sua senha possui uma sequência ou uma repetição. Favor entrar com uma nova senha");
		warning.setForeground(Color.red);
		add(warning); warning.setVisible(false);
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
				isFreeToType = true;
			}
		}
		else if (button.equals("Entrar"))
		{
			// entrar no sistema
		}
		else if (isFreeToType) // senha menor que 10, eh possivle add mais digitos
		{
			// pegar ultimo e novo digito
			int temp, newDigit;

			// realizo a checagem se não for o primeiro digito
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

				if (password.length() == 8) go.setEnabled(true);

				if (password.length() == 10) isFreeToType = false;
			}
			passwordField.setText(password);
		}
	}
}
