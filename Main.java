import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {
  public Main() {
    init();
  }

  private void init() {
    Coder game = new Coder();
    game.setFocusable(true);
    add(game);
    pack();
    setTitle("Game: Tetris");
    // setLocation(15, 5);
    setLocation(100, 50);
    // setLocation(1750, 250);
    // setLocation(1600, 250);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // setLocationRelativeTo(null);
    setResizable(false);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      Main main = new Main();
      main.setVisible(true);
    });
  }
}
