
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
  Clip clip;

  // public void playSound_MP3(){
  // String bip = "sound/click_mp3.mp3";
  // Media hit = new Media(new File(bip).toURI().toString());
  // MediaPlayer mediaPlayer = new MediaPlayer(hit);
  // mediaPlayer.play();
  // }
  // public void playSound_wav(String fn){
  // try {
  // AudioData data=new AudioStream(new FileInputStream(fn)).getData();
  // ContinuousAudioDataStream sound=new ContinuousAudioDataStream(data);
  // AudioPlayer.player.start(sound);
  // AudioPlayer.player.stop(sound);
  //
  // } catch(Exception e) {
  // System.out.println("Error");
  // }
  // // AudioInputStream audioIn = AudioSystem.getAudioInputStream(fn);
  // // Clip clip = AudioSystem.getClip();
  // // clip.open(audioIn);
  // // clip.start();
  // }
  public void playSoundLoop(String fn, int loop) // Loop
  {
    try {
      if (loop > 0) {
        File yourFile = new File(fn);
        AudioInputStream stream;
        AudioFormat format;
        DataLine.Info info;
        // Clip clip;

        stream = AudioSystem.getAudioInputStream(yourFile);
        format = stream.getFormat();
        info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        // Set Volume
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-60.0f);
        // gainControl.setValue(-4.0f);
        // Set Volume End
        clip.loop(loop - 1);
        clip.start();
      } else {
        clip.stop();
      }
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }

  public void stopSound() {
    try {
      clip.stop();
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
      Clip clip;

      stream = AudioSystem.getAudioInputStream(yourFile);
      format = stream.getFormat();
      info = new DataLine.Info(Clip.class, format);
      clip = (Clip) AudioSystem.getLine(info);
      clip.open(stream);
      // Set Volume
      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      // gainControl.setValue(6.0f); // max 6.0f
      gainControl.setValue(-60.0f);
      // Set Volume End
      clip.start();
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
  }
  // public void playBGM(String fn, int loop)
  // {
  // try {
  // if (loop > 0) {
  // File yourFile = new File(fn);
  // AudioInputStream stream;
  // AudioFormat format;
  // DataLine.Info info;
  //
  // stream = AudioSystem.getAudioInputStream(yourFile);
  // format = stream.getFormat();
  // info = new DataLine.Info(Clip.class, format);
  // clipBGM = (Clip) AudioSystem.getLine(info);
  // clipBGM.open(stream);
  // clipBGM.loop(loop-1);
  // clipBGM.start();
  // }
  // else {
  // clipBGM.stop();
  // }
  // }
  // catch (Exception ex) {
  // System.out.println(ex.toString());
  // }
  // }
}
