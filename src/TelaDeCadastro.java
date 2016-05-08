import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private JButton btn;
	
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

		jpf.setEditable(true);
		add(jpf , BorderLayout.SOUTH);
		
		btn = new JButton("Cadastrar");
		btn.addActionListener(this); btn.setEnabled(true);
		add(btn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String button = e.getActionCommand();
		
		if (button.equals("Cadastrar"))
		{
			// verificar repetição/sequencia/senha válida
		}
		
	}

}
