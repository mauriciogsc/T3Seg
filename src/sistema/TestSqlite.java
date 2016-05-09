package sistema;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class TestSqlite
{	
	public void createDatabase() throws ClassNotFoundException, NoSuchAlgorithmException, SQLException
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
			statement.executeUpdate("CREATE TABLE usuario (id INTEGER PRIMARY KEY, login STRING, senha STRING, salt STRING, totalDeAcessos INTEGER, tentativas INTEGER,bloqueado INTEGER,dataBloqueio STRING)");
			statement.executeUpdate("CREATE TABLE MensagensDeRegistro (id INTEGER PRIMARY KEY, descricao STRING)");
			statement.executeUpdate("CREATE TABLE Mensagens (data TEXT, MRId INTEGER, UsuarioId INTEGER"+
					", FOREIGN KEY(UsuarioId) REFERENCES usuario(id), FOREIGN KEY(MRId) REFERENCES MensagensDeRegistro(id))");
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
		createAdmin();
	}

	public void createUser(String login, String pswd) throws SQLException, NoSuchAlgorithmException
	{
		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

			String salt = getSalt();	
			String password = setPsw(pswd + salt);

			statement.executeUpdate("INSERT INTO usuario(login,senha,salt,totalDeAcessos,tentativas,bloqueado) values('"+login+"', '"+password+"', '"+salt+"', 0, 0,0)");
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

	public void createAdmin() throws SQLException, NoSuchAlgorithmException
	{
		// create a database connection
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:sample.db");

			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
			String salt = getSalt();	
			String password = setPsw("03592419" + salt);
			
			statement.executeUpdate("INSERT INTO usuario(login,senha,salt,totalDeAcessos,tentativas,bloqueado) values('admin', '"+password+"', '"+salt+"', 0, 0,0)");
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