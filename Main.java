import java.awt.EventQueue;
import javax.swing.JFrame;
public class Main extends JFrame {
  public Main() {
    init();
  }
  private void init() {
    Coder myGame = new Coder();
    myGame.setFocusable(true);
    add(myGame);
    pack();
    setTitle("Game: Tetris");
    // setLocation(15, 5);
    setLocation(400, 100);
    // setLocation(1700, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // setLocationRelativeTo(null);
  }
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      Main main = new Main();
      main.setVisible(true);
  	});
  }
}
