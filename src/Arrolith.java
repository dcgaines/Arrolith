import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

public class Arrolith extends Game {
	public static boolean sound;
	
	public Arrolith() {
			
		File prefs = new File("prefs.txt");
		//Set vars based on preferences
				try {
					Scanner scan = new Scanner(prefs);
					while (scan.hasNext())
					{
						String tmp = scan.nextLine();
						if (tmp.contains("[SOUND]"))
							sound = (scan.nextLine().contains("true")); //set sound
						else if (tmp.contains("[GRAPHICS]"))
							WIDTH = scan.nextInt(); //set graphics
					}
					if (WIDTH == 0) WIDTH = 1024;
					HEIGHT = (int) (WIDTH * (9.0/16));
					scan.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
