import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Arrolith extends Game {

//	static Thread aThread;
//	static Thread bThread;

	@Override
	public void tick(Graphics2D graphics, Input input, Sound sound) {
		// TODO Auto-generated method stub
//		aThread = new Thread() {
//			public void run() {
//				if (input.pressed(Button.A)) {
//					System.out.println("A Button Pressed");
//				}
//			}
//		};
//		bThread = new Thread(){
//			public void run() {
//				if (input.pressed(Button.B))
//					System.out.println("B Button Pressed");
//			}
//		};
//		
//		aThread.start();
//		bThread.start();
	}

	@Override
	public Image cover() {
		try {
			return ImageIO.read(new File("cover.jpg"));
		} catch (Exception e) {
			return null;
		}
	}

	public static void main(String[] args) {
		Arcadia.display(new Arcadia(new Game[] { new Arrolith() }));

	}
}
