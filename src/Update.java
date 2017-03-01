import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import arcadia.Input;
import arcadia.Sound;

public class Update {
	
	//Abstraction variables
	private final byte STARTUP = 0;
	private final byte MENU = 1;
	private final byte INGAME = 2;
	private final byte PAUSED = 3;
	
	//Game variables
	private byte currState;
	//private byte currPlayer;
	private byte actionsUsed;
	private int tFrame; //used for the timing of the phases
	private ArrayList<Player> players;
	
	
	//Settings variables
	private boolean sound;
	private int scrWidth;
	private int scrHeight;
	
	//Objects
	private Menu menu;
	private File prefs;
	private Keys keys;
	private Map map;
	private ArroGraphics graphics;
	private InGame game;
	
	
	public Update() {
		
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
		
	
	private void calculate(Input input) {
		keys.checkKeys(input);
		switch (currState)
		{
		case STARTUP:
			currState = MENU;
			break;
		case MENU:
			menu.calculate(keys, map);
			break;
		case INGAME:	
			game.calculate(keys);
			break;
		case PAUSED:
			break;
		}
		keys.setBuffers();
	}
	
	private void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, (int) (1920 * graphics.multiplyer), (int) (1080 * graphics.multiplyer)); // allows a clean reset of the image
		switch (currState)
		{
		case STARTUP:
			
			break;
		case MENU:
			graphics.drawMenu(g, menu.state);
			break;
		case INGAME:
			graphics.centerMap(g, map, players.get(game.currPlayer));
			graphics.drawMap(g, map, players);
			graphics.drawHud(g, players, actionsUsed, tFrame);
			/*if (turnPhase == READY) {
				g.setColor(Color.BLACK);
				g.fillRect((int) (760 * graphics.multiplyer), (int) (340 * graphics.multiplyer), 
						(int) (400 * graphics.multiplyer), (int) (400 * graphics.multiplyer));
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(15 * graphics.multiplyer));
				g.drawRect((int) (760 * graphics.multiplyer), (int) (340 * graphics.multiplyer), 
						(int) (400 * graphics.multiplyer), (int) (400 * graphics.multiplyer));
				g.drawString("Press A", 800 * graphics.multiplyer, 504 * graphics.multiplyer);
				g.drawString("to start.", 800 * graphics.multiplyer, 640 * graphics.multiplyer);
			}*/
			break;
		case PAUSED:
			
			break;
		} 
	}
	
	public void tick(Graphics2D g, Input input, Sound sound) {
		/*    		CALCULATIONS			 */
		calculate(input);
		
		/*  			DRAWING			   */
		draw(g);
		}
}
