import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.regex.*;
import java.lang.*;/*write*/

class ReadFile {
  private String a;
  private Scanner x;
  private Formatter y;
  private String data = "";

  // Now
  public void OpenFile_read() {
    try {
      x = new Scanner(new File("bin.txt"));
    } catch (Exception e) {
      System.out.println("could not find file");
    }
  }

  public void ReadFile() {
    Coder.mapNumberS = x.next();
  }

  public void OpenFile_write() {
    try {
      y = new Formatter("bin.txt");
    } catch (Exception e) {
      System.out.println("could not find file");
    }
  }

  public void AddRecord(String text) {
    y.format(text);
  }

  public void CloseFile_read() {
    x.close();
  }

  public void CloseFile_write() {
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