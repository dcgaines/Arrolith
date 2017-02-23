import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import arcadia.Arcadia;
import arcadia.Game;
import arcadia.Input;
import arcadia.Sound;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.prefs.Preferences;

public class Arrolith extends Game {
	private final byte STARTUP = 0;
	private final byte MENU = 1;
	private final byte INGAME = 2;
	private final byte PAUSED = 3;
	private final byte PLAY = 0;
	private final byte TWOPLAYERS = 1;
	private final byte THREEPLAYERS = 2;
	private final byte FOURPLAYERS = 3;
	private final byte SOUND = 4;
	private final byte READY = 0;
	private final byte MOVE = 1;
	private final byte ACTION = 2;
	private final byte PASS = 3;
	private byte currState;
	private byte menuState;
	private byte turnPhase;
	private byte pauseState;
	private byte currPlayer;
	private byte actionsUsed;
	private int tFrame; //used for the timing of the phases
	private boolean sound;
	private File prefs;
	private Keys keys;
	private Map map;
	private ArroGraphics graphics;
	private int scrWidth;
	private int scrHeight;
	private ArrayList<Player> players;
	private Font alegreya;
	public Arrolith()
	{
		currState = STARTUP;
		menuState = PLAY;
		pauseState = PLAY;
		sound = true;
		keys = new Keys();
		keys.setBuffer(keys.A);
		graphics = new ArroGraphics();
		players = new ArrayList<Player>();
		prefs = new File("prefs.txt");
		actionsUsed = 0;
		currPlayer = 0;
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
			System.out.println(scrHeight);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			alegreya = Font.createFont(Font.TRUETYPE_FONT, new File("Alegreya.ttf"))
					.deriveFont(100 * (float) graphics.multiplyer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(scrWidth);
	}
	
	private void menu() {
		switch (menuState)
		{
		case PLAY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				menuState = TWOPLAYERS;
			else if (keys.getKey(keys.DOWN) && keys.getBuffer(keys.DOWN))
				menuState = SOUND;
			break;
		case SOUND:
			if (keys.getKey(keys.UP) && keys.getBuffer(keys.DOWN))
				menuState = PLAY;
			break;
		case TWOPLAYERS:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A)) {
				//Generate map
				try { map = new Map(2); }
				catch (IOException e1) { e1.printStackTrace(); }
				//Generate players
				Random rand = new Random();
				for (byte i = 0; i < 2; i++) {
					int n = rand.nextInt(4) + 1;
					switch (n) {
					case 1: players.add(new Savant("")); break;
					case 2: players.add(new Mason("")); break;
					case 3: players.add(new Juggernaut("")); break;
					case 4: players.add(new Operative("")); break;
					}
					players.get(i).setInitPos(i, map);
				}
				currState = INGAME;
				turnPhase = READY;
			}
			else if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) 
				menuState = THREEPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				menuState = PLAY;
			break;
		case THREEPLAYERS:
			if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) 
				menuState = FOURPLAYERS;
			else if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT))
				menuState = TWOPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				menuState = PLAY;
			break;
		case FOURPLAYERS:
			if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT))
				menuState = THREEPLAYERS;
			else if (keys.getKey(keys.B) && keys.getBuffer(keys.B))
				menuState = PLAY;
			break;
		}
	}
	
	@Override
	public void tick(Graphics2D g, Input input, Sound sound) {
		/*    		CALCULATIONS			 */
		
		keys.checkKeys(input);
		switch (currState)
		{
		case STARTUP:
			currState = MENU;
			break;
		case MENU:
			menu();
			break;
		case INGAME:
			switch (turnPhase) {
			case READY:
				
				if (keys.getKey(keys.A) && keys.getBuffer(keys.A)) {
					turnPhase = MOVE;
					actionsUsed = 0;
					tFrame = 0;
				}
				break;
			case MOVE:
				
				if (actionsUsed > 13 || tFrame > 600) {
					tFrame = 0;
					turnPhase = ACTION;
				}
				if (keys.getKey(keys.UP) && keys.getBuffer(keys.UP)) {
					if (players.get(currPlayer).walkUp(map))
						actionsUsed++;
				}
				else if (keys.getKey(keys.DOWN) && keys.getBuffer(keys.DOWN)) {
					if (players.get(currPlayer).walkDown(map))
						actionsUsed++;
				}
				else if (keys.getKey(keys.LEFT) && keys.getBuffer(keys.LEFT)) {
					if (players.get(currPlayer).walkLeft(map))
						actionsUsed++;
				}
				else if (keys.getKey(keys.RIGHT) && keys.getBuffer(keys.RIGHT)) {
					if (players.get(currPlayer).walkRight(map))
						actionsUsed++;
				}
				tFrame++;
				break;
			case ACTION:
				turnPhase = PASS;
				break;
			case PASS:
				currPlayer++;
				if (currPlayer > players.size() - 1)
					currPlayer = 0;
				tFrame = 0;
				actionsUsed = 0;
				turnPhase = READY;
				break;
			}
			
			break;
		case PAUSED:
			
			break;
		}
		keys.setBuffers();
		
		
		/*  			DRAWING			   */
		g.setColor(Color.BLACK);
		g.setFont(alegreya);
		g.fillRect(0, 0, (int) (1920 * graphics.multiplyer), (int) (1080 * graphics.multiplyer)); // allows a clean reset of the image
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			graphics.drawMenu(g, menuState);
			break;
		case INGAME:
			graphics.centerMap(g, map, players.get(currPlayer));
			graphics.drawMap(g, map, players);
			graphics.drawHud(g, players, actionsUsed, tFrame);
			if (turnPhase == READY) {
				g.setColor(Color.BLACK);
				g.fillRect((int) (760 * graphics.multiplyer), (int) (340 * graphics.multiplyer), 
						(int) (400 * graphics.multiplyer), (int) (400 * graphics.multiplyer));
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(15 * graphics.multiplyer));
				g.drawRect((int) (760 * graphics.multiplyer), (int) (340 * graphics.multiplyer), 
						(int) (400 * graphics.multiplyer), (int) (400 * graphics.multiplyer));
				g.drawString("Press A", 800 * graphics.multiplyer, 504 * graphics.multiplyer);
				g.drawString("to start.", 800 * graphics.multiplyer, 640 * graphics.multiplyer);
			}
			break;
		case PAUSED:
			
			break;
		}
		
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
