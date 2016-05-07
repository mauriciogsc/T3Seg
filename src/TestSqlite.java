import java.sql.Connection;
   import java.sql.DriverManager;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.sql.Statement;

   public class TestSqlite
    {
    public void test() throws ClassNotFoundException
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
         statement.executeUpdate("DROP TABLE IF EXISTS usuario");
         statement.executeUpdate("CREATE TABLE usuario (id INTEGER PRIMARY KEY, login STRING, senha STRING, salt STRING, totalDeAcessos INTEGER, tentativas INTEGER)");
         statement.executeUpdate("CREATE TABLE MensagensDeRegistro (id INTEGER PRIMARY KEY, descricao STRING)");
         statement.executeUpdate("CREATE TABLE Mensagens (data TEXT, MRId INTEGER, UsuarioId INTEGER"+
         ", FOREIGN KEY(UsuarioId) REFERENCES usuario(id), FOREIGN KEY(MRId) REFERENCES MensagensDeRegistro(id)");
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
  }
 }