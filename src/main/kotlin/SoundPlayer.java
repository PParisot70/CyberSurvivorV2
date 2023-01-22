
import java.io.File;
import javax.sound.sampled.*;

public class SoundPlayer {

    Clip clip;

    public SoundPlayer(String audioFile) {
        try {
            File file = new File(audioFile);
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void stop() {
        clip.stop();
    }

    public void reset() {
        clip.setFramePosition(0);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java SoundPlayer audioFile");
            System.exit(1);
        }
        SoundPlayer player = new SoundPlayer(args[0]);
        player.play();
    }
}