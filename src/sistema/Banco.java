package sistema;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.sun.javafx.scene.paint.GradientUtils.Parser;

public class Banco
{	
	public void createDatabase() throws Exception
	{
		// load the sqlite-JDBC driver using the current class loader
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try
		{
			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
		
			statement.executeUpdate("DROP TABLE IF EXISTS usuario");
			statement.executeUpdate("DROP TABLE IF EXISTS MensagensDeRegistro");
			statement.executeUpdate("DROP TABLE IF EXISTS Mensagens");
			statement.executeUpdate("DROP TABLE IF EXISTS grupo");
			statement.executeUpdate("CREATE TABLE usuario (id INTEGER PRIMARY KEY, login STRING, senha STRING, salt STRING, totalDeAcessos INTEGER, tentativas INTEGER,bloqueado INTEGER,dataBloqueio STRING,grupoId INTEGER,certificado BLOB,nome STRING"+
					", FOREIGN KEY(grupoId) REFERENCES grupo(id))");
			statement.executeUpdate("CREATE TABLE MensagensDeRegistro (id INTEGER PRIMARY KEY, descricao STRING)");
			statement.executeUpdate("CREATE TABLE Mensagens (data TEXT, MRId INTEGER, UsuarioId INTEGER"+
					", FOREIGN KEY(UsuarioId) REFERENCES usuario(id), FOREIGN KEY(MRId) REFERENCES MensagensDeRegistro(id))");
			statement.executeUpdate("CREATE TABLE grupo (id INTEGER PRIMARY KEY,nome STRING)");

			statement.executeUpdate("INSERT INTO grupo(nome) values('Administrador'),('Usuario')");
			InsertMensagens(statement);
			
			//int ids [] = {1,2,3,4,5};
			//String names [] = {"Peter","Pallar","William","Paul","James Bond"};

			//for(int i=0;i<ids.length;i++){
			//     statement.executeUpdate("INSERT INTO person values(' "+ids[i]+"', '"+names[i]+"')");   
			//}

			//statement.executeUpdate("UPDATE person SET name='Peter' WHERE id='1'");
			//statement.executeUpdate("DELETE FROM person WHERE id='1'");

			// ResultSet resultSet = statement.executeQuery("SELECT * from person");
			//  while(resultSet.next())
			//  {
			//     // iterate & read the result set
			//     System.out.println("name = " + resultSet.getString("name"));
			//     System.out.println("id = " + resultSet.getInt("id"));
			//  }
			// }
		}
		catch(SQLException e){  System.err.println(e.getMessage()); }       
		finally {         
			try {
				if(connection != null)
					connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}

		// administrador
		//createAdmin();
		createUser("admin","03592419",1,"Keys/usercert-x509.crt","Administrador");
	}

	public byte[] getCertificado(String login)
	{
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			 ResultSet resultSet = statement.executeQuery("SELECT certificado FROM usuario WHERE login='"+login+"'");
			 
			  if(resultSet.next())
			  {
				  byte[] ret = resultSet.getBytes(1);
				  connection.close();
			     return ret;
			  }
			  else
			  {
				  connection.close();
				  return null;
			  }
		}
		catch(SQLException e){  System.err.println(e.getMessage()); return null; } 
	}
	
	public void createUser(String login, String pswd,int grupoId, String pathCert,String nome) throws Exception
	{
		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			PreparedStatement prepStat = connection.prepareStatement("INSERT INTO usuario(login,senha,salt,totalDeAcessos,tentativas,bloqueado,grupoId,certificado,nome) values(?, ? , ? , 0,0,0,?,?,?)");
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			prepStat.setString(1, login);
			String salt = getSalt();	
			String password = setPsw(pswd + salt);
			prepStat.setString(2, password);
			prepStat.setString(3, salt);
			prepStat.setInt(4, grupoId);
            File fil = new File(pathCert);
            byte[] cert = new byte[(int)fil.length()];
            FileInputStream fin = new FileInputStream(fil);
            fin.read(cert);
			prepStat.setBytes(5,cert);
			prepStat.setString(6, nome);
			prepStat.executeUpdate();
		}
		catch(SQLException e){  System.err.println(e.getMessage()); }       
		finally {         
			try {
				if(connection != null)
					connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}		
	}

	public void createAdmin() throws Exception
	{
		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
			PreparedStatement prepStat = connection.prepareStatement("INSERT INTO usuario(login,senha,salt,totalDeAcessos,tentativas,bloqueado,grupoId,certificado) values('admin', ? , ? , 0,0,0,1,?)");
			
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
			String salt = getSalt();	
			String password = setPsw("03592419" + salt);
			prepStat.setString(1, password);
			prepStat.setString(2, salt);
            File fil = new File("Keys/usercert-x509.crt");
            byte[] cert = new byte[(int)fil.length()];
            FileInputStream fin = new FileInputStream(fil);
            fin.read(cert);
			prepStat.setBytes(3,cert);
			prepStat.executeUpdate();
		}
		catch(SQLException e){  System.err.println(e.getMessage()); }       
		finally {         
			try {
				if(connection != null)
					connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}		
	}

	public String getSalt()
	{
		int num;
		String salt = "";
		for (int i = 0; i < 9; i++)
		{
			Random rand = new Random();
			num = rand.nextInt(10);// pegar numero randomico de 0 a 9 inclusive

			salt+=(Integer.toString(num));

		}
		return salt;
	}
	
	public static void InsertRegistro(int id, int userId)
	{
		Connection connection = null;
		System.out.println(id);
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			if(userId==0)
				statement.executeUpdate("INSERT INTO Mensagens(MRId) values("+Integer.toString(id)+")");
			else
				statement.executeUpdate("INSERT INTO Mensagens(MRId,UsuarioId) values("+Integer.toString(id)+","+Integer.toString(userId)+")");
				
			 
		}
		catch(SQLException e){  System.err.println(e.getMessage());} 
		finally{
			try {
				if(connection != null)
					connection.close();
			}
			catch(SQLException e) {  // Use SQLException class instead.          
				System.err.println(e); 
			}
		}
	}

	public void InsertMensagens(Statement statement) throws SQLException
	{
		
		statement.executeUpdate("INSERT INTO MensagensDeRegistro (id,descricao)"+
				"values(1001,'Sistema iniciado.'),"+
				"(1002,'Sistema encerrado.'),"+
				"(2001,'Autenticação etapa 1 iniciada.'),"+
				"(2002,'Autenticação etapa 1 encerrada.'),"+
				"(2003,'Login name <login_name> identificado com acesso liberado.'),"+
				"(2004,'Login name <login_name> identificado com acesso bloqueado.'),"+
				"(2005,'Login name <login_name> não identificado.'),"+
				"(3001,'Autenticação etapa 2 iniciada para <login_name>.'),"+
				"(3002,'Autenticação etapa 2 encerrada para <login_name>.'),"+
				"(3003,'Senha pessoal verificada positivamente para <login_name>.'),"+
				"(3004,'Senha pessoal verificada negativamente para <login_name>.'),"+
				"(3005,'Primeiro erro da senha pessoal contabilizado para <login_name>.'),"+
				"(3006,'Segundo erro da senha pessoal contabilizado para <login_name>.'),"+
				"(3007,'Terceiro erro da senha pessoal contabilizado para <login_name>.'),"+
				"(3008,'Acesso do usuario <login_name> bloqueado pela autenticação etapa 2.'),"+
				"(4001,'Autenticação etapa 3 iniciada para <login_name>.'),"+
				"(4002,'Autenticação etapa 3 encerrada para <login_name>.'),"+
				"(4003,'Chave privada verificada positivamente para <login_name>.'),"+
				"(4004,'Primeiro erro da chave privada contabilizado para <login_name>.'),"+
				"(4005,'Segundo erro da chave privada contabilizado para <login_name>.'),"+
				"(4006,'Terceiro erro da chave privada contabilizado para <login_name>.'),"+
				"(4007,'Caminho da chave privada inválido fornecido por <login_name>.'),"+
				"(4008,'Frase secreta inválida fornecida por <login_name>.'),"+
				"(4009,'Acesso do usuario <login_name> bloqueado pela autenticação etapa 3.'),"+
				"(5001,'Tela principal apresentada para <login_name>.'),"+
				"(5002,'Opção 1 do menu principal selecionada por <login_name>.'),"+
				"(5003,'Opção 2 do menu principal selecionada por <login_name>.'),"+
				"(5004,'Opção 3 do menu principal selecionada por <login_name>.'),"+
				"(5005,'Opção 4 do menu principal selecionada por <login_name>.'),"+
				"(6001,'Tela de cadastro apresentada para <login_name>.'),"+
				"(6002,'Botão cadastrar pressionado por <login_name>.'),"+
				"(6003,'Caminho do certificado digital inválido fornecido por <login_name>.'),"+
				"(6004,'Confirmação de dados aceita por <login_name>.'),"+
				"(6005,'Confirmação de dados rejeitada por <login_name>.'),"+
				"(6006,'Botão voltar de cadastro para o menu principal pressionado por <login_name>.'),"+
				"(7001,'Tela de listagem de chave privada e certificado apresentada para <login_name>.'),"+
				"(7002,'Botão voltar de listagem para o menu principal pressionado por <login_name>.'),"+
				"(8001,'Tela de consulta de arquivos secretos apresentada para <login_name>.'),"+
				"(8002,'Botão voltar de consulta para o menu principal pressionado por <login_name>.'),"+
				"(8003,'Botão Listar de consulta pressionado por <login_name>.'),"+
				"(8006,'Caminho de pasta inválido fornecido por <login_name>.'),"+
				"(8007,'Lista de arquivos apresentada para <login_name>.'),"+
				"(8008,'Arquivo <arq_name> selecionado por <login_name> para decriptação.'),"+
				"(8009,'Arquivo <arq_name> decriptado com sucesso para <login_name>.'),"+
				"(8010,'Arquivo <arq_name> verificado (integridade e autenticidade) com sucesso para <login_name>.'),"+
				"(8011,'Falha na decriptação do arquivo <arq_name> para <login_name>.'),"+
				"(8012,'Falha na verificação do arquivo <arq_name> para <login_name>.'),"+
				"(9001,'Tela de saída apresentada para <login_name>.'),"+
				"(9002,'Botão sair pressionado por <login_name>.'),"+
				"(9003,'Botão voltar de sair para o menu principal pressionado por <login_name>.')");
	}
	
	public String setPsw(String psw) throws NoSuchAlgorithmException
	{		
		String hex = "";
		byte[] texto = new byte[(int)psw.getBytes().length];

		//transformando o conteudo do arquivo em digest do tipo informado
		MessageDigest mDigest = MessageDigest.getInstance("MD5");

		//criando o digest e salvando
		texto = psw.getBytes();
		mDigest.update(texto);
		StringBuffer buf_digest = new StringBuffer();
		byte[] digest = mDigest.digest();
		//transformando o digest em uma string Hexa
		for(int j = 0; j < digest.length; j++) {
			hex = Integer.toHexString(0x0100 + (digest[j] & 0x00FF)).substring(1);
			buf_digest.append((hex.length() < 2 ? "0" : "") + hex);
		}

		return buf_digest.toString();
	}

}