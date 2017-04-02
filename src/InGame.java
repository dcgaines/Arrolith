import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is used for making update methods for 
 * @author tyeic
 *
 */
public class InGame {

	
	protected Map map;
	ArrayList<Monster> monsters = null;
	protected Combat combat = null;
	
	private byte turnPhase;
	private final byte READY = 0;
	private final byte MOVE = 1;
	private final byte ACTION = 2;
	private final byte PASS = 3;
	private final byte COMBAT = 4;
	public byte currPlayer;
	public byte actionsUsed;
	private boolean transition;
	
	public InGame() {
		currPlayer = 0;
		actionsUsed = 0;
		transition = false;
	}
	
	private boolean checkMovement(Keys keys) {
		//note - movement only occurs when first conditions
		return ((keys.isKeyPressed(keys.DOWN)  && Update.players.get(currPlayer).walkDown(map))    //checks walking down
			||  (keys.isKeyPressed(keys.UP)    && Update.players.get(currPlayer).walkUp(map))      //checks walking up
		    ||  (keys.isKeyPressed(keys.LEFT)  && Update.players.get(currPlayer).walkLeft(map))    //checks walking left
		    || 	(keys.isKeyPressed(keys.RIGHT) && Update.players.get(currPlayer).walkRight(map))); //checks walking right
	}
	
	public void init(int numPlayers) {
		//Generate map
		try { 
			map = new Map(numPlayers); 
			Scanner scan = null;
			//read in monsters
			switch (numPlayers) {
			case 2:  scan = new Scanner(new File("monsters2p.txt")); break;
			case 3:  scan = new Scanner(new File("monsters3p.txt")); break;
			case 4:  scan = new Scanner(new File("monsters4p.txt")); break;
			default: throw new IOException();
			}
			int numOfMonsters = scan.nextInt();
			monsters = new ArrayList<Monster>(numOfMonsters);
			for (int i = 0; i < numOfMonsters; i++) {
				int x = scan.nextInt();
				int y = scan.nextInt();
				monsters.add(new Monster(x, y));
			}
			
			
			scan.close();		
		} catch (IOException e1) { e1.printStackTrace(); }
		
		//TODO: Generate players
		Random rand = new Random();
		for (byte i = 0; i < numPlayers; i++) {
			int n = rand.nextInt(4) + 1;
			switch (n) {
			case 1: Update.players.add(new Savant("")); break;
			case 2: Update.players.add(new Mason("")); break;
			case 3: Update.players.add(new Juggernaut("")); break;
			case 4: Update.players.add(new Operative("")); break;
			}
			Update.players.get(i).setInitPos(i, map);
		}
	}
	
	private boolean checkMonsters() {
		for (int i = 0; i < monsters.size(); i++)
			if 	(monsters.get(i).getX() == Update.players.get(currPlayer).getX()
			  && monsters.get(i).getY() == Update.players.get(currPlayer).getY())
				return true;
		return false;
	}
	
	public void calculate(Keys keys) {
		switch (turnPhase) {
		case READY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				turnPhase = MOVE;
			break;
		case MOVE:
			if (Update.players.get(currPlayer).getAP() > 0 && Update.getFrame() < 420) {
				checkMovement(keys); //if any key is pressed whatsoever
				if (checkMonsters()) {
					turnPhase = COMBAT;
					Update.resetFrame();
					transition = true;
					//TODO: Combat instantiation: combat = new Combat();
				}
				Update.incFrame();
				
				break;
			}
		case ACTION:
		case PASS:
			currPlayer++;
			if (currPlayer >= Update.players.size())
				currPlayer = 0;
			actionsUsed = 0;
			Update.resetFrame();
			turnPhase = READY;
			break;
		case COMBAT:
			if (transition) {
				if (Update.getFrame() > 5)
					transition = false;
				Update.incFrame();
			}
			else {
				//TODO: call combat.calculate();
			}
			break;
		}
	}
	
	public void draw(Graphics2D g, ArroGraphics graphics) {
		
		
		
		
		if (turnPhase == COMBAT) {
			//white transition
			if (transition) {
				int rgb = (int) (((6 - Update.getFrame()) / 6.) * 255);
				g.setColor(new Color(rgb, rgb, rgb, rgb));
				g.fillRect(0, 0, (int) (1920 * graphics.multiplyer), (int) (1080 * graphics.multiplyer));
			}
		}
		else {
			graphics.centerMap(g, map, Update.players.get(currPlayer));
			graphics.drawMap(g, map, Update.players, monsters);
			graphics.drawHud(g, Update.players, actionsUsed, Update.getFrame(), currPlayer);
			if (turnPhase == READY) {
				graphics.drawReady(g);
			}
		}
	}
	
}
