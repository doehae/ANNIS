package annis.tabledefs;

import java.io.Serializable;

/**
 * A column definition for SQLite.
 * @author Thomas Krause <krauseto@hu-berlin.de>
 *
 */
@SuppressWarnings("serial")
public class Column implements Serializable
{
  
  public enum Type
  {
    NULL, INTEGER, REAL, TEXT, BLOB
  }
  
  private final String name;
  private Type type = Type.TEXT;
  private boolean unique = false;
  
  public Column(String name)
  {
    this.name = name;
  }
  
  /**
   * Copy constructor.
   * @param orig
   */
  public Column(Column orig)
  {
    this.name = orig.name;
    this.type = orig.type;
    this.unique = orig.unique;
  }

  
  public Column type(Type type)
  {
    Column copy = new Column(this);
    copy.type = type;
    return copy;
  }
  
  public Column unique()
  {
    Column copy = new Column(this);
    copy.unique = true;
    return copy;
  }
  
  public String getName()
  {
    return name;
  }
  
  public Type getType()
  {
    return type;
  }
  
  public boolean isUnique()
  {
    return unique;
  }
  
  @Override
  public String toString()
  {
    return name + " " + type.name() + (unique ? " UNIQUE" : "");
  }
}
