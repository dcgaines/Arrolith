import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;

import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

import java.util.Scanner;
import java.util.prefs.Preferences;

public class Arrolith extends Game {
	//private enum STATES { STARTUP, MENU, INGAME, PAUSED; }
	private final byte STARTUP = 0;
	private final byte MENU = 1;
	private final byte INGAME = 2;
	private final byte PAUSED = 3;
	private final byte PLAY = 0;
	private final byte TWOPLAYERS = 1;
	private final byte THREEPLAYERS = 2;
	private final byte FOURPLAYERS = 3;
	private final byte SOUND = 4;
	private final byte QUIT = 5;
	private byte currState;
	private byte menuState;
	private byte turnPhase;
	private byte pauseState;
	private boolean sound;
	private File prefs;
	Keys keys;
	private int scrWidth;
	private int scrHeight;
	
	
	public Arrolith()
	{
		currState = STARTUP;
		menuState = PLAY;
		pauseState = PLAY;
		sound = true;
		keys = new Keys();
		prefs = new File("prefs.txt");
		try {
			Scanner scan = new Scanner(prefs);
			while (scan.hasNext())
			{
				String tmp = scan.nextLine();
				if (tmp.contains("[SOUND]"))
					sound = (scan.nextLine().contains("true"));
				else if (tmp.contains("[GRAPHICS]"))
					scrWidth = scan.nextInt();
			}
			if (scrWidth == 0) scrWidth = 1024;
			scrHeight = (int) (scrWidth * (9.0/16));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println(scrWidth);
	}
	
	
	
	@Override
	public void tick(Graphics2D graphics, Input input, Sound sound) {
		
		/*    		CALCULATIONS			 */
		
		
		keys.checkKeys(input);
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			
			break;
		case INGAME:
			
			break;
		case PAUSED:
			
			break;
		}
		keys.setBuffers();
		
		
		/*  			DRAWING			   */
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			
			break;
		case INGAME:
			
			break;
		case PAUSED:
			
			break;
		}
		
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
