import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class is used for making update methods for 
 * @author tyeic
 *
 */
public class InGame {

	public static final int moveTime = 30 * 30;
	private final byte WIN = 5;
	
	protected Map map;
	ArrayList<Monster> monsters = null;
	private ArrayList<Potion> potions = new ArrayList<Potion>();
	protected Combat combat = null;
	
	private byte turnPhase;
	private final byte READY = 0;
	private final byte MOVE = 1;
	private final byte ACTION = 2;
	private final byte PASS = 3;
	private final byte COMBAT = 4;
	private final byte END = 5;
	public byte currPlayer;
	public byte actionsUsed;
	private boolean transition;
	
	int currMonster;
	
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
			case 2:  scan = new Scanner(new File("monsters/monsters2p.txt")); break;
			case 3:  scan = new Scanner(new File("monsters/monsters3p.txt")); break;
			case 4:  scan = new Scanner(new File("monsters/monsters4p.txt")); break;
			default: throw new IOException();
			}
			int numOfMonsters = scan.nextInt();
			monsters = new ArrayList<Monster>(numOfMonsters);
			for (int i = 0; i < numOfMonsters; i++) {
				int x = scan.nextInt();
				int y = scan.nextInt();
				int level = scan.nextInt( );
				monsters.add(new Monster(x, y, level));
			}
			
			
			scan.close();		
		} catch (IOException e1) { e1.printStackTrace(); }
		
		for (byte i = 0; i < numPlayers; i++) {
			Update.players.get(i).setInitPos(i, map);
		}
		
		placePotions();
	}
	
	private int checkMonsters() {
		for (int i = 0; i < monsters.size(); i++)
			if 	(monsters.get(i).getX() == Update.players.get(currPlayer).getX()
			  && monsters.get(i).getY() == Update.players.get(currPlayer).getY() 
			  && monsters.get(i).getOwner() != Update.players.get(currPlayer)) 
				return i;
		return -1;
	}
	
	public boolean calculate(Keys keys) {
		switch (turnPhase) {
		case READY:
			if (keys.getKey(keys.A) && keys.getBuffer(keys.A))
				turnPhase = MOVE;
			break;
		case MOVE:
			if (Update.players.get(currPlayer).getAP() > 0 && Update.getFrame() < moveTime) {
				checkMovement(keys); //if any key is pressed whatsoever
				currMonster = checkMonsters();
				if (currMonster != -1) {
					turnPhase = COMBAT;
					Update.resetFrame();
					transition = true;
					combat = new Combat(Update.players.get(currPlayer), monsters.get(currMonster));
					Update.players.get(currPlayer).resetAP();
				}
				Update.incFrame();
				break;
			}
		case ACTION:
		case PASS:
			Update.players.get(currPlayer).resetAP();
			actionsUsed = 0;
			Update.resetFrame();
			if (Update.players.get(currPlayer).getInsignias() > WIN) {
				turnPhase = END;
				return false;
			}
			currPlayer++;
			if (currPlayer >= Update.players.size())
				currPlayer = 0;
			turnPhase = READY;
			break;
		case COMBAT:
			if (transition) {
				if (Update.getFrame() > 5)
					transition = false;
				Update.incFrame();
			}
			else {
				int result = combat.calculate(keys);
				Monster mon = monsters.get( currMonster );
				Player p = Update.players.get( currPlayer );
				if (result > 0) {
					turnPhase = ACTION;
					if (mon.getOwner() != null) mon.getOwner( ).addInsignias( -result );
					monsters.set(currMonster, new Monster(Update.players.get(currPlayer), mon.getLevel( )));
					map.getTile( p.yCoord, p.xCoord ).setOwner( currPlayer );
					p.walkDown(map);
					p.addInsignias( result );
					p.addCoins( (int) ( result * 20 * ( 1 + ((p.negotiation) / 10.) ) ) );
					p.updateStats( );
				} else if (result < 0) {
					turnPhase = ACTION;
					p.setInitPos(currPlayer, map);
					p.addHealth(p.getMaxHealth());
					p.addCoins( result );
					p.updateStats( );
				}
			}
			break;
		case END:
			Update.incFrame();
			if (Update.getFrame() > 150) {
				Update.resetFrame();
				return true;
			}
			break;
		}
		return false;
	}
	
	public void draw(Graphics2D g, ArroGraphics graphics) {
		if (turnPhase == COMBAT) {
			combat.draw(g, graphics);
			//white transition
			if (transition) {
				int rgb = (int) (((6 - Update.getFrame()) / 6.) * 255);
				g.setColor(new Color(rgb, rgb, rgb, rgb));
				g.fillRect(0, 0, (int) (1920 * graphics.multiplyer), (int) (1080 * graphics.multiplyer));
			}
		}
		else if (turnPhase == END) {
			ArrayList<Byte> places = new ArrayList<Byte>(4);
			places.add(currPlayer);
			for (byte j = 0; j < Update.players.size(); j++) {
				Byte maxPlayer = -1;
				int max = Integer.MIN_VALUE;
				for (Byte i = 0; i < Update.players.size(); i++) {
					if (Update.players.get(i).getInsignias() > max && !places.contains(i)) {
						maxPlayer = i;
						max = Update.players.get(i).getInsignias();
					}
				}
				places.add(maxPlayer);
			}
			graphics.drawEndGame(g, Update.players, currPlayer, places.get(1), (places.size() > 2) ? places.get(2) : -1, (places.size() > 3) ? places.get(3) : -1);
		}
		else {
			graphics.centerMap(g, map, Update.players.get(currPlayer));
			graphics.drawMap(g, map, Update.players, monsters, potions);
			graphics.drawHud(g, Update.players, actionsUsed, Update.getFrame(), currPlayer);
			if (turnPhase == READY) {
				graphics.drawReady(g, currPlayer);
			}
		}
	}
	
	public void placePotions(){
		int x;
		int y;
		
		if(potions.size( ) < 12){
			x = (int)(Math.random( ) * map.getWidth( ));
			y = (int)(Math.random( ) * map.getHeight( ));
			Tile tile = map.getTile( y, x ); // Row, col
			if(tile.getWalk( )){
				potions.add( new Potion(x, y));
				tile.setWalk( false );
				tile.setAct( true );
			}
			placePotions();
		} else
			return;
	}
	
	public void removePotion(int x, int y){
		for(int i = 0; i < potions.size( ); i++){
			Potion pot = potions.get( i );
			if(pot.getX( ) == x && pot.getY( ) == y ){
				potions.remove( i );
				placePotions();
				break;
			}
		}
	}
}
