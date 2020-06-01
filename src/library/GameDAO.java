package library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GameDAO
{
  private final String url;
  private final String username;
  private final String password;
  
  public GameDAO(String url, String username, String password)
  {
    super();
    
    this.url = url;
    this.username = username;
    this.password = password;
  }
  
  public Game getGame(int id) throws SQLException
  {
    final String sql = "SELECT * FROM books WHERE book_id = ?";
    
    Game game = null;
    Connection conn = getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sql);
    
    pstmt.setInt(1, id);
    ResultSet rs = pstmt.executeQuery();
    
    if (rs.next()) {
      String title = rs.getString("title");
      String author = rs.getString("author");
      int copies = rs.getInt("copies");
      int available = rs.getInt("available");
      
      game = new Game(id, title, author, copies, available);
    }
    
    rs.close();
    pstmt.close();
    conn.close();
    
    return game;
  }
  
  public List<Game> getGames() throws SQLException
  {
    final String sql = "SELECT * FROM books ORDER BY book_id ASC";
    
    List<Game> games = new ArrayList<>();
    Connection conn = getConnection();
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery(sql);
    
    while (rs.next()) {
      int id = rs.getInt("book_id");
      String title = rs.getString("title");
      String author = rs.getString("author");
      int copies = rs.getInt("copies");
      int available = rs.getInt("available");
      
      game.add(new Game(id, title, author, copies, available));
    }
    
    rs.close();
    stmt.close();
    conn.close();
    
    return games;
  }
  
  public boolean insertGame(String title, String author, int copies, int available)
		    throws SQLException
		{
		  final String sql = "INSERT INTO books (title, author, copies, available) " +
		      "VALUES (?, ?, ?, ?)";
			
		  Connection conn = getConnection();
		  PreparedStatement pstmt = conn.prepareStatement(sql);
			
		  pstmt.setString(1, title);
		  pstmt.setString(2, author);
		  pstmt.setInt(3, copies);
		  pstmt.setInt(4, available);
		  int affected = pstmt.executeUpdate();
			
		  pstmt.close();
		  conn.close();
			
		  return affected == 1;
		}
  
  public boolean updateGame(Game game) throws SQLException
  {
    final String sql = "UPDATE books SET title = ?, author = ?, copies = ?, available = ? " +
        "WHERE book_id = ?";
    
    Connection conn = getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sql);
    
    pstmt.setString(1, game.getTitle());
    pstmt.setString(2, game.getAuthor());
    pstmt.setInt(3, game.getCopies());
    pstmt.setInt(4, game.getAvailable());
    pstmt.setInt(5, game.getId());
    int affected = pstmt.executeUpdate();
    
    pstmt.close();
    conn.close();
    
    return affected == 1;
  }
  
  public boolean deleteGame(Game game) throws SQLException
  {
    final String sql = "DELETE FROM books WHERE book_id = ?";
    
    Connection conn = getConnection();
    PreparedStatement pstmt = conn.prepareStatement(sql);
    
    pstmt.setInt(1, game.getId());
    int affected = pstmt.executeUpdate();
    
    pstmt.close();
    conn.close();
    
    return affected == 1;
  }
  
  private Connection getConnection() throws SQLException
  {
    final String driver = "com.mysql.cj.jdbc.Driver";
    
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    
    return DriverManager.getConnection(url, username, password);
  }
}