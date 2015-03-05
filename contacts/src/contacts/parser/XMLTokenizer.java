package contacts.parser;

import java.io.*;

public class XMLTokenizer {

  private XMLTokenManager _tm;
  private Token _current;

  // /////////////////////////////////////////////////////////////////////////////////////
  // the public interface

  public XMLTokenizer(Reader r) {
    _tm = new XMLTokenManager(new SimpleCharStream(r));
  }

  // return the current token, without advancing
  public Token current() {
    return _current;
  }    

  //advance the current token, losing all knowledge of the previous current token
  public void advance() {
    _current = _tm.getNextToken();
  }

}

