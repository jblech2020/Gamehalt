package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import library.Game;
import library.GameDAO;

public class Controller extends HttpServlet
{
  private static final long serialVersionUID = 1L;
  private GameDAO dao;
  
  public void init()
  {
	  final String url = getServletContext().getInitParameter("JDBC-URL");
	  final String username = getServletContext().getInitParameter("JDBC-USERNAME");
	  final String password = getServletContext().getInitParameter("JDBC-PASSWORD");

    dao = new GameDAO(url, username, password);
  }
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    doGet(request, response);
  }
  
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException
  {
    final String action = request.getServletPath();
  	
    try {
      switch (action) {
        case "/add":
        case "/edit":
          showEditForm(request, response);
          break;
        case "/insert":
          insertGame(request, response);
          break;
        case "/update":
          updateGame(request, response);
          break;
        default:
          viewGames(request, response);
          break;
      }   
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }

  private void insertGame(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException
  {
    String title = request.getParameter("title");
    String author = request.getParameter("author");
    int copies = Integer.parseInt(request.getParameter("copies"));
  	
    dao.insertGame(title, author, copies, copies);
    response.sendRedirect(request.getContextPath() + "/");
  }
  
  private void showEditForm(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException
		{
		  try {
		    final int id = Integer.parseInt(request.getParameter("id"));
		    
		    Game game = dao.getGame(id);
		    request.setAttribute("game", game);
		  } finally {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("bookform.jsp");
		    dispatcher.forward(request, response);
		  }
		}
  
  private void updateGame(HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException
		{
		  final String action = request.getParameter("action") != null
		    ? request.getParameter("action")
		    : request.getParameter("submit").toLowerCase();
		  final int id = Integer.parseInt(request.getParameter("id"));
			
		  Game game = dao.getGame(id);
		  switch (action) {
		    case "rent":
		      game.rentMe();
		      break;
		    case "return":
		      game.returnMe();
		      break;
		    case "save":
		      String title = request.getParameter("title");
		      String author = request.getParameter("author");
		      int copies = Integer.parseInt(request.getParameter("copies"));
		      int available = game.getAvailable() + (copies - game.getCopies());
				
		      game.setTitle(title);
		      game.setAuthor(author);
		      game.setCopies(copies);
		      game.setAvailable(available);
		      break;
		    case "delete":
		      deleteGame(id, request, response);
		      return;
		    }

		    dao.updateGame(game);
		    response.sendRedirect(request.getContextPath() + "/");
		  }
		    
		private void deleteGame(final int id, HttpServletRequest request, HttpServletResponse response)
		    throws SQLException, ServletException, IOException
		{	
		  dao.deleteGame(dao.getGame(id));	
		  response.sendRedirect(request.getContextPath() + "/");
		}
  
  private void viewGames(HttpServletRequest request, HttpServletResponse response)
      throws SQLException, ServletException, IOException
  {
    List<Game> games = dao.getGames();
    request.setAttribute("games", games);
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("inventory.jsp");
    dispatcher.forward(request, response);
  }
}