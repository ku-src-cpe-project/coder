import java.io.*;
import java.util.*;
import java.util.regex.*;

class ReadFile {
  private Scanner x;
  private Formatter y;

  // Now
  public void openFileRead() {
    try {
      x = new Scanner(new File("bin.txt"));
    } catch (Exception e) {
      System.out.println("could not find file");
    }
  }

  public void ReadFile() {
    Coder.mapNummberSave = x.next();
  }

  public void openFileWrite() {
    try {
      y = new Formatter("bin.txt");
    } catch (Exception e) {
      System.out.println("could not find file");
    }
  }

  public void write(String text) {
    y.format(text);
  }

  public void closeFileRead() {
    x.close();
  }

  public void closeFileWrite() {
    y.close();
  }

  // Search word in text File
  public void search_(String search, String data) {
    Pattern pt = Pattern.compile(search);
    Matcher mt = pt.matcher(data);
    while (mt.find()) {
      System.out.println(mt.group());
    }
  }
}