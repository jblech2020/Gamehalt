package library;

public class Game
{  
  private int id;
  private String title;
  private String author;
  private int copies;
  private int available;
    
  public Book(int id, String title, String author, int copies, int available)
  {
    super();
    
    this.id = id;
    this.title = title;
    this.author = author;
    this.copies = copies;
    this.available = available;
  }
  
  public int getId()
  {
    return id;
  }
  
  public String getTitle()
  {
    return title;
  }
  
  public String getAuthor()
  {
    return author;
  }
  
  public int getCopies()
  {
    return copies;
  }
  
  public void setCopies(int copies)
  {
    this.copies = copies;
  }
  
  public int getAvailable()
  {
    return available;
  }
  
  public void rentMe()
  {
    if (available > 0) {
      available--;
    }
  }
  
  public void returnMe()
  {
    if (available < copies) {
      available++;
    }
  }
  
  public void setTitle(String title)
  {
    this.title = title;
  }

  public void setAuthor(String author)
  {
    this.author = author;
  }

  public void setAvailable(int available)
  {
    this.available = available;
  }
}