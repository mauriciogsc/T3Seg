package telas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class TelaDeSaida extends JFrame implements ActionListener{

	private JButton Sair;

	private JLabel login;
	private JLabel grupo;
	private JLabel descricao;
	private JLabel totalAcessos;
	private JLabel menu;
	
	private JLabel saida;
	private JLabel msgSaida;

	private ResultSet currentUser;

	public TelaDeSaida() throws SQLException {

		super();	   

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

		// criando itens do menu 
		Sair = new JButton("Sair");             

		Dimension d = new Dimension(80,30);
		Sair.setMaximumSize(d);

		login = new JLabel("bla");
		grupo = new JLabel("grupo");
		descricao = new JLabel("descricao tabajara");
		totalAcessos = new JLabel("bla");

		saida = new JLabel("Saida do Sistema:");
		msgSaida = new JLabel("Pressione o botão Sair para confirmar.");
		
		menu = new JLabel("Menu Principal:");

		parte1.add(login);
		parte1.add(grupo);
		parte1.add(descricao);

		parte1.add(new JLabel(" "));
		parte1.add(new JLabel(" "));
		parte1.add(totalAcessos);

		parte2.add(saida);
		parte2.add(new JLabel(" "));
		parte2.add(msgSaida);
		parte2.add(Sair);

		Sair.addActionListener(this);       

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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String button = arg0.getActionCommand();
	}
}
