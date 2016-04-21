

import java.awt.BorderLayout;
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

	private JButton b0,b1,b2,b3,b4,b5,b6,b7,b8,b9,Backspace, go;
	private JPasswordField passwordField;
	private JLabel label;
	private String password = new String();

	public VirtualKeyboard()
	{

		super("Virtual Keyboard");

		setLayout( new FlowLayout() );

		label = new JLabel("Digite sua senha usando o teclado virtual");
		add(label);

		passwordField = new JPasswordField(10);

		passwordField.setEditable(true);
		add(passwordField , BorderLayout.NORTH);

		b0 = new JButton( "0" ); add(b0); b0.addActionListener(this);
		b1 = new JButton( "1" ); add(b1); b1.addActionListener(this);
		b2 = new JButton( "2" ); add(b2); b2.addActionListener(this);
		b3 = new JButton( "3" ); add(b3); b3.addActionListener(this);
		b4 = new JButton( "4" ); add(b4); b4.addActionListener(this);
		b5 = new JButton( "5" ); add(b5); b5.addActionListener(this);
		b6 = new JButton( "6" ); add(b6); b6.addActionListener(this);
		b7 = new JButton( "7" ); add(b7); b7.addActionListener(this);
		b8 = new JButton( "8" ); add(b8); b8.addActionListener(this);
		b9 = new JButton( "9" ); add(b9); b9.addActionListener(this);

		Backspace = new JButton("Backspace"); add(Backspace); Backspace.addActionListener(this);
		
		go = new JButton("Entrar"); add(go); go.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String button = e.getActionCommand();	
		if(button.equals("Backspace"))
		{
			
		}
		else if (button.equals("Entrar"))
		{
			
		}
		else
		{
		// verificar aqui a qtde de char
			password+=button;		
			passwordField.setText(password);
			System.out.println(password);
		}
	}
}
