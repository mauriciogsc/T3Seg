package telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
	
   public MenuSistema() {
        
	   super();	   
	   	   
	   createUserPanel();
       
   }
   
   private void createUserPanel()
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
	                     
       // criando itens do menu 
       Cadastrar = new JButton("Cadastrar um novo usu�rio");
       Listar = new JButton("Listar chave privada e certificado digital");
       Consultar = new JButton("Consultar pasta de arquivos secretos do usu�rio");
       Sair = new JButton("Sair do Sistema");             
       
       login = new JLabel("admin");
       grupo = new JLabel("grupo");
       descricao = new JLabel("descricao tabajara");
       totalAcessos = new JLabel("Acessos: 4");
       
       menu = new JLabel("Menu Principal:");
              
       parte1.add(login);
       parte1.add(grupo);
       parte1.add(descricao);

       parte1.add(new JLabel(" "));
       parte1.add(new JLabel(" "));
       parte1.add(totalAcessos);
       
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
	                     
       // criando itens do menu 
       Cadastrar = new JButton("Cadastrar um novo usu�rio");
       Listar = new JButton("Listar chave privada e certificado digital");
       Consultar = new JButton("Consultar pasta de arquivos secretos do usu�rio");
       Sair = new JButton("Sair do Sistema");             
       
       login = new JLabel("admin");
       grupo = new JLabel("grupo");
       descricao = new JLabel("descricao tabajara");
       totalAcessos = new JLabel("Acessos: 4");
       
       menu = new JLabel("Menu Principal:");
              
       parte1.add(login);
       parte1.add(grupo);
       parte1.add(descricao);

       parte1.add(new JLabel(" "));
       parte1.add(new JLabel(" "));
       parte1.add(totalAcessos);
       
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
		System.out.println("CADASTRAR");
	}
	else if (menuItem.equals("Listar chave privada e certificado digital"))
	{
		System.out.println("LISTAR");
	}
	else if(menuItem.equals("Consultar pasta de arquivos secretos do usu�rio"))
	{
		System.out.println("CONSULTAR");
	}
	else if(menuItem.equals("Sair do Sistema"))
	{
		System.out.println("SAIR");
	}
}
	

}