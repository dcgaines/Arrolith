import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import arcadia.Input;
import arcadia.Sound;

public class Update {
	
	//Abstraction variables
	private static final byte STARTUP = 0;
	private static final byte MENU = 1;
	private static final byte INGAME = 2;
	private static final byte PAUSED = 3;
	
	//Game variables
	private static byte currState;
	//private byte currPlayer;
	private static int tFrame; //used for the timing of the phases
	public static ArrayList<Player> players;
	
	
	//Settings variables
	private static boolean sound;
	private static int scrWidth;
	private static int scrHeight;
	
	//Objects
	public static Menu menu;
	public static File prefs;
	public static Keys keys;
	public static ArroGraphics graphics;
	public static InGame game;
	
	
	public static void init() {
		
		//Initialize game variables
		currState = STARTUP;
		
		//Instantiate objects
		keys = new Keys();
		graphics = new ArroGraphics();
		players = new ArrayList<Player>();
		prefs = new File("prefs.txt");
		menu = new Menu();
		game  = new InGame();
		
		keys.setBuffer(keys.A); //fixes a glitch with startup
		
		//Set vars based on preferences
		try {
			
			Scanner scan = new Scanner(prefs);
			while (scan.hasNext())
			{
				String tmp = scan.nextLine();
				if (tmp.contains("[SOUND]"))
					sound = (scan.nextLine().contains("true")); //set sound
				else if (tmp.contains("[GRAPHICS]"))
					scrWidth = scan.nextInt(); //set graphics
			}
			if (scrWidth == 0) scrWidth = 1024;
			scrHeight = (int) (scrWidth * (9.0/16));
			System.out.println(scrHeight);
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
		
	private static void calculate(Input input) {
		keys.checkKeys(input);
		switch (currState)
		{
		case STARTUP:
			currState = MENU;
			break;
		case MENU:
			int temp = menu.calculate(keys); //menu returns the number of players to be added, -1 if no selection
			if (temp != -1) {
				game.init(temp);
				currState = INGAME;
			}
			break;
		case INGAME:	
			game.calculate(keys);
			break;
		case PAUSED:
			break;
		}
		keys.setBuffers();
	}
	
	private static void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) (1920 * graphics.multiplyer), (int) (1080 * graphics.multiplyer)); // allows a clean reset of the image
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			menu.draw(g, graphics);
			break;
		case INGAME:
			game.draw(g, graphics);
			
			break;
		case PAUSED:
			
			break;
		} 
	}
	
	public static void tick(Graphics2D g, Input input, Sound sound) {
		/*    		CALCULATIONS			 */
		calculate(input);
		
		/*  			DRAWING			   */
		draw(g);
	}
	public static void incFrame() { tFrame++; }
	public static int getFrame() { return tFrame; }
	public static void resetFrame() { tFrame = 0; }
}
