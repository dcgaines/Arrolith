import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Arrolith extends Game {
	
	public Arrolith() {
		Update.init();
	}
	
	public void tick(Graphics2D g, Input input, Sound sound) {
		Update.tick(g, input, sound);
	}

	public Image cover() {
		try {
			return ImageIO.read(new File("img/splash.png"));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new Arrolith() }));
	}
}
