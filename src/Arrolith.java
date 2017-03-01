import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Arrolith extends Game {
	Update update;
	
	
	
	public Arrolith() {
		update = new Update();
	}
	
	@Override
	public void tick(Graphics2D g, Input input, Sound sound) {
		update.tick(g, input, sound);
	}

	@Override
	public Image cover() {
		try {
			return ImageIO.read(new File("splash.png"));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new Arrolith() }));
	}
}
