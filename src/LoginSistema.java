
import javax.swing.*;

public class LoginSistema {

	private String login;
	private String password;

	private JLabel labelLogin = new JLabel("Digite seu login");
	private JTextField jtf = new JTextField();

	private JLabel labelPsw = new JLabel("Digite sua senha:");
	private JPasswordField jpf = new JPasswordField();

	public void systemInit()
	{
		JOptionPane.showConfirmDialog(null,
				new Object[]{labelLogin, jtf}, "Login:",
				JOptionPane.OK_CANCEL_OPTION); 
		login = jtf.getText();

		if (login == null || login.equals("")) JOptionPane.showMessageDialog(null, "Você não inseriu seu login.");
		/*
		else 
		{
			JOptionPane.showConfirmDialog(null,
					new Object[]{labelPsw, jpf}, "Password:",
					JOptionPane.OK_CANCEL_OPTION); 

			password = new String(jpf.getPassword());

			System.out.println("Login: "+ login);
			System.out.println("Senha: "+ password);
		}
		 */

		VirtualKeyboard typingTutor = new VirtualKeyboard();    // creates TypingTutor

		typingTutor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		typingTutor.setLocation(300, 300);
		typingTutor.setSize(400, 200);      // set dimensions of window
		typingTutor.setVisible(true);
		JFrame frame = new JFrame();

	}

}
