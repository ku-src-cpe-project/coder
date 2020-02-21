import java.io.*;
import java.util.*;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.regex.*;
import java.lang.*;/*write*/

class ReadFile {
  private String mapNameStr;
  private String a;
  private Scanner x;
  private Formatter y;
  private String data = "";

  // Now
  public void openRead() {
    try {
      x = new Scanner(new File("bin.txt"));
    } catch (Exception e) {
      System.out.println("could not find file");
    }
  }

  public void ReadFile() {
    setMapName(x.next());
  }

  public void openWrite() {
    try {
      y = new Formatter("bin.txt");
    } catch (Exception e) {
      System.out.println("could not find file");
    }
  }

  public void write(String text) {
    y.format(text);
  }

  public void closeRead() {
    x.close();
  }

  public void closeWrite() {
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

  public String getMapName() {
    return this.mapNameStr;
  }

  public void setMapName(String a) {
    this.mapNameStr = a;
  }
}