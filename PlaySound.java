
//  .wav
// import java.awt.*;
// import java.awt.event.*;
// import java.awt.image.BufferedImage;
// import javax.swing.JPanel;
// import javax.swing.ImageIcon;
import java.io.*;
import javax.sound.sampled.*;

// .wav (continue)
// import javax.sound.sampled.AudioInputStream;
// import javax.sound.sampled.AudioSystem;
// import javax.sound.sampled.Clip;
// //
// import sun.audio.AudioStream;
//  .mp3
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;
class PlaySound {
  Clip clipBGM;
  Clip clipSFX;
  private int volume;

  public PlaySound() {
    this.volume = 50;
  }

  public void playSoundLoop(String b) // Loop
  {
    try {
      File yourFile = new File(b);
      AudioInputStream stream = AudioSystem.getAudioInputStream(yourFile);
      AudioFormat format = stream.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format);
      clipBGM = (Clip) AudioSystem.getLine(info);
      clipBGM.open(stream);
      // Set Volume
      FloatControl gainControl = (FloatControl) clipBGM.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(convVolume(this.volume));
      clipBGM.loop(9999);
      clipBGM.start();
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  public void playSoundSingle(String fn) // Single
  {
    try {
      File yourFile = new File(fn);
      AudioInputStream stream;
      AudioFormat format;
      DataLine.Info info;
      stream = AudioSystem.getAudioInputStream(yourFile);
      format = stream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clipSFX = (Clip) AudioSystem.getLine(info);
      clipSFX.open(stream);
      // Set Volume
      FloatControl gainControl = (FloatControl) clipSFX.getControl(FloatControl.Type.MASTER_GAIN);
      gainControl.setValue(convVolume(this.volume)); // max 6.0f
      clipSFX.start();
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  public void stopSound(int a) {
    try {
      clipBGM.stop();
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  public int convVolume(int a) {
    int b;
    b = (int) (((a * (6.0f + 60.0f)) / 100) - 60.0f);
    return b;
  }

  public void update(int b) {
    this.volume = b;
    FloatControl gainControl = (FloatControl) clipBGM.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(convVolume(b));
  }
}
