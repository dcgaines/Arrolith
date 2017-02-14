import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Button;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

import java.util.ArrayList;
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
	private Keys keys;
	private Map map;
	private ArroGraphics graphics;
	private int scrWidth;
	private int scrHeight;
	private ArrayList<Player> players;
	
	public Arrolith()
	{
		currState = STARTUP;
		menuState = PLAY;
		pauseState = PLAY;
		sound = true;
		keys = new Keys();
		graphics = new ArroGraphics();
		players = new ArrayList<Player>();
		for (int i = 0; i < 1; i++)
			players.add(new Juggernaut("ok"));
		try {
			map = new Map(2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		//System.out.println(scrWidth);
	}
	
	
	
	@Override
	public void tick(Graphics2D g, Input input, Sound sound) {
		
		/*    		CALCULATIONS			 */
		
		
		keys.checkKeys(input);
		switch (currState)
		{
		case STARTUP:
			if (keys.getKey(3) && keys.getBuffer(3))
				players.get(0).walkUp(map);
			else if (keys.getKey(4) && keys.getBuffer(4))
				players.get(0).walkDown(map);
			else if (keys.getKey(5) && keys.getBuffer(5))
				players.get(0).walkLeft(map);
				//graphics.moveLeft(5);
			else if (keys.getKey(6) && keys.getBuffer(6))
				players.get(0).walkRight(map);
				//graphics.moveRight(5);
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) (1920 * graphics.multiplyer), (int) (1080 * graphics.multiplyer)); // allows a clean reset of the image
		graphics.centerMap(g, map, players.get(0));
		switch (currState)
		{
		case STARTUP:
			//g.
			try {
				graphics.drawMap(g, map, players);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
