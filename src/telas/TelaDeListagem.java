package telas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class TelaDeListagem extends JFrame implements ActionListener{
	
	private String currentUser;
	
	public TelaDeListagem(String user)
	{
		this.currentUser = user;
		
		
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
		
	}

}
