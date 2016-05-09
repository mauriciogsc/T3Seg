package telas;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import sistema.VerificaChavePrivada;

public class TelaDeArquivo extends JFrame implements ActionListener{

	private JLabel secretPhrase;
	private String phrase;
	
	private JTextField jtf_sp;
	private JFileChooser fc;
	JTextArea filePath;
	private JButton fc_button;

	public TelaDeArquivo()
	{
		super("Pegar Arquivo");
		
		setLayout(new GridLayout(0,2));
		
		secretPhrase = new JLabel("Frase Secreta:");
		jtf_sp = new JTextField(20);
		
		fc = new JFileChooser();
		
		filePath = new JTextArea();
		fc_button = new JButton("Pegar Arquivo");
		
		fc_button.addActionListener(this);
		
		this.add(secretPhrase);
		this.add(jtf_sp);
		this.add(new JLabel(" "));
		this.add(new JLabel(" "));
		this.add(filePath);
		this.add(fc_button);
		
	}
	
	public void start()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(300, 300);
		this.setSize(500, 120);      // set dimensions of window
		this.setVisible(true);
		JFrame frame = new JFrame();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		String button = arg0.getActionCommand();
		phrase = jtf_sp.getText();
		VerificaChavePrivada verify = new VerificaChavePrivada();
		
		if (button.equals("Pegar Arquivo"))
		{

            System.out.println("aqr");
			int returnVal = fc.showOpenDialog(TelaDeArquivo.this);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            if (verify.VerificaChave(file, phrase)){
	            	System.out.println("OK VERIFICACAO");
	            }
	            else
	            {
	            	System.out.println("NOT OK VERIFICACAO");
	            }
	            filePath.append(file.getName() + "." + "\n");
	        } else {
	        	filePath.append("Open command cancelled by user." + "\n");
	        }
	        
		}
	}

}
